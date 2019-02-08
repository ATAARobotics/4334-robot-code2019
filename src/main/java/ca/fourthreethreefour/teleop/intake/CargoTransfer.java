/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import ca.fourthreethreefour.teleop.intake.Cargo;

/**
 * Add your docs here.
 */
public class CargoTransfer extends Command {
  /**
   * Add your docs here.
   */

   Cargo cargo = new Cargo();
   Double motorSpeed;
   boolean isDone;
  public CargoTransfer(double speed, boolean finished) {
    motorSpeed = speed;
    isDone = finished;
    requires(cargo);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    cargo.cargoTransfer(motorSpeed);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isDone;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    cargo.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
