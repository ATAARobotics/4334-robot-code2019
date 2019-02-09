/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.autonomous.pathweaver;

import ca.fourthreethreefour.teleop.Teleop;
import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * Add your docs here.
 */
public class PathFinder {

    private static final int k_ticks_per_rev = 1024;
    private static final double k_wheel_diameter = 4.0 / 12.0;
    private static final double k_max_velocity = 10;

    private static final String k_path_name = "Test";

    private EncoderFollower m_left_follower;
    private EncoderFollower m_right_follower;
  
    private Notifier m_follower_notifier;

    private Teleop teleop;

    public PathFinder(Teleop teleop) {
        this.teleop = teleop;
    }

    public void pathRun() {
        Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
        Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");

        m_left_follower = new EncoderFollower(left_trajectory);
        m_right_follower = new EncoderFollower(right_trajectory);

        m_left_follower.configureEncoder(m_left_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
        // You must tune the PID values on the following line!
        m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);

        m_right_follower.configureEncoder(m_right_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
        // You must tune the PID values on the following line!
        m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
        
        m_follower_notifier = new Notifier(this::followPath);
        m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    }

    private void followPath() {
        if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
          m_follower_notifier.stop();
        } else {
            double left_speed = m_left_follower.calculate(m_left_encoder.get());
            double right_speed = m_right_follower.calculate(m_right_encoder.get());
            double heading = m_gyro.getAngle();
            double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
            double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
            double turn =  0.8 * (-1.0/80.0) * heading_difference;
            
            // leftMotors.set(left_speed + turn);
            // rightMotors.set(right_speed - turn);
            teleop.ExtDrive(driveValue, turnValue);
        }
    }
    
    public void stop() {
        m_follower_notifier.stop();
        leftMotors.set(0);
        rightMotors.set(0);
    }
}
