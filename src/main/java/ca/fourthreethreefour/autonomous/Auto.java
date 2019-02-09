package ca.fourthreethreefour.autonomous;

import ca.fourthreethreefour.autonomous.pathweaver.PathFinder;
import ca.fourthreethreefour.teleop.Teleop;

/**
 * Auto class which contains everything autonomous period related
 * 
 * @author Cool Kornak and the Auto Team
 */
public class Auto {

  private Teleop teleop;
  private PathFinder pathFinder;

  public Auto(Teleop tele) {
    this.teleop = tele;
    this.pathFinder = new PathFinder();
  }
  /**
   * Function that contains 'tasks' designed to be ran at initalization
   */
  public void AutoInit() {
    pathFinder.pathRun();
  }

  /**
   * Periodic function that contains 'tasks' that are designed to be ran periodically.
   */
  public void AutoPeriodic() {

  }

  /**
   * Function that contains 'tasks' designed to be ran at disablization (Is that a word? Idk gonna use it though).
   */
  public void AutoDisabled() {

  }

}
