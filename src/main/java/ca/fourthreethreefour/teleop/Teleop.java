package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.intake.Arm;
import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.Hatch;
import ca.fourthreethreefour.teleop.intake.Mechanum;
import ca.fourthreethreefour.teleop.systems.Encoders;
import ca.fourthreethreefour.vision.exceptions.visionErrorException;
import ca.fourthreethreefour.vision.exceptions.visionTargetDetectionException;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.shuffleboard.Settings;
import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;
import ca.fourthreethreefour.vision.Vision;

public class Teleop {

  // Initialize miscellaneous configuration values
  private static int armPIDSetpoint = 90;
  private static int armPIDScale = 1800;
  private static int armPIDOffset = -918; // Todo: Tune offset at competition
  private static final int armPIDAcceptableError = 2;
  private static final int armPIDHatchIntakeOuttakeSetpoint = 90;
  private static final int armPIDCargoOuttakeSetpoint = 110;
  private static final int armPIDHatchIntakeSetpoint = 200;
  private static final int armPIDCargoIntakeSetpoint = 10;

  //Creates and initializes various objects needed in teleop
  private XboxController driver = new XboxController(Settings.DRIVER_CONTROLLER_PORT);
  private Cargo cargo = new Cargo();
  public Encoders encoders = new Encoders();
  public Hatch hatch = new Hatch(encoders);
  private Mechanum mechanum = new Mechanum();
  public Arm arm = new Arm(encoders, cargo);
  public Drive drive = new Drive();
  private Ultrasonics ultrasonics = new Ultrasonics();
  public Vision vision = new Vision(this);
  public PIDController armPIDLeft;
  public PIDController armPIDRight;

  public static boolean cargoOuttake;

  public void RobotInit() {
    ultrasonics.enable();
    ultrasonics.ultrasonicPollingThread();
    encoders.potentiometerInit(armPIDOffset);
    armPIDLeft = new PIDController(0.05, 0, 0, encoders.armPotentiometer, cargo.intakeRotateMotor1);
    armPIDRight = new PIDController(0.05, 0, 0, encoders.armPotentiometer, cargo.intakeRotateMotor2);
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
    arm.setAbsoluteTolerance(armPIDAcceptableError);
    cargoOuttake = true;
  }
  

