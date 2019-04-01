package ca.fourthreethreefour.teleop.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import ca.fourthreethreefour.commands.ReverseSolenoid;
import ca.fourthreethreefour.commands.SetSolenoid;
import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends Subsystem {

  // Creates and initializes solenoid and motor objects and
  // assigns them to their respective ports on the robot
  public DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(Settings.GEAR_SOLENOID_PORT_1, Settings.GEAR_SOLENOID_PORT_2); // TODO Update ports
  private WPI_TalonSRX rearLeftMotor = new WPI_TalonSRX(Settings.REAR_LEFT_MOTOR_PORT);  // TODO Update ports
  private WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(Settings.FRONT_LEFT_MOTOR_PORT); // TODO Update ports
  private WPI_TalonSRX rearRightMotor = new WPI_TalonSRX(Settings.REAR_RIGHT_MOTOR_PORT); // TODO Update ports
  private WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(Settings.FRONT_RIGHT_MOTOR_PORT); // TODO Update ports
  private SpeedControllerGroup leftSpeedControllerGroup = new SpeedControllerGroup(rearLeftMotor, frontLeftMotor);
  private SpeedControllerGroup rightSpeedControllerGroup = new SpeedControllerGroup(rearRightMotor, frontRightMotor);
  public DifferentialDrive driveTrain = new DifferentialDrive(leftSpeedControllerGroup, rightSpeedControllerGroup);
    public Value gearLow = Value.kReverse; // TODO Ensure that these are correct direction
    public Value gearHigh = Value.kForward;

    public Boolean ignoreController = false;


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
    // //Calls the arcadeDrive class in teleop
    double speed = Math.abs(controller.getY(Hand.kLeft)) > 0.05 ? controller.getY(Hand.kLeft) : 0,
      turn = Math.abs(controller.getX(Hand.kRight)) > 0.05 ? -controller.getX(Hand.kRight) : 0;
    speed = speed * Settings.DRIVE_SPEED;
    speed = cargoOuttake ? speed : -speed;
    speed = speed >= 0 ? speed*speed : -(speed*speed);

    if (Settings.CAM_TURN) {
      turn = turn * Settings.CAM_SPEED;
    } else {
      turn = turn >= 0 ? Math.max(Math.pow(turn, Settings.TURN_EXPONENT), turn*Settings.TURN_BASE) : Math.min(-Math.pow(Math.abs(turn), Settings.TURN_EXPONENT), turn*Settings.TURN_BASE);
    }
    if(!ignoreController){
        driveTrain.arcadeDrive(speed, turn);
    }
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

  public void ExtArcadeDrive(double speed, double angle){
    driveTrain.arcadeDrive(speed, angle);
  }

  /**
   * Gearshift function
   * @return void
   */
  public void gearShift() {
      new ReverseSolenoid(gearShiftSolenoid, gearLow).reverse();
  }

  public void setGearHigh() {
    new SetSolenoid(gearShiftSolenoid, gearHigh).set();
  }

  public void setGearLow() {
    new SetSolenoid(gearShiftSolenoid, gearLow).set();
  }
}
