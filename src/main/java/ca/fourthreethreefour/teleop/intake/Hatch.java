/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import ca.fourthreethreefour.commands.ReverseSolenoid;
import ca.fourthreethreefour.commands.SetSolenoid;
import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Hatches
 */
public class Hatch extends Subsystem {

  //Creates a double solenoid object and sets it
  //to its respective ports on the robot
  public DoubleSolenoid hatchKnockoffSolenoid = new DoubleSolenoid(Settings.HATCH_SOLENOID_PORT_1, Settings.HATCH_SOLENOID_PORT_2);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void hatchSolenoidOut() {
    new SetSolenoid(hatchKnockoffSolenoid, DoubleSolenoid.Value.kForward).execute();
  }
  
  public void hatchSolenoidIn() {
    new SetSolenoid(hatchKnockoffSolenoid, DoubleSolenoid.Value.kReverse).execute();
  }

  public void hatchShift() {
    new ReverseSolenoid(hatchKnockoffSolenoid, Value.kReverse).execute();
  }

  public Value get() {
    return hatchKnockoffSolenoid.get();
  }
}
