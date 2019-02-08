/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that is designed to control the Cargo mecahnisms
 */
public class Cargo extends Subsystem {

  private VictorSPX cargoOuttakeLeftMotor = new VictorSPX(0);
  private VictorSPX cargoOuttakeRightMotor = new VictorSPX(1);
  private VictorSPX intakeRotateMotor1 = new VictorSPX(2);
  private VictorSPX intakeRotateMotor2 = new VictorSPX(3);


  @Override
  public void initDefaultCommand() {
  }

  public void cargoTransfer(double speed) {
    cargoOuttakeLeftMotor.set(ControlMode.PercentOutput, speed);
    cargoOuttakeRightMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void cargoOuttake(double speed) {
    cargoOuttakeLeftMotor.set(ControlMode.PercentOutput, -speed);
    cargoOuttakeRightMotor.set(ControlMode.PercentOutput, speed);
  }

  public void intakeRotate(double speed) {
    intakeRotateMotor1.set(ControlMode.PercentOutput, -speed);
    intakeRotateMotor2.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    cargoOuttakeLeftMotor.set(ControlMode.PercentOutput, 0);
    cargoOuttakeRightMotor.set(ControlMode.PercentOutput, -0);
  }
}
