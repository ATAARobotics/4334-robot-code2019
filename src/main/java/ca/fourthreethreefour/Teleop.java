package ca.fourthreethreefour;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Teleop {

    //Creates and initializes joystick objects and assigns them
    //to their respective ports on the Driver Station
    private Joystick gunnerStick = new Joystick(1);
    private Joystick driveStick = new Joystick(0);
    //Creates and initializes solenoid and motor objects and
    //assigns them to their respective ports on the robot
    private DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(2, 3);
    private VictorSPX rearLeftMotor = new VictorSPX(1);
    private TalonSRX frontLeftMotor = new TalonSRX(0);
    private VictorSPX rearRightMotor = new VictorSPX(3);
    private TalonSRX frontRightMotor = new TalonSRX(2);
    //Creates an ATADrive object with motors and a solenoid as modules
    private ATADrive driveTrain = new ATADrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, gearShiftSolenoid);
    //Sets the boolean lowGear equal to true
    private boolean lowGear = true;


    public void TeleopPeriodic() {
          //Calls the arcadeDrive class in teleop
          driveTrain.arcadeDrive(driveStick.getRawAxis(1), driveStick.getRawAxis(4), true);
          
          if(driveStick.getRawButton(3)) {
            lowGear = !lowGear;
            driveTrain.gearShift(lowGear);
          }
          if (driveStick.getRawButton(1)) {
            driveTrain.slow = !driveTrain.slow;
          } 
          driveTrain.arcadeDrive(driveStick.getRawAxis(1), driveStick.getRawAxis(4), true);

    }
}
