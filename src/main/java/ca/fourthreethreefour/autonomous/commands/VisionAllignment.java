/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.autonomous.commands;

import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.teleop.drivetrain.Drive;
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
  Drive drive;
  boolean visionAligned = false;

  public VisionAllignment(Vision vision, Teleop teleop, GenericHID driver, Drive drive) {
    this.vision = vision;
    this.teleop = teleop;
    this.driver = driver;
    this.drive = drive;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Block Controller from Driving Robot
      drive.ignoreController = true;
      //Set Alignment Variables
      visionAligned = false;

      try {
        //Start Vision Components, Get Alignment angle and start PID
        vision.startVision();
      } catch (visionErrorException e) {
        System.out.println(e.getMessage());
        //Shake Controller on Error
        driver.setRumble(RumbleType.kLeftRumble, 1);
        driver.setRumble(RumbleType.kRightRumble, 1);
      }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Continue Alignment
    if(vision.visionActive){
      vision.startAlignPID();
      try {
        if(!visionAligned) {
          //Check to see if aligned
          visionAligned = vision.checkAlign();
        } else {
          driver.setRumble(RumbleType.kLeftRumble, 0.5);
          driver.setRumble(RumbleType.kRightRumble, 0.5);
        }
      } catch (visionTargetDetectionException e) {
        //Shake Controller on Error
        System.out.println(e.getMessage());
        
        driver.setRumble(RumbleType.kLeftRumble, 1);
        driver.setRumble(RumbleType.kRightRumble, 1);
      }
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
    vision.stopAlignPID();
    drive.ignoreController = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    vision.stopVision();
    driver.setRumble(RumbleType.kLeftRumble, 0);
    driver.setRumble(RumbleType.kRightRumble, 0);
    vision.stopAlignPID();
    drive.ignoreController = false;
  }
}
