/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour;

import ca.fourthreethreefour.shuffleboard.Settings;
import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  Settings shuffleboard = new Settings();
  Teleop teleop = new Teleop();
  Ultrasonics ultrasonics = new Ultrasonics();
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    shuffleboard.ShuffleInit();
    teleop.TeleopInit();
    ultrasonics.enable();
    ultrasonics.ultrasonicPollingThread();
  }

  /**
   * This function is called periodically while disabled.
   */
  @Override
  public void disabledPeriodic() {
    shuffleboard.ShufflePeriodic();
    // System.out.println(shuffleboard.EXAMPLE_PORT);
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
