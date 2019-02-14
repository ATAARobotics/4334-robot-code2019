package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.Hatch;
import ca.fourthreethreefour.teleop.intake.HatchRelease;
import ca.fourthreethreefour.teleop.intake.Mechanum;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import ca.fourthreethreefour.teleop.drivetrain.Drive;

public class Teleop {


  //Creates and initializes joystick objects and assigns them
  //to their respective ports on the Driver Station
  private XboxController driveStick = new XboxController(0);
  
  public Cargo cargo = new Cargo();
  public Encoders encoders = new Encoders();
  public Hatch hatch = new Hatch();
  private Mechanum mechanum = new Mechanum();
  public Drive drive = new Drive();

  /**
   * Runs as the start of teleop
   * @return void
   */
  public void TeleopInit() {
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
    }

    double intakeSpeed = driveStick.getTriggerAxis(Hand.kRight) - driveStick.getTriggerAxis(Hand.kLeft);
    if (Math.abs(intakeSpeed) > 0.05) {
      cargo.intakeRotate(intakeSpeed*0.25);

    };
    
    drive.drive(driveStick);
    
    if (driveStick.getStickButtonPressed(Hand.kRight)) {
      drive.gearShift();
    }

    if (driveStick.getBumperPressed(Hand.kLeft)) {
      new HatchRelease(this, 2);
    }

    if (driveStick.getStartButton()) {
      mechanum.mechanumRoller(-1);
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
