/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.fourthreethreefour;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */

public class Drive extends Subsystem {

    //Creates and initializes solenoid and motor objects and
    //assigns them to their respective ports on the robot
    private DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(2, 3);
    private TalonSRX rearLeftMotor = new TalonSRX(1);
    private TalonSRX frontLeftMotor = new TalonSRX(0);
    private TalonSRX rearRightMotor = new TalonSRX(3);
    private TalonSRX frontRightMotor = new TalonSRX(2);
    //Creates an ATADrive object with motors and a solenoid as modules
    private ATADrive driveTrain = new ATADrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, gearShiftSolenoid);
    //Sets the boolean lowGear equal to true
    private boolean lowGear = true;
    private boolean slow = false;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void drive(Joystick controller) {
    //Calls the arcadeDrive class in teleop
    driveTrain.arcadeDrive(controller.getRawAxis(1), controller.getRawAxis(4), true);
  }

  public void gearShift() {
      lowGear = !lowGear;
      driveTrain.gearShift(lowGear);
  }

  public void speedShift() {
      slow = !slow;
      driveTrain.slow = slow;
  }
}
