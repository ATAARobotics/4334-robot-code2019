package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.drivetrain.Drive;
import edu.wpi.first.wpilibj.Joystick;

public class Teleop {


  //Creates and initializes joystick objects and assigns them
  //to their respective ports on the Driver Station
  private Joystick operatorStick = new Joystick(1);
  private Joystick driveStick = new Joystick(0);
  private Drive drive = new Drive();

  public void TeleopInit() {
    
  }

  public void TeleopPeriodic() {
    drive.drive(driveStick);
    
    if(driveStick.getRawButton(3)) {
      drive.gearShift();
    }
  }

  public void ExtDrive(double driveValue, double turnValue) {
    drive.ExtDrive(driveValue, turnValue);
  }
}
