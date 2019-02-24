/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour;

import ca.fourthreethreefour.autonomous.Auto;
import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.shuffleboard.Settings;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import java.io.IOException;

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
  AHRS navX;
  private static final int encoderTicksPerRevolution = 30000;
  private static final double wheelDiameter = 0.1524;
  private static final double maxVelocity = 4.5;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    shuffleboard.ShuffleInit(teleop);
    this.teleop = new Teleop();
    teleop.RobotInit();
    try {
      // Initializes the navX object on the roboRIO's MXP port and resets it
      navX = new AHRS(SPI.Port.kMXP);
      navX.reset();
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
    }
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
    auto.AutoInit(); // Runs everything set in the .AutoInit() function.
    try {
      // Gets and sets the specified autonomous routine trajectories for the left and right side of the drivetrain
      Trajectory left_trajectory = PathfinderFRC.getTrajectory("output/" + teleop.encoders.pathWeaverPathName + ".left");
      Trajectory right_trajectory = PathfinderFRC.getTrajectory("output/" + teleop.encoders.pathWeaverPathName + ".right");
      teleop.encoders.drivetrainLeftController = new EncoderFollower(left_trajectory);
      teleop.encoders.drivetrainRightController = new EncoderFollower(right_trajectory);

      // Configures the drivetrain left and right side controllers to use the appropriate configurations
      teleop.encoders.drivetrainLeftController.configureEncoder
              (teleop.drive.frontLeftMotor.getSelectedSensorPosition(), encoderTicksPerRevolution, wheelDiameter);
      teleop.encoders.drivetrainRightController.configureEncoder
              (teleop.drive.frontRightMotor.getSelectedSensorPosition(), encoderTicksPerRevolution, wheelDiameter);
      teleop.encoders.drivetrainLeftController.configurePIDVA(1.0, 0.15, 0.1, 1 / maxVelocity, 0);
      teleop.encoders.drivetrainRightController.configurePIDVA(1.0, 0.15, 0.1, 1 / maxVelocity, 0);

      // Sets up the autonomous controller and starts it
      teleop.encoders.autoController = new Notifier(this::followPath);
      teleop.encoders.autoController.startPeriodic(left_trajectory.get(0).dt);
    } catch (IOException e) {
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    auto.AutoPeriodic(); // Runs everything set in the .AutoPeriodic() function.
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

  private void followPath() {
    if (teleop.encoders.drivetrainLeftController.isFinished() || teleop.encoders.drivetrainRightController.isFinished()) {
      teleop.encoders.autoController.stop();
    } else {
      double left_speed = teleop.encoders.drivetrainLeftController.calculate
              (teleop.drive.frontLeftMotor.getSelectedSensorPosition());
      double right_speed = teleop.encoders.drivetrainRightController.calculate
              (teleop.drive.frontRightMotor.getSelectedSensorPosition());
      double heading = navX.getAngle();
      double desired_heading = Pathfinder.r2d(teleop.encoders.drivetrainLeftController.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn = 0.8 * (-1.0 / 80.0) * heading_difference;
      teleop.drive.leftSpeedControllerGroup.set(-left_speed - turn);
      teleop.drive.rightSpeedControllerGroup.set(right_speed + turn);
      System.out.println("--------------------------------------------------------------------------");
      System.out.println("Drivetrain Left Code: " + left_speed);
      System.out.println("Drivetrain Right Code: " + right_speed);
      System.out.println("Drivetrain Left Actual: " + teleop.drive.leftSpeedControllerGroup.get());
      System.out.println("Drivetrain Right Actual: " + teleop.drive.rightSpeedControllerGroup.get());
      System.out.println("--------------------------------------------------------------------------");
    }
  }
}