  /**
   * Runs periodically during teleop
   * @return void
   */
  public void TeleopPeriodic() {

    //Stop Alignment and Vision

    //If any axis on controller passes threshold, disable vision alignment and return driver control.
    if(Math.abs(driver.getY(Hand.kLeft)) > 0.05 || Math.abs(driver.getY(Hand.kRight)) > 0.05 || Math.abs(driver.getX(Hand.kLeft)) > 0.05 || Math.abs(driver.getX(Hand.kRight)) > 0.05 || driver.getXButton() || driver.getYButton()){
      vision.stopVision();
      driver.setRumble(RumbleType.kLeftRumble, 0);
      driver.setRumble(RumbleType.kRightRumble, 0);
      if(vision.isAlignEnabled()) {
        vision.stopAlignPID();
      }
      if(hatch.isMoveEnabled()){
        hatch.hatchAlignPID.disable();
      }
      drive.ignoreController = false;
    }

    drive.drive(driver, cargoOuttake);

    System.out.println(arm.returnPIDInput());
    // Logging.log("PID: " + arm.returnPIDInput());
    // double intakeSpeed = driver.getTriggerAxis(Hand.kRight) - driver.getTriggerAxis(Hand.kLeft);
    // if (Math.abs(intakeSpeed) > 0.05) {
    //   cargo.intakeRotate(intakeSpeed*Settings.INTAKE_ROTATE_SPEED);
    // } else {
    //   cargo.intakeRotate(0);
    // };

    if (driver.getTriggerAxis(Hand.kLeft) > 0.05) {
      cargo.cargoOuttake(driver.getTriggerAxis(Hand.kLeft));
      mechanum.mechanumRoller(-driver.getTriggerAxis(Hand.kLeft));
    } else if (driver.getTriggerAxis(Hand.kRight) > 0.05 /* && encoders.cargoButton.get() */) {
      cargo.cargoTransfer(driver.getTriggerAxis(Hand.kRight));
      mechanum.mechanumRoller(driver.getTriggerAxis(Hand.kRight));
      if (!encoders.cargoButton.get()) {
      //           armPIDSetpoint = armPIDCargoOuttakeSetpoint + 1;
      //           armPIDLeft.setSetpoint(armPIDSetpoint);
      //           armPIDRight.setSetpoint(armPIDSetpoint);
      //           armPIDLeft.enable();
      //           armPIDRight.enable();
      //           mechanum.mechanumRetract();
              Logging.log("Auto shoot setpoint");
              arm.setSetpoint(armPIDCargoOuttakeSetpoint + 1);
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
    
    if (driver.getStickButtonPressed(Hand.kRight)) {
      drive.gearShift();
    }

    if (driver.getStickButtonReleased(Hand.kLeft) && Settings.REVERSABLE_CONTROLS) {
      cargoOuttake =  !cargoOuttake;
    }
    Logging.put(Settings.DRIVE_DIRECTION_ENTRY, cargoOuttake);

    if (driver.getAButton()) {
      hatch.hatchSolenoidOut();
    } else {
      hatch.hatchSolenoidIn();
    }

    if (driver.getBButtonReleased()) {
      mechanum.mechanumShift();
    }

    if (driver.getBumper(Hand.kLeft)) {
        arm.armRotate(1);
    } else if (driver.getBumper(Hand.kRight)) {
      if (encoders.armInnerLimitSwitch.get()) {
        arm.armRotate(-1);
      }
    } else if (!arm.isEnabled()) {
      arm.armRotate(0);
    }

      // Up D-Pad - Sets the PID setpoint to hatch outtake and retracts the mecanum intake
        if (driver.getPOV() == 0) { // TODO make the setpoints shuffleboard stuff
          Logging.log("Shooter set point up");
          arm.setSetpoint(armPIDHatchIntakeOuttakeSetpoint);
          arm.enable();
          mechanum.mechanumRetract();
        } else if (driver.getPOV() == 90) {  // Right D-Pad - Sets the PID setpoint to cargo outtake and retracts the mecanum intake
          Logging.log("Shooter set point right");
          arm.setSetpoint(armPIDCargoOuttakeSetpoint);
          arm.enable();
          mechanum.mechanumRetract();
        } else if (driver.getPOV() == 180) {  // Down D-Pad - Sets the PID setpoint to hatch ground and retracts the mecanum intake
          Logging.log("Shooter set point down");
          arm.setSetpoint(armPIDHatchIntakeSetpoint);
          arm.enable();
          mechanum.mechanumRetract();
        } else if (driver.getPOV() == 270 && encoders.armInnerLimitSwitch.get()) {  // Left D-Pad - Sets the PID setpoint to cargo intake from the mecanum intake
          Logging.log("Shooter set point left");
          arm.setSetpoint(armPIDCargoIntakeSetpoint);
          arm.enable();
          mechanum.mechanumExtend();
        } else if (arm.onTarget()  && arm.isEnabled()) {
          arm.disable();
        } else if (!encoders.armInnerLimitSwitch.get() && arm.getSetpoint() != armPIDCargoOuttakeSetpoint + 1 && arm.isEnabled()) {
          arm.disable();
          armPIDOffset += encoders.armPotentiometer.get();
        }

    //Hatch Side Movement
    if(driver.getYButton() && encoders.hatchHallEffectRight.get()){
      //Move Right
      hatch.hatchAlignMotor.set(0.01);
    } else if(driver.getXButton() && encoders.hatchHallEffectLeft.get()){
      //Move Left
      hatch.hatchAlignMotor.set(-0.01);
    } else {
      //Stop Motor
      hatch.hatchAlignMotor.set(0);
    }

    if(!encoders.hatchHallEffectRight.get()){
      System.out.println("Right HallEffect");
    }

    if(!encoders.hatchHallEffectLeft.get()){
      System.out.println("Left HallEffect");
    }



    //Vision Driver Assist

    //Vision Variables
    boolean visionAligned = false;
    double visionSpeed;

    //Start Align DriverAssist
    if(driver.getBackButtonReleased()){
      //Block Controller from Driving Robot
      drive.ignoreController = true;
      //Set Alignment Variables
      vision.visionActive = true;
      visionAligned = false;

      try {
        //Start Vision Components, Get Alignment angle and start PID
        vision.startVision();
        vision.startAlignPID();
        hatch.hatchAlignPID.setSetpoint(vision.getPiRotation());
        hatch.hatchAlignPID.enable();
      } catch (visionErrorException e) {
        System.out.println(e.getMessage());
        //Shake Controller on Error
        driver.setRumble(RumbleType.kLeftRumble, 1);
        driver.setRumble(RumbleType.kRightRumble, 1);
      }
    }

    //Continue Alignment
    if(vision.visionActive){
      try {
        if(!visionAligned) {
          //Check to see if aligned
          visionAligned = vision.checkAlign();
        } else {
          driver.setRumble(RumbleType.kLeftRumble, 0.5);
          driver.setRumble(RumbleType.kRightRumble, 0.5);
        }
      } catch (visionTargetDetectionException e) {
        //Shake Controller on Error
        driver.setRumble(RumbleType.kLeftRumble, 1);
        driver.setRumble(RumbleType.kRightRumble, 1);
      }
    }

    //Toggle Green Vision LED
    if(driver.getStartButtonPressed()){
      if(vision.ledRelay.get() == Relay.Value.kReverse){
        vision.ledRelay.set(Relay.Value.kOff);
      } else if (vision.ledRelay.get() == Relay.Value.kOff){
        vision.ledRelay.set(Relay.Value.kOn);
        vision.ledRelay.set(Relay.Value.kReverse);
      }
    }

    if(encoders.hatchHallEffectRight.get() || encoders.hatchHallEffectLeft.get()){
      hatch.hatchAlignPID.disable();
    }

    if(hatch.isMoveEnabled() && hatch.hatchAlignPID.onTarget()){
      hatch.hatchAlignPID.disable();
    }


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
    } else*/

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
