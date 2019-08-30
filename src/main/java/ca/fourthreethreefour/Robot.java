/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour;

import ca.fourthreethreefour.autonomous.Auto;
import ca.fourthreethreefour.settings.Settings;
import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  Auto auto = new Auto();
  Settings shuffleboard = new Settings();
  Teleop teleop;
  Encoders encoders;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    shuffleboard.ShuffleInit();
    Timer.delay(0.1);
    this.teleop = new Teleop();
    this.encoders = teleop.encoders;
    encoders.initalizeNavX();

    teleop.RobotInit();
    
    System.out.println(Settings.HATCH_SOLENOID_PORT_1);
    System.out.println(Settings.HATCH_SOLENOID_PORT_2);
    System.out.println("-------");
  }

  @Override
  public void disabledInit() {
    teleop.arm.disable();
    teleop.vision.visionAlignPID.disable();
    teleop.sideWinder.disable();
  }
  
  /**
   * This function is called periodically while disabled.
   */
  @Override
  public void disabledPeriodic() {
      shuffleboard.settingsPeriodic();
    // System.out.println(shuffleboard.EXAMPLE_PORT);
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    auto.AutoInit(); // Runs everything set in the .AutoInit() function.
    teleop.TeleopInit();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    auto.AutoPeriodic(); // Runs everything set in the .AutoPeriodic() function.
    teleop.TeleopPeriodic();
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    auto.AutoDisabled(); // Runs everything set in the .AutoDisabled() function.
    teleop.TeleopInit();
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    teleop.TeleopPeriodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
