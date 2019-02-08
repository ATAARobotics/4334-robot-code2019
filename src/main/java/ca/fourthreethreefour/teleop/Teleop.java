package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.CargoOuttake;
import ca.fourthreethreefour.teleop.intake.CargoTransfer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.ButtonType;

public class Teleop {


  //Creates and initializes joystick objects and assigns them
  //to their respective ports on the Driver Station
  private XboxController driveStick = new XboxController(0);
  
  Cargo cargo = new Cargo();

  public void TeleopPeriodic() {
    if (driveStick.getYButton()) {
      new CargoOuttake(0.5, !driveStick.getYButton());
    }
    if (driveStick.getXButton()) {
      new CargoTransfer(0.5, !driveStick.getXButton());
    }

    double intakeSpeed = driveStick.getTriggerAxis(Hand.kRight) - driveStick.getTriggerAxis(Hand.kLeft);
    if (Math.abs(intakeSpeed) > 0.1) {
      cargo.intakeRotate(intakeSpeed*0.8);
    };
  }
}
