/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that is designed to control the Cargo mecahnisms
 */
public class Cargo extends Subsystem {

  public VictorSP cargoOuttakeLeftMotor = new VictorSP(2);
  public VictorSP cargoOuttakeRightMotor = new VictorSP(3);
  public VictorSP intakeRotateMotor1 = new VictorSP(0);
  public VictorSP intakeRotateMotor2 = new VictorSP(1);

  @Override
  public void initDefaultCommand() {
  }

  public void cargoTransfer(double speed) {
    cargoOuttakeLeftMotor.set(speed);
    cargoOuttakeRightMotor.set(-speed);
  }

  public void cargoOuttake(double speed) {
    cargoOuttakeLeftMotor.set(-speed);
    cargoOuttakeRightMotor.set(speed);
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
