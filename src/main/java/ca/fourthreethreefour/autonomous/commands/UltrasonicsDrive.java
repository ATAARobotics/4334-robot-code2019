/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.autonomous.commands;

import ca.fourthreethreefour.settings.Settings;
import ca.fourthreethreefour.teleop.Teleop;
import edu.wpi.first.wpilibj.command.Command;

public class UltrasonicsDrive extends Command {

  Teleop teleop;
  double ultrasonicFinal;
  public UltrasonicsDrive(Teleop teleop, double ultrasonicFinal) {
    this.teleop = teleop;
    this.ultrasonicFinal = ultrasonicFinal;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    teleop.ExtArcadeDrive(Settings.DRIVE_SPEED, 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (teleop.ultrasonics.getUltrasonicFrontLeftValue() <= ultrasonicFinal) {
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    teleop.ExtArcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
