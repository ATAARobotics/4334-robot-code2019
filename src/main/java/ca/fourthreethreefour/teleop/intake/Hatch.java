/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour.teleop.intake;

import ca.fourthreethreefour.commands.SetSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Hatches
 */
public class Hatch extends Subsystem {

  //Creates a double solenoid object and sets it
  //to its respective ports on the robot
  public DoubleSolenoid hatchKnockoffSolenoid = new DoubleSolenoid(0, 1);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void hatchSolenoidOut() {
    new SetSolenoid(hatchKnockoffSolenoid, DoubleSolenoid.Value.kForward).set();
  }
  
  public void hatchSolenoidIn() {
    new SetSolenoid(hatchKnockoffSolenoid, DoubleSolenoid.Value.kReverse).set();
  }
}
