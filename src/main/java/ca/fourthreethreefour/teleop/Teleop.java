package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.systems.Ultrasonics;
import edu.wpi.first.wpilibj.Joystick;

public class Teleop {

  private Ultrasonics ultrasonics = new Ultrasonics();

  public void RobotInit() {
    ultrasonics.enable();
    ultrasonics.ultrasonicPollingThread();
  }

  public void TeleopInit() {
    
  }

  public void TeleopPeriodic() {
    ultrasonics.printValues();
  }

}
