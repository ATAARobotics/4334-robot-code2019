package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.Hatch;
import ca.fourthreethreefour.teleop.intake.Mechanum;
import ca.fourthreethreefour.teleop.systems.Encoders;
import ca.fourthreethreefour.vision.exceptions.visionErrorException;
import ca.fourthreethreefour.vision.exceptions.visionTargetDetectionException;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.shuffleboard.Settings;
import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;
import ca.fourthreethreefour.vision.Vision;

public class Teleop {


  //Creates and initializes various objects needed in teleop
  private XboxController driver = new XboxController(Settings.DRIVER_CONTROLLER_PORT);
  // private XboxController operator = new XboxController(Settings.OPERATOR_CONTROLLER_PORT);
  
  private Cargo cargo = new Cargo();
  public Encoders encoders = new Encoders();
  private Hatch hatch = new Hatch();
  private Mechanum mechanum = new Mechanum();
  public Drive drive = new Drive();
  private Ultrasonics ultrasonics = new Ultrasonics();
  public Vision vision = new Vision(this);

  public static boolean cargoOuttake;

  public void RobotInit() {
    ultrasonics.enable();
    ultrasonics.ultrasonicPollingThread();
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
    cargoOuttake = true;
  }
  

  /**
   * Runs periodically during teleop
   * @return void
   */
  public void TeleopPeriodic() {

    //Stop Alignment and Vision

    //If any axis on controller passes threshold, disable vision alignment and return driver control.
    if(Math.abs(driver.getY(Hand.kLeft)) > 0.05 || Math.abs(driver.getY(Hand.kRight)) > 0.05 || Math.abs(driver.getX(Hand.kLeft)) > 0.05 || Math.abs(driver.getX(Hand.kRight)) > 0.05){
      vision.stopVision();
      driver.setRumble(RumbleType.kLeftRumble, 0);
      driver.setRumble(RumbleType.kRightRumble, 0);
      if(!vision.isEnabled()) {
        vision.stopAlignPID();
      }
      drive.ignoreController = false;
    }

    drive.drive(driver, cargoOuttake);

    // double intakeSpeed = driver.getTriggerAxis(Hand.kRight) - driver.getTriggerAxis(Hand.kLeft);
    // if (Math.abs(intakeSpeed) > 0.05) {
    //   cargo.intakeRotate(intakeSpeed*Settings.INTAKE_ROTATE_SPEED);
    // } else {
    //   cargo.intakeRotate(0);
    // };
    if (driver.getBumper(Hand.kLeft)) {
      cargo.intakeRotate(Settings.INTAKE_ROTATE_SPEED);
    } else if (driver.getBumper(Hand.kRight) && encoders.armInnerLimitSwitch.get()) {
      cargo.intakeRotate(-Settings.INTAKE_ROTATE_SPEED);
    } else {
      cargo.intakeRotate(0);
    }

    if (driver.getTriggerAxis(Hand.kLeft) > 0.05) {
      cargo.cargoOuttake(driver.getTriggerAxis(Hand.kLeft));
      mechanum.mechanumRoller(-driver.getTriggerAxis(Hand.kLeft));
    } else if (driver.getTriggerAxis(Hand.kRight) > 0.05 && encoders.cargoButton.get()) {
      cargo.cargoTransfer(driver.getTriggerAxis(Hand.kRight));
      mechanum.mechanumRoller(driver.getTriggerAxis(Hand.kRight));
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

    if (driver.getStickButtonPressed(Hand.kLeft)) {
      cargoOuttake =  !cargoOuttake;
      Logging.put(Settings.DRIVE_DIRECTION_ENTRY, cargoOuttake);
    }

    if (driver.getAButtonReleased()) {
      hatch.hatchShift();
    }

    if (driver.getBButtonReleased()) {
      mechanum.mechanumShift();
    }

    // if (operator.getAButtonPressed()) {
    //   Logging.log("Shooter set point A");
    // } else if (operator.getBButtonPressed()) {
    //   Logging.log("Shooter set point B");
    // } else if (operator.getXButtonPressed()) {
    //   Logging.log("Shooter set point X");
    // } else if (operator.getYButtonPressed()) {
    //   Logging.log("Shooter set point Y");
    // }
    if (driver.getPOV() == 0) {
        Logging.log("Shooter set point up");
    } else if (driver.getPOV() == 90) {
      Logging.log("Shooter set point right");
    } else if (driver.getPOV() == 180) {
      Logging.log("Shooter set point down");
    } else if (driver.getPOV() == 270) {
      Logging.log("Shooter set point left");
    }

    //Vision Driver Assist

    //Vision Variables
    boolean visionAligned = false;
    double visionSpeed;

    //Start Align DriverAssist
    if(driver.getBackButtonPressed()){
      //Block Controller from Driving Robot
      drive.ignoreController = true;
      //Set Alignment Variables
      vision.visionActive = true;
      visionAligned = false;

      try {
        //Start Vision Components, Get Alignment angle and start PID
        vision.startVision();
        vision.startAlignPID();
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


    // ultrasonics.printValues();

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
