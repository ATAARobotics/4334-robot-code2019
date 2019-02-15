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

public class Teleop {


  //Creates and initializes various objects needed in teleop
  private XboxController driver = new XboxController(0);
  private XboxController operator = new XboxController(1);
  
  private Cargo cargo = new Cargo();
  private Encoders encoders = new Encoders();
  private Hatch hatch = new Hatch();
  private Mechanum mechanum = new Mechanum();
  public Drive drive = new Drive();

  public static boolean cargoOuttake;

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

    double intakeSpeed = driver.getTriggerAxis(Hand.kRight) - driver.getTriggerAxis(Hand.kLeft);
    if (Math.abs(intakeSpeed) > 0.05) {
      cargo.intakeRotate(intakeSpeed*Settings.INTAKE_ROTATE_SPEED);
    } else {
      cargo.intakeRotate(0);
    };

    if (operator.getTriggerAxis(Hand.kLeft) > 0.05) {
      cargo.cargoOuttake(operator.getTriggerAxis(Hand.kLeft));
    } else if (driver.getBumper(Hand.kRight) && encoders.cargoButton.get()) {
      cargo.cargoTransfer(1);
      mechanum.mechanumRoller(1);
    } else {
      cargo.stop();
      if (driver.getBumper(Hand.kLeft)) {
        mechanum.mechanumRoller(-1);
      } else {
        mechanum.mechanumRoller(0);
      }
    }
    
    if (driver.getStickButtonPressed(Hand.kRight)) {
      drive.gearShift();
    }

    if (driver.getStickButtonPressed(Hand.kLeft)) {
      cargoOuttake =  !cargoOuttake;
      Logging.put(Settings.DRIVE_DIRECTION_ENTRY, cargoOuttake);
    }

    if (driver.getAButtonPressed()) {
      hatch.hatchShift();
    }

    if (operator.getBumperPressed(Hand.kRight)) {
      mechanum.mechanumShift();
    }

    if (operator.getAButtonPressed()) {
      Logging.log("Shooter set point A");
    } else if (driver.getBButtonPressed()) {
      Logging.log("Shooter set point B");
    } else if (driver.getXButtonPressed()) {
      Logging.log("Shooter set point X");
    } else if (driver.getYButtonPressed()) {
      Logging.log("Shooter set point Y");
    }

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
}
