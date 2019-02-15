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
  private XboxController driveStick = new XboxController(0);
  
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
    drive.gearShiftSolenoid.set(drive.gearLow);
    mechanum.mechanumSolenoid.set(Value.kReverse);
    cargoOuttake = true;
  }
  

  /**
   * Runs periodically during teleop
   * @return void
   */
  public void TeleopPeriodic() {
    // if (driveStick.getYButton()) {
      // cargo.cargoOuttake(1);
    // } else {
      // cargo.cargoOuttake(0);
    // }
    if (driveStick.getYButton()) {
      cargo.cargoOuttake(1);
    } else if (driveStick.getXButton() && encoders.cargoButton.get()) {
      cargo.cargoTransfer(1);
      mechanum.mechanumRoller(1);
    } else {
      cargo.stop();
      if (driveStick.getStartButton()) {
        mechanum.mechanumRoller(-1);
      } else {
        mechanum.mechanumRoller(0);
      }
    }

    double intakeSpeed = driveStick.getTriggerAxis(Hand.kRight) - driveStick.getTriggerAxis(Hand.kLeft);
    if (Math.abs(intakeSpeed) > 0.05) {
      cargo.intakeRotate(intakeSpeed*Settings.INTAKE_ROTATE_SPEED);
    } else {
      cargo.intakeRotate(0);
    };
    
    drive.drive(driveStick, cargoOuttake);
    
    if (driveStick.getStickButtonPressed(Hand.kRight)) {
      drive.gearShift();
    }

    if (driveStick.getStickButtonPressed(Hand.kLeft)) {
      cargoOuttake =  !cargoOuttake;
      Logging.put(Settings.DRIVE_DIRECTION_ENTRY, cargoOuttake);
    }

    if (driveStick.getBumperPressed(Hand.kLeft)) {
      hatch.hatchShift();
    }

    if (driveStick.getBumperPressed(Hand.kRight)) {
      mechanum.mechanumShift();
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
