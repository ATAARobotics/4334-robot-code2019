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

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that is designed to control the Cargo mecahnisms
 */
public class Cargo extends Subsystem {

  private VictorSP cargoOuttakeLeftMotor = new VictorSP(0);
  private VictorSP cargoOuttakeRightMotor = new VictorSP(1);
  private VictorSP intakeRotateMotor1 = new VictorSP(2);
  private VictorSP intakeRotateMotor2 = new VictorSP(3);

  @Override
  public void initDefaultCommand() {
  }

  public void cargoTransfer(double speed) {
    cargoOuttakeLeftMotor.set(-speed);
    cargoOuttakeRightMotor.set(speed);
  }

  public void cargoOuttake(double speed) {
    cargoOuttakeLeftMotor.set(speed);
    cargoOuttakeRightMotor.set(-speed);
  }

  public void intakeRotate(double speed) {
    intakeRotateMotor1.set(speed);
    intakeRotateMotor2.set(speed);
  }

  public void stop() {
    cargoOuttakeLeftMotor.set(0);
    cargoOuttakeRightMotor.set(-0);
  }
}
