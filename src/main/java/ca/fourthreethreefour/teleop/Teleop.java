package ca.fourthreethreefour.teleop;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.Joystick;

public class Teleop {


  //Creates and initializes joystick objects and assigns them
  //to their respective ports on the Driver Station
  private Joystick operatorStick = new Joystick(1);
  private Joystick driveStick = new Joystick(0);
  public Drive drive = new Drive();
  public Encoders encoder = new Encoders();

  public void RobotInit() {

    drive.leftSpeedControllerGroup.setInverted(true);
    drive.rightSpeedControllerGroup.setInverted(true);
    drive.driveTrain.setSafetyEnabled(true);

    drive.frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
    drive.frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
    drive.frontLeftMotor.setSensorPhase(true);
    drive.frontRightMotor.setSensorPhase(false);

    encoder.initalizeNavX();
  }

  public void TeleopInit() {

  }

  public void TeleopPeriodic() {
    drive.drive(driveStick);
    
    if(driveStick.getRawButton(3)) {
      drive.gearShift();
    }
  }

  public void ExtDrive(double leftValue, double rightValue) {
    drive.ExtDrive(leftValue, rightValue);
  }
}
