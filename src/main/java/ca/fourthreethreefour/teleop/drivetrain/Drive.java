package ca.fourthreethreefour.teleop.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import ca.fourthreethreefour.commands.ReverseSolenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

    //Creates and initializes solenoid and motor objects and
    //assigns them to their respective ports on the robot
    public DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(2, 3); //may need to add another solenoid, there is two gearboxes after all.
    private WPI_TalonSRX rearLeftMotor = new WPI_TalonSRX(1);
    private WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(0);
    private WPI_TalonSRX rearRightMotor = new WPI_TalonSRX(3);
    private WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(2);
    private SpeedControllerGroup leftSpeedControllerGroup = new SpeedControllerGroup(rearLeftMotor, frontLeftMotor);
    private SpeedControllerGroup rightSpeedControllerGroup = new SpeedControllerGroup(rearRightMotor, frontRightMotor);
    public DifferentialDrive driveTrain = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedControllerGroup);
    //Sets the boolean lowGear equal to true
    private boolean lowGear = true;
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
  public void drive(XboxController controller) {
    // //Calls the arcadeDrive class in teleop
    // double leftSpeed = controller.getRawAxis(1) - controller.getRawAxis(4);
    // double rightSpeed = controller.getRawAxis(1) + controller.getRawAxis(4);
    // driveTrain.tankDrive(leftSpeed, rightSpeed);
    driveTrain.arcadeDrive(controller.getY(Hand.kLeft), -controller.getX(Hand.kRight));
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
