package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.autonomous.commands.ShootingAimShip;
import ca.fourthreethreefour.autonomous.commands.VisionAllignment;
import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.settings.Settings;
import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.intake.Arm;
import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.Hatch;
import ca.fourthreethreefour.teleop.intake.Mechanum;
import ca.fourthreethreefour.teleop.intake.SideWinder;
import ca.fourthreethreefour.teleop.systems.Encoders;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;
import ca.fourthreethreefour.vision.Vision;
//import ca.fourthreethreefour.vision.exceptions.visionErrorException;
//import ca.fourthreethreefour.vision.exceptions.visionTargetDetectionException;
import edu.wpi.first.wpilibj.Relay;
//import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Teleop {

  // Initialize miscellaneous configuration values
  // private static int armPIDOffset = -328; // Todo: Tune offset at competition
  // private static final int armPIDAcceptableError = 2;
  // private static final int armPIDHatchIntakeOuttakeSetpoint = 90;
  // private static final int armPIDCargoOuttakeSetpoint = 110;
  // private static final int armPIDHatchIntakeSetpoint = 200;
  // private static final int armPIDCargoIntakeSetpoint = 10;

  // Creates and initializes various objects needed in teleop
  private XboxController driver = new XboxController(Settings.DRIVER_CONTROLLER_PORT);
  private Cargo cargo = new Cargo();
  public Encoders encoders = new Encoders();
  public Hatch hatch = new Hatch();
  private Mechanum mechanum = new Mechanum();
  public Arm arm = new Arm(encoders, cargo);
  public SideWinder sideWinder = new SideWinder(encoders, hatch);
  // private Arm armPIDLeft;
  // private Arm armPIDRight;
  public Drive drive = new Drive();
  public Ultrasonics ultrasonics = new Ultrasonics();
  public Vision vision = new Vision(this);
  public VisionAllignment visionAllignment = new VisionAllignment(this.vision, this, this.driver, this.drive);
  private ShootingAimShip shootingAlign = new ShootingAimShip(this, this.ultrasonics, this.arm);
  
  public static boolean cargoOuttake;

  public double middleSetpointHatch = Settings.HATCH_PID_MIDDLE_SETPOINT;

  public void RobotInit() {
    ultrasonics.enable();
    ultrasonics.ultrasonicPollingThread();
    encoders.potentiometerInit();
  }

  /**
   * Runs as the start of teleop
   * @return void
   */
  public void TeleopInit() {
    cargo.intakeRotateMotor1.setSafetyEnabled(true);
    cargo.intakeRotateMotor2.setSafetyEnabled(true);
    cargo.cargoOuttakeLeftMotor.setSafetyEnabled(true);
    cargo.cargoOuttakeRightMotor.setSafetyEnabled(true);
    mechanum.mechanumMotor.setSafetyEnabled(true);
    drive.driveTrain.setSafetyEnabled(true);
    drive.gearShiftSolenoid.set(drive.gearLow);
    mechanum.mechanumSolenoid.set(Value.kReverse);
    hatch.hatchSolenoidIn();
    arm.setAbsoluteTolerance(Settings.ARM_PID_TOLERANCE);
    cargoOuttake = true;
  }
  

  /**
   * Runs periodically during teleop
   * @return void
   */
  public void TeleopPeriodic() {

    //If any axis on controller passes threshold, disable vision alignment and return driver control.
    if(Math.abs(driver.getY(Hand.kLeft)) > 0.15 || Math.abs(driver.getY(Hand.kRight)) > 0.15 || Math.abs(driver.getX(Hand.kLeft)) > 0.15 || Math.abs(driver.getX(Hand.kRight)) > 0.15){
      visionAllignment.cancel();
    }

    drive.drive(driver, cargoOuttake);

    // System.out.println(arm.returnPIDInput());
    encoders.printPotentiometer();
    // Logging.log("PID: " + arm.returnPIDInput());
    // double intakeSpeed = driver.getTriggerAxis(Hand.kRight) - driver.getTriggerAxis(Hand.kLeft);
    // if (Math.abs(intakeSpeed) > 0.05) {
    //   cargo.intakeRotate(intakeSpeed*Settings.INTAKE_ROTATE_SPEED);
    // } else {
    //   cargo.intakeRotate(0);
    // };

    if (driver.getTriggerAxis(Hand.kLeft) > 0.05) {
      cargo.cargoOuttake(driver.getTriggerAxis(Hand.kLeft)*Settings.INTAKE_ROTATE_SPEED);
      mechanum.mechanumRoller(-driver.getTriggerAxis(Hand.kLeft));
    } else if (driver.getTriggerAxis(Hand.kRight) > 0.05 /* && encoders.cargoButton.get() */) {
      // if (encoders.cargoButton.get()) {
        cargo.cargoTransfer(driver.getTriggerAxis(Hand.kRight));
        mechanum.mechanumRoller(driver.getTriggerAxis(Hand.kRight));
      // }
      if (!encoders.cargoButton.get()) {
      //           armPIDSetpoint = armPIDCargoOuttakeSetpoint + 1;
      //           armPIDLeft.setSetpoint(armPIDSetpoint);
      //           armPIDRight.setSetpoint(armPIDSetpoint);
      //           armPIDLeft.enable();
      //           armPIDRight.enable();
      //           mechanum.mechanumRetract();
              Logging.log("Auto shoot setpoint");
              if (shootingAlign.isRunning()) {
                shootingAlign.cancel();
              }
              arm.setSetpoint(Settings.ARM_PID_SHOOTING_SETPOINT + 1);
              arm.enable();
              mechanum.mechanumRetract();
            }

    } else {
      cargo.stop();
      mechanum.mechanumRoller(0);
      if (!encoders.cargoButton.get()) {
        mechanum.mechanumRetract();
      }
    }
    
    if (driver.getStickButtonReleased(Hand.kRight)) {
      drive.setGearLow();
    } else if (Math.abs(driver.getY(Hand.kLeft)) <= Settings.LOW_GEAR_THRESHOLD || (Math.abs(driver.getY(Hand.kLeft)) <= Settings.LOW_GEAR_THRESHOLD && Math.abs(driver.getX(Hand.kRight)) >= Settings.LOW_GEAR_THRESHOLD)) {
      drive.setGearHigh();
    }

    if (driver.getStickButtonReleased(Hand.kLeft) && Settings.REVERSABLE_CONTROLS) {
      cargoOuttake =  !cargoOuttake;
    }
    // Logging.put(Settings.DRIVE_DIRECTION_ENTRY, cargoOuttake);

    if (driver.getAButton()) {
      hatch.hatchSolenoidOut();
    } else {
      hatch.hatchSolenoidIn();
    }

    if (driver.getBButtonReleased()) {
      mechanum.mechanumShift();
    }

    if (!encoders.hatchHallEffectRight.get()) {
      Settings.HATCH_POTENTIOMETER_OFFSET -= encoders.hatchPotentiometerGet();
      middleSetpointHatch = encoders.hatchPotentiometerGet() + Settings.HATCH_PID_MIDDLE_SETPOINT;

    }

    if ((encoders.armPotentiometerGet() < Settings.HATCH_ARM_PID_THRESHOLD) || (driver.getPOV() == 270)) {
      sideWinder.setSetpoint(middleSetpointHatch);
      sideWinder.enable();
    } else if (driver.getXButton() && encoders.hatchHallEffectRight.get()) {
      if (sideWinder.isEnabled()) {
        sideWinder.disable();
      }
      hatch.hatchSet(0.2);
    } else if (driver.getYButton() && encoders.hatchHallEffectLeft.get()) {
      if (sideWinder.isEnabled()) {
        sideWinder.disable();
      }
      hatch.hatchSet(-0.2);
    } else if (!sideWinder.isEnabled()) {
      hatch.hatchSet(0);
    }
    
    if (driver.getBumper(Hand.kLeft)) {
      if (arm.isEnabled()) {
        arm.disable();
      }
        arm.armRotate(1);
    } else if (driver.getBumper(Hand.kRight)) {
      if (arm.isEnabled()) {
        arm.disable();
      }
      if (encoders.armInnerLimitSwitch.get()) {
        arm.armRotate(-1);
      }
    } else if (!arm.isEnabled()) {
      arm.armRotate(0);
    }

      // Up D-Pad - Sets the PID setpoint to hatch outtake and retracts the mecanum intake
        if (driver.getPOV() == 0) {
          Logging.log("Shooter set point up");
          if (shootingAlign.isRunning()) {
            shootingAlign.cancel();
          }
          arm.setSetpoint(Settings.ARM_PID_HATCH_SETPOINT);
          arm.enable();
          mechanum.mechanumRetract();
        } else if (driver.getPOV() == 90) {  // Right D-Pad - Sets the PID setpoint to cargo outtake and retracts the mecanum intake
          Logging.log("Shooter set point right");
          shootingAlign.start();
          // arm.setSetpoint(Settings.ARM_PID_SHOOTING_SETPOINT);
          // arm.enable();
          mechanum.mechanumRetract();
        } else if (driver.getPOV() == 180) {  // Down D-Pad - Sets the PID setpoint to hatch ground and retracts the mecanum intake
          Logging.log("Shooter set point down");
          if (shootingAlign.isRunning()) {
            shootingAlign.cancel();
          }
          arm.setSetpoint(Settings.ARM_PID_GROUND_SETPOINT);
          arm.enable();
          mechanum.mechanumRetract();
        } else if (driver.getPOV() == 270 && encoders.armInnerLimitSwitch.get()) {  // Left D-Pad - Sets the PID setpoint to cargo intake from the mecanum intake
          Logging.log("Shooter set point left");
          if (shootingAlign.isRunning()) {
            shootingAlign.cancel();
          }
          arm.setSetpoint(Settings.ARM_PID_INTAKE_SETPOINT);
          arm.enable();
          mechanum.mechanumExtend();
        } else if (arm.onTarget()  && arm.isEnabled()) {
          if (shootingAlign.isRunning()) {
            shootingAlign.cancel();
          }
          arm.disable();
        } else if (!encoders.armInnerLimitSwitch.get() && arm.getSetpoint() != Settings.ARM_PID_SHOOTING_SETPOINT + 1 && arm.isEnabled()) {
          if (shootingAlign.isRunning()) {
            shootingAlign.cancel();
          }
          arm.disable();
      }

      if (!encoders.armInnerLimitSwitch.get()) {
        Settings.ARM_POTENTIOMETER_OFFSET -= (encoders.armPotentiometerGet() - Settings.ARM_POTENTIOMETER_BASE);
        System.out.println("BUTTON HIT");
      }

    //Toggle Green Vision LED
    if(driver.getStartButtonPressed()){
      if(vision.ledRelay.get() == Relay.Value.kReverse){
        vision.ledRelay.set(Relay.Value.kForward);
      } else {
        vision.ledRelay.set(Relay.Value.kReverse);
      }
    }

    if (driver.getBackButtonPressed()) {
      visionAllignment.start();
    }
    
    //Vision Driver Assist

    // //Vision Variables
    // boolean visionAligned = false;
    // double visionSpeed;
    // // boolean visionActive = false;

    // //Start Align DriverAssist
    // if(driver.getBackButtonPressed()){
    //   //Block Controller from Driving Robot
    //   drive.ignoreController = true;
    //   //Set Alignment Variables
    //   visionAligned = false;

    //   try {
    //     //Start Vision Components, Get Alignment angle and start PID
    //     vision.startVision();
    //   } catch (visionErrorException e) {
    //     System.out.println(e.getMessage());
    //     //Shake Controller on Error
    //     driver.setRumble(RumbleType.kLeftRumble, 1);
    //     driver.setRumble(RumbleType.kRightRumble, 1);
    //   }
    // }

    // //Continue Alignment
    // if(vision.visionActive){
    //   vision.startAlignPID();
    //   try {
    //     if(!visionAligned) {
    //       //Check to see if aligned
    //       visionAligned = vision.checkAlign();
    //     } else {
    //       driver.setRumble(RumbleType.kLeftRumble, 0.5);
    //       driver.setRumble(RumbleType.kRightRumble, 0.5);
    //     }
    //   } catch (visionTargetDetectionException e) {
    //     //Shake Controller on Error
    //     System.out.println(e.getMessage());
        
    //     driver.setRumble(RumbleType.kLeftRumble, 1);
    //     driver.setRumble(RumbleType.kRightRumble, 1);
    //   }
    // }

    // //Stop Alignment and Vision

    // ultrasonics.printValues();
    // System.out.println("Is enabled? " + arm.isEnabled());
    // System.out.println("Setpoint: " + arm.getSetpoint());
    // System.out.println(encoders.armInnerLimitSwitch.get());

    /*if (!encoders.armInnerLimitSwitch.get() && arm.getSetpoint() != 111) {
      arm.disable();
      armPIDOffset += encoders.armPotentiometer.get();
    // } else if (!encoders.cargoButton.get()) {
    //   arm.setSetpoint(111);
    //   arm.enable();
    //   mechanum.mechanumRetract();
    } */

    Scheduler.getInstance().run();

  }

  /**
   * Drive function for external use
   * @param leftValue value for left motors, ranges from 1 to -1
   * @param rightValue value for right motors, ranges from 1 to -1
   * @return void
   */
  public void ExtDrive(double leftValue, double rightValue) {
    drive.ExtDrive(leftValue, rightValue);
  }

  /**
   * ArcadeDrive for external use
   * @param speed value for both motors, ranges from 1 to -1
   * @param angle value for motors, ranges from 1 to -1
   */
  public void ExtArcadeDrive(double speed, double angle){
    drive.ExtArcadeDrive(speed, angle);
  }

}
