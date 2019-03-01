package ca.fourthreethreefour.teleop.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import ca.fourthreethreefour.commands.ReverseSolenoid;
import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

    //Creates and initializes various objects and assigns
    //them to their respective ports on the robot
    public DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(Settings.GEAR_SOLENOID_PORT_1, Settings.GEAR_SOLENOID_PORT_2); //may need to add another solenoid, there is two gearboxes after all.
    public WPI_TalonSRX rearLeftMotor = new WPI_TalonSRX(Settings.REAR_LEFT_MOTOR_PORT);
    public WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(Settings.FRONT_LEFT_MOTOR_PORT);
    public WPI_TalonSRX rearRightMotor = new WPI_TalonSRX(Settings.REAR_RIGHT_MOTOR_PORT);
    public WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(Settings.FRONT_RIGHT_MOTOR_PORT);
    public SpeedControllerGroup leftSpeedControllerGroup = new SpeedControllerGroup(rearLeftMotor, frontLeftMotor);
    public SpeedControllerGroup rightSpeedControllerGroup = new SpeedControllerGroup(rearRightMotor, frontRightMotor);
    public DifferentialDrive driveTrain = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedControllerGroup);
    public Value gearLow = Value.kReverse;
    public Value gearHigh = Value.kForward;
    

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  /**
   * Drive function with controller
   * @param controller controller to drive with
   * @return void
   */
  public void drive(XboxController controller, boolean cargoOuttake) {

    double speed = controller.getY(Hand.kLeft);
    speed = speed * Settings.DRIVE_SPEED;
    speed = cargoOuttake ? speed : -speed;
  }

  /**
   * Drive function for external use
   * @param leftValue value for left motors
   * @param rightValue value right motors
   * @return void
   */
  public void ExtDrive(double leftDrive, double rightDrive) {
    driveTrain.tankDrive(leftDrive, rightDrive);
  }

  /**
   * Gearshift function
   * @return void
   */
  public void gearShift() {
      new ReverseSolenoid(gearShiftSolenoid, gearLow).reverse();
  }
}
