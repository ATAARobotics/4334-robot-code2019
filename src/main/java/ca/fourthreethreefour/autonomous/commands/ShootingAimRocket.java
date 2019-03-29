/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.autonomous.commands;

import ca.fourthreethreefour.autonomous.Equations;
import ca.fourthreethreefour.settings.Settings;
import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.teleop.intake.Arm;
//import ca.fourthreethreefour.teleop.systems.Encoders;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;
//import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

public class ShootingAimRocket extends Command {

  Teleop teleop;
  Ultrasonics ultrasonics;
  Arm arm;

  public ShootingAimRocket(Teleop teleop, Ultrasonics ultrasonics, Arm arm) {
    this.teleop = teleop;
    this.ultrasonics = ultrasonics;
    this.arm = arm;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    double distance = (ultrasonics.getUltrasonicFrontLeftValue() + ultrasonics.getUltrasonicFrontRightValue())/2;
    if (distance < 3 && distance >= 0) {
      arm.setSetpoint(180 - new Equations().shootRocketFormula(distance) + Settings.ROCKET_EQUATION_CORRECTION);
    } else {
      arm.setSetpoint(Settings.ARM_PID_SHOOTING_SETPOINT);
      System.out.println("Shooting Align Canceled. Distance off");
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!arm.isEnabled()) {
      arm.enable();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return arm.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    arm.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    arm.disable();
  }
}
