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
import ca.fourthreethreefour.teleop.*;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Hatches
 */
public class Hatch extends Subsystem {

  public PIDSubsystem hatchAlignPID;
  private Boolean isMoveEnabled;
  private Encoders encoders;

  public Hatch(Encoders encoders){

    this.encoders = encoders;

    //Configure Vision Move PID
    hatchAlignPID = new PIDSubsystem("MovePID", 0.01, 0.0, 0.0) { //TODO: Tune PID Controller
      @Override
      protected double returnPIDInput() {return encoders.hatchPotentiometer.get();}

      @Override
      protected void usePIDOutput(double output) {
        hatchAlignMotor.set(output);
      }

      @Override
      protected void initDefaultCommand() { }

      @Override
      public void enable(){
        //Enables PID
        super.enable();
        //Set enabled variable to true
        isMoveEnabled = true;
      }

      @Override
      public void disable(){
        //Disables PID
        super.disable();
        //Set enabled variable to false
        isMoveEnabled = false;
      }
    };

    //Configures then disables the PID Controller
    hatchAlignPID.setAbsoluteTolerance(0.5);
    hatchAlignPID.getPIDController().setContinuous(false);
    hatchAlignPID.setOutputRange(-0.01,0.01); //TODO: Raise Max Speed After Testing
    hatchAlignPID.disable();
  }

  //Creates a double solenoid object and sets it
  //to its respective ports on the robot
  public DoubleSolenoid hatchKnockoffSolenoid = new DoubleSolenoid(Settings.HATCH_SOLENOID_PORT_1, Settings.HATCH_SOLENOID_PORT_2);
  public VictorSP hatchAlignMotor = new VictorSP(6);

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

  public void hatchShift() {
    new ReverseSolenoid(hatchKnockoffSolenoid, Value.kReverse).reverse();
  }

  public void hatchAllignShift(double speed) {
    hatchAlignMotor.set(speed);
  }

  //Stops the motors
  public void stop() {
    hatchAlignMotor.set(0);
  }

  public void startMovePID(){
    //Enabled Horizontal Movement PID Controller
    hatchAlignPID.enable();
  }

  public void stopMovePID(){
    //Disables Horizontal Movement PID Controller
    hatchAlignPID.disable();
  }

  public boolean isMoveEnabled(){
    return isMoveEnabled;
  }

  public void centerHatchMotor(){
    hatchAlignPID.setSetpoint(0.5);
    hatchAlignPID.enable();
  }
}
