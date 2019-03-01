package ca.fourthreethreefour.teleop;

import ca.fourthreethreefour.teleop.intake.Arm;
import ca.fourthreethreefour.teleop.intake.Cargo;
import ca.fourthreethreefour.teleop.intake.Hatch;
import ca.fourthreethreefour.teleop.intake.Mechanum;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.shuffleboard.Settings;
import ca.fourthreethreefour.teleop.drivetrain.Drive;
import ca.fourthreethreefour.teleop.systems.Ultrasonics;

public class Teleop {


  double armPIDOffset = -1506;
  //Creates and initializes various objects needed in teleop
  private XboxController driver = new XboxController(Settings.DRIVER_CONTROLLER_PORT);
  // private XboxController operator = new XboxController(Settings.OPERATOR_CONTROLLER_PORT);
  
  private Cargo cargo = new Cargo();
  public Encoders encoders = new Encoders(armPIDOffset);
  private Hatch hatch = new Hatch();
  private Mechanum mechanum = new Mechanum();
  public Arm arm = new Arm(encoders, cargo);
  public Drive drive = new Drive();
  private Ultrasonics ultrasonics = new Ultrasonics();

  public static boolean cargoOuttake;

  public void RobotInit() {
    ultrasonics.enable();
    ultrasonics.ultrasonicPollingThread();
  }

  /**
   * Runs as the start of teleop
   * @return void
   */
  public void TeleopInit() {
    cargo.intakeRotateMotor1.setSafetyEnabled(true);
    cargo.intakeRotateMotor2.setSafetyEnabled(true);
    cargo.cargoOuttakeLeftMotor.setSafetyEnabled(true);
    cargo.cargoOuttakeRightMotor.setSafetyEnabled(true);
    mechanum.mechanumMotor.setSafetyEnabled(true);
    drive.driveTrain.setSafetyEnabled(true);
    drive.gearShiftSolenoid.set(drive.gearLow);
    mechanum.mechanumSolenoid.set(Value.kReverse);
    hatch.hatchSolenoidIn();
    arm.setAbsoluteTolerance(2);
    cargoOuttake = true;
  }
  

  /**
   * Runs periodically during teleop
   * @return void
   */
  public void TeleopPeriodic() {

    drive.drive(driver, cargoOuttake);


    System.out.println(arm.returnPIDInput());
    // double intakeSpeed = driver.getTriggerAxis(Hand.kRight) - driver.getTriggerAxis(Hand.kLeft);
    // if (Math.abs(intakeSpeed) > 0.05) {
    //   cargo.intakeRotate(intakeSpeed*Settings.INTAKE_ROTATE_SPEED);
    // } else {
    //   cargo.intakeRotate(0);
    // };
    if (driver.getBumper(Hand.kLeft)) {
      arm.disable();
      cargo.intakeRotate(Settings.INTAKE_ROTATE_SPEED);
    } else if (driver.getBumper(Hand.kRight) && encoders.armInnerLimitSwitch.get()) {
      arm.disable();
      cargo.intakeRotate(-Settings.INTAKE_ROTATE_SPEED);
    } else if (!arm.isEnabled()) {
      cargo.intakeRotate(0);
    }

    if (driver.getTriggerAxis(Hand.kLeft) > 0.05) {
      cargo.cargoOuttake(driver.getTriggerAxis(Hand.kLeft));
      mechanum.mechanumRoller(-driver.getTriggerAxis(Hand.kLeft));
    } else if (driver.getTriggerAxis(Hand.kRight) > 0.05 && encoders.cargoButton.get()) {
      cargo.cargoTransfer(driver.getTriggerAxis(Hand.kRight));
      mechanum.mechanumRoller(driver.getTriggerAxis(Hand.kRight));
    } else {
      cargo.stop();
      mechanum.mechanumRoller(0);
      if (!encoders.cargoButton.get()) {
        mechanum.mechanumRetract();
      }
    }
    
    if (driver.getStickButtonPressed(Hand.kRight)) {
      drive.gearShift();
    }

    if (driver.getStickButtonReleased(Hand.kLeft) && Settings.REVERSABLE_CONTROLS) {
      cargoOuttake =  !cargoOuttake;
    }
    Logging.put(Settings.DRIVE_DIRECTION_ENTRY, cargoOuttake);

    if (driver.getAButton()) {
      hatch.hatchSolenoidOut();
    } else {
      hatch.hatchSolenoidIn();
    }

    if (driver.getBButtonReleased()) {
      mechanum.mechanumShift();
    }

    // Up D-Pad - Sets the PID setpoint to hatch outtake and retracts the mecanum intake
    if (!encoders.armInnerLimitSwitch.get() && arm.getSetpoint() != 111) {
      arm.disable();
      armPIDOffset += encoders.armPotentiometer.get();
    } else if (driver.getPOV() == 0) { // TODO make the setpoints shuffleboard stuff
      Logging.log("Shooter set point up");
      arm.setSetpoint(90);
      arm.enable();
      mechanum.mechanumRetract();
    } else if (driver.getPOV() == 90) {  // Right D-Pad - Sets the PID setpoint to cargo outtake and retracts the mecanum intake
      Logging.log("Shooter set point right");
      arm.setSetpoint(110);
      arm.enable();
      mechanum.mechanumRetract();
    } else if (driver.getPOV() == 180) {  // Down D-Pad - Sets the PID setpoint to hatch ground and retracts the mecanum intake
      Logging.log("Shooter set point down");
      arm.setSetpoint(172);
      arm.enable();
      mechanum.mechanumRetract();
    } else if (driver.getPOV() == 270) {  // Left D-Pad - Sets the PID setpoint to cargo intake from the mecanum intake
      Logging.log("Shooter set point left");
      arm.setSetpoint(10);
      arm.enable();
      mechanum.mechanumExtend();
    } else if (arm.onTarget()) {
      arm.disable();
    } else {
      arm.disable();
    }

    // ultrasonics.printValues();

    if (!encoders.cargoButton.get()) {
      arm.setSetpoint(111);
      arm.enable();
      mechanum.mechanumRetract();
    }

    // System.out.println("Is enabled? " + arm.isEnabled());
    // System.out.println("Setpoint: " + arm.getSetpoint());
    System.out.println("Arm Motor 1: " + cargo.intakeRotateMotor1.get() + " Arm Motor 2: " + cargo.intakeRotateMotor2.get());
    System.out.println(encoders.armInnerLimitSwitch.get());

  }

  /**
   * Drive function for external use
   * @param leftValue value for left motors, ranges from 1 to -1
   * @param rightValue value for right motors, ranges from 1 to -1
   * @return void
   */
  public void ExtDrive(double leftValue, double rightValue) {
    drive.ExtDrive(leftValue, rightValue);
  }
}
