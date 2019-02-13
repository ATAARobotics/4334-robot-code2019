/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import ca.fourthreethreefour.teleop.Teleop;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Add your docs here.
 */
public class HatchRelease extends TimedCommand {

  Teleop teleop;
  /**
   * Add your docs here.
   */
  public HatchRelease(Teleop teleop, double timeout) {
    super(timeout);
    this.teleop = teleop;
    requires(teleop.hatch);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    teleop.hatch.hatchSolenoidOut();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Called once after timeout
  @Override
  protected void end() {
    teleop.hatch.hatchSolenoidIn();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
