/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Add your docs here.
 */
public class SideWinder extends PIDSubsystem {
  /**
   * Add your docs here.
   */

  boolean isEnabled;
  double setpoint;
  Encoders encoders;
  Hatch hatch;

  public SideWinder(Encoders encoders, Hatch hatch) {
    // Intert a subsystem name and PID values here
    super("SideWinder", 0.017, 0, 0);
    this.encoders = encoders;
    this.hatch = hatch;
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  @Override
  public void initDefaultCommand() {
    super.setAbsoluteTolerance(0.0);
    // super.setOutputRange(-0.5, 0.5);
  }

  @Override
  public void enable() {
    super.enable();
    isEnabled = true;
  }
  
  @Override
  public void disable() {
    super.disable();
    isEnabled = false;
  }

  @Override
  public void setSetpoint(double setpoint) {
    super.setSetpoint(setpoint);
    this.setpoint = setpoint;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public double getSetpoint() {
    return setpoint;
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return encoders.hatchPotentiometerGet();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);

    // To ensure that if its moving one direction that direction' sensor isn't pressed yet
    if (encoders.hatchHallEffectLeft.get() && output >= 0) {
      hatch.hatchSet(-output);
    } else if (encoders.hatchHallEffectRight.get() && output <= 0) {
      hatch.hatchSet(-output);
    } else {
      hatch.hatchSet(0);
    }
  }
}
