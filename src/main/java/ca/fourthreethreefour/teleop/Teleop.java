package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.drivetrain.Drive;
import edu.wpi.first.wpilibj.XboxController;

public class Teleop {


  //Creates and initializes joystick objects and assigns them
  //to their respective ports on the Driver Station
  private XboxController operatorStick = new XboxController(1);
  private XboxController driveStick = new XboxController(0);
  private Drive drive = new Drive();

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
    drive.drive(driveStick);
    
    if(driveStick.getBButton()) {
      drive.gearShift();
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
