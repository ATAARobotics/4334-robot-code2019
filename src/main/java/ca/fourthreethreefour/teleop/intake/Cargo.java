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

import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that is designed to control the Cargo mecahnisms
 */
public class Cargo extends Subsystem {

  //Creates and initializes motor objects and assigns them
  //to their respective ports on the robot
  public VictorSPX cargoOuttakeLeftMotor = new VictorSPX(Settings.CARGO_OUTTAKE_LEFT_PORT);
  public VictorSPX cargoOuttakeRightMotor = new VictorSPX(Settings.CARGO_OUTTAKE_RIGHT_PORT);
  public VictorSPX intakeRotateMotor1 = new VictorSPX(Settings.INTAKE_ROTATE_PORT_1);
  public VictorSPX intakeRotateMotor2 = new VictorSPX(Settings.INTAKE_ROTATE_PORT_2);

  @Override
  //Initialize the default command for this subsystem
  public void initDefaultCommand() {
  }

  //Sets the left motor speed positive and right motor
  //speed negative for the cargo transfer method
  public void cargoTransfer(double speed) {
    cargoOuttakeLeftMotor.set(ControlMode.PercentOutput, speed);
    cargoOuttakeRightMotor.set(ControlMode.PercentOutput, -speed);
  }

  //Sets the left motor speed positive and right motor
  //speed negative for the cargo outtake method
  public void cargoOuttake(double speed) {
    cargoOuttakeLeftMotor.set(ControlMode.PercentOutput, -speed);
    cargoOuttakeRightMotor.set(ControlMode.PercentOutput, speed);
  }

  //Sets the intake motor speeds positive for the intake rotate method
  public void intakeRotate(double speed) {
    intakeRotateMotor1.set(ControlMode.PercentOutput, speed);
    intakeRotateMotor2.set(ControlMode.PercentOutput, speed);
  }

  //Stops the motors
  public void stop() {
    cargoOuttakeLeftMotor.set(ControlMode.PercentOutput, 0);
    cargoOuttakeRightMotor.set(ControlMode.PercentOutput, -0);
  }
}
