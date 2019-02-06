package ca.fourthreethreefour;

import edu.wpi.first.wpilibj.Joystick;

public class Teleop {


  //Creates and initializes joystick objects and assigns them
  //to their respective ports on the Driver Station
  private Joystick gunnerStick = new Joystick(1);
  private Joystick driveStick = new Joystick(0);
  private Drive drive = new Drive();

  public void TeleopPeriodic() {
    drive.drive(driveStick);
    
    if(driveStick.getRawButton(3)) {
      drive.gearShift();
    }
    
    if (driveStick.getRawButton(1)) {
      drive.speedShift();
    }

  }
}
