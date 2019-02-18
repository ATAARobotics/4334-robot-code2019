package ca.fourthreethreefour.teleop;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.Joystick;

public class Teleop {


  // //Creates and initializes joystick objects and assigns them
  // //to their respective ports on the Driver Station
  private Joystick operatorStick = new Joystick(1);
  //private Joystick driveStick = new Joystick(0);
  //Creates drive and encoder objects
  public Drive drive = new Drive();
  public Encoders encoder = new Encoders();

  //Calls the robotInit function in the teleop class
  public void RobotInit() {

    //Inverts the direction of the speed controllers
    drive.leftSpeedControllerGroup.setInverted(true);
    drive.rightSpeedControllerGroup.setInverted(true);
    //Enables motor safety for the drivetrain
    drive.driveTrain.setSafetyEnabled(true);

    //Selects the CRTE mag encoder as the feedback
    //device for the motor controllers
    drive.frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
    drive.frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
    //Inverts the phase of the sensor of the front left motor sensor and
    //doesn't invert the phase of the sensor of the front right motor
    drive.frontLeftMotor.setSensorPhase(true);
    drive.frontRightMotor.setSensorPhase(false);

    //Initialize NavX for the encoder
    // encoder.initalizeNavX();
  }

  //Function that runs when teleop begins
  public void TeleopInit() {

  }

  //Function that is called periodically in teleop
  public void TeleopPeriodic() {
    // drive.drive(driveStick);
    
    // if(driveStick.getRawButton(3)) {
    //   drive.gearShift();
    // }
  }

  public void ExtDrive(double leftValue, double rightValue) {
    drive.ExtDrive(leftValue, rightValue);
  }
}
