/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.autonomous.commands;

import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.vision.Vision;
import ca.fourthreethreefour.vision.exceptions.visionErrorException;
import ca.fourthreethreefour.vision.exceptions.visionTargetDetectionException;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

public class VisionAllignment extends Command {
  Vision vision;
  Teleop teleop;
  GenericHID driver;
  boolean visionAligned = false;

  public VisionAllignment(Vision vision, Teleop teleop, GenericHID driver) {
    this.vision = vision;
    this.teleop = teleop;
    this.driver = driver;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    try {
      vision.startVision();
      vision.startAlignPID();
    } catch (visionErrorException e) {
      System.out.println(e.getMessage());
      driver.setRumble(RumbleType.kLeftRumble, 1);
      driver.setRumble(RumbleType.kRightRumble, 1);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    try {
      visionAligned = vision.alignDrive();
    } catch (visionTargetDetectionException e) {
      driver.setRumble(RumbleType.kLeftRumble, 1);
      driver.setRumble(RumbleType.kRightRumble, 1);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return visionAligned;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    vision.stopVision();
    driver.setRumble(RumbleType.kLeftRumble, 0);
    driver.setRumble(RumbleType.kRightRumble, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    vision.stopVision();
    driver.setRumble(RumbleType.kLeftRumble, 0);
    driver.setRumble(RumbleType.kRightRumble, 0);
    vision.stopAlignPID();
    
  }
}
