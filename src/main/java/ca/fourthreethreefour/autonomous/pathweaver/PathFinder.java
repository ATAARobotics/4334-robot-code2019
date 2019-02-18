/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.autonomous.pathweaver;

//import com.kauailabs.navx.frc.AHRS;

import ca.fourthreethreefour.teleop.Teleop;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * Add your docs here.
 */
public class PathFinder {

    //Stores various variables in the pathfinder class
    private static final int k_ticks_per_rev = 30000;
    private static final double k_wheel_diameter = 0.1524;
    private static final double k_max_velocity = 4.5;

    private static final String k_path_name = "Straight2";

    private EncoderFollower drivetrainControllerLeft;
    private EncoderFollower drivetrainControllerRight;
  
    private Notifier m_follower_notifier;

    private Teleop teleop;
    private ADXRS450_Gyro m_gyro;

    /**
     * 
     */
    public PathFinder(Teleop teleop) {
        this.teleop = teleop;
        this.m_gyro = teleop.encoder.gyro;
    }

    public void pathRun() {

        //Sets the left and right motor sensors to their
        //original positions when pathfinder is running
        teleop.drive.frontLeftMotor.setSelectedSensorPosition(0);
        teleop.drive.frontRightMotor.setSelectedSensorPosition(0);
        
        //Disables drivetrain safety when pathfinder is running
        teleop.drive.driveTrain.setSafetyEnabled(false);
        
        Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
        Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");

        drivetrainControllerLeft = new EncoderFollower(left_trajectory);
        drivetrainControllerRight = new EncoderFollower(right_trajectory);

        drivetrainControllerLeft.configureEncoder(teleop.drive.frontLeftMotor.getSelectedSensorPosition(), k_ticks_per_rev, k_wheel_diameter);
        // You must tune the PID values on the following line!
        drivetrainControllerLeft.configurePIDVA(0.3, 0.15, 0.1, 1 / k_max_velocity, 0);

        drivetrainControllerRight.configureEncoder(teleop.drive.frontRightMotor.getSelectedSensorPosition(), k_ticks_per_rev, k_wheel_diameter);
        // You must tune the PID values on the following line!
        drivetrainControllerRight.configurePIDVA(0.3, 0.15, 0.1, 1 / k_max_velocity, 0);
        
        m_follower_notifier = new Notifier(this::followPath);
        m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    }

    private void followPath() {
        if (drivetrainControllerLeft.isFinished() || drivetrainControllerRight.isFinished()) {
          m_follower_notifier.stop();
        } else {
            double left_speed = drivetrainControllerLeft.calculate(teleop.drive.frontLeftMotor.getSelectedSensorPosition());
            double right_speed = drivetrainControllerRight.calculate(teleop.drive.frontRightMotor.getSelectedSensorPosition());
            double heading = m_gyro.getAngle();
            double desired_heading = Pathfinder.r2d(drivetrainControllerLeft.getHeading());
            double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
            double turn =  0.8 * (-1.0/80.0) * heading_difference;
            
            teleop.ExtDrive(-left_speed - turn, right_speed + turn);
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Drivetrain Left Code: " + left_speed);
            System.out.println("Drivetrain Right Code: " + right_speed);
            System.out.println("Drivetrain Left Actual: " + teleop.drive.leftSpeedControllerGroup.get());
            System.out.println("Drivetrain Right Actual: " + teleop.drive.rightSpeedControllerGroup.get());
            System.out.println("Gyro Value: " + heading);
            System.out.println("Desired Gyro: " + desired_heading);
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    
    public void stop() {
        // m_follower_notifier.stop();
        teleop.ExtDrive(0, 0);
    }
}
