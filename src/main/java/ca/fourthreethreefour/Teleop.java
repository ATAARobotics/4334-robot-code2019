package ca.fourthreethreefour;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Teleop {

    private Joystick gunnerStick = new Joystick(1);
    private Joystick driveStick = new Joystick(0);

    private DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(2, 3);
    private VictorSPX rearLeftMotor = new VictorSPX(1);
    private TalonSRX frontLeftMotor = new TalonSRX(0);
    private VictorSPX rearRightMotor = new VictorSPX(3);
    private TalonSRX frontRightMotor = new TalonSRX(2);
    private ATADrive driveTrain = new ATADrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, gearShiftSolenoid);
    private boolean lowGear = true;


    public void TeleopPeriodic() {
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
