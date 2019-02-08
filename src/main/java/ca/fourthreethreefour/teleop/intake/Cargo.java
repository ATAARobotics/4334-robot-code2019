/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that is designed to control the Cargo mecahnisms
 */
public class Cargo extends Subsystem {

  private TalonSRX cargoOuttakeLeftMotor = new TalonSRX(0);
  private TalonSRX cargoOuttakeRightMotor = new TalonSRX(1);
  private TalonSRX intakeRotateMotor1 = new TalonSRX(2);
  private TalonSRX intakeRotateMotor2 = new TalonSRX(3);

  @Override
  public void initDefaultCommand() {
  }

  public void cargoTransfer() {
    
  }
}
