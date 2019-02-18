/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import ca.fourthreethreefour.commands.ReverseSolenoid;
import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Mechanum extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public VictorSP mechanumMotor = new VictorSP(Settings.MECHANUM_MOTOR_PORT);
  public DoubleSolenoid mechanumSolenoid = new DoubleSolenoid(Settings.MECHANUM_SOLENOID_PORT_1, Settings.MECHANUM_SOLENOID_PORT_2);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void mechanumRoller(double speed) {
    mechanumMotor.set(speed);
  }

  public void mechanumShift() {
    new ReverseSolenoid(mechanumSolenoid).reverse();
  }
}
