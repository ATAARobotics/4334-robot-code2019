package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.Hatch;
import ca.fourthreethreefour.teleop.intake.Mechanum;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.shuffleboard.Settings;
import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;

public class Teleop {


  //Creates and initializes various objects needed in teleop
  private XboxController driver = new XboxController(Settings.DRIVER_CONTROLLER_PORT);
  // private XboxController operator = new XboxController(Settings.OPERATOR_CONTROLLER_PORT);
  
  private Cargo cargo = new Cargo();
  private Encoders encoders = new Encoders();
  public Hatch hatch = new Hatch();
  private Mechanum mechanum = new Mechanum();
  public Drive drive = new Drive();
  public Ultrasonics ultrasonics = new Ultrasonics();

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

  public void ExtArcadeDrive(double speed, double angle){
    drive.ExtArcadeDrive(speed, angle);
  }
}
