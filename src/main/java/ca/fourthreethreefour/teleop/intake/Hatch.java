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

import ca.fourthreethreefour.commands.ReverseSolenoid;
import ca.fourthreethreefour.commands.SetSolenoid;
import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Hatches
 */
public class Hatch extends Subsystem {

  //Creates a double solenoid object and sets it
  //to its respective ports on the robot
  public DoubleSolenoid hatchKnockoffSolenoid = new DoubleSolenoid(Settings.HATCH_SOLENOID_PORT_1, Settings.HATCH_SOLENOID_PORT_2); // TODO Update ports
  public VictorSPX hatchAlignMotor = new VictorSPX(Settings.HATCH_SIDEWEAVER_PORT);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void hatchSolenoidOut() {
    new SetSolenoid(hatchKnockoffSolenoid, DoubleSolenoid.Value.kForward).set();
  }
  
  public void hatchSolenoidIn() {
    new SetSolenoid(hatchKnockoffSolenoid, DoubleSolenoid.Value.kReverse).set();  // TODO Ensure directions are correct
  }

  public void hatchShift() {
    new ReverseSolenoid(hatchKnockoffSolenoid, Value.kReverse).reverse(); // TODO Ensure directions are correct
  }

  public void hatchSet(double speed) {
    hatchAlignMotor.set(ControlMode.PercentOutput, speed);
  }

  public Value get() {
    return hatchKnockoffSolenoid.get();
  }
}
