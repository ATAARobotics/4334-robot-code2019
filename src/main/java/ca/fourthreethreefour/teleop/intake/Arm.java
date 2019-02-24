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
public class Arm extends PIDSubsystem {

  Encoders encoders;
  Cargo arm;
  boolean isEnabled;
  double setpoint;

  /**
   * Add your docs here.
   */
  public Arm(Encoders encoders, Cargo cargo) {
    // Insert a subsystem name and PID values here
    super("ArmRotate", 0.05, 0, 0);
    this.encoders = encoders;
    this.arm = cargo;
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  public void setP(int p) {
    super.getPIDController().setP(p);
  }

  public void setI(int i) {
    super.getPIDController().setI(i);
  }

  public void setD(int d) {
    super.getPIDController().setD(d);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void enable() {
    super.enable();
    isEnabled = true;
  }

  @Override
  public void disable() {
    super.enable();
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
  public double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return encoders.armPotentiometer.get();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    // e.g. yourMotor.set(output);
    arm.intakeRotateMotor1.set(output);
    arm.intakeRotateMotor2.set(output);
  }


}
