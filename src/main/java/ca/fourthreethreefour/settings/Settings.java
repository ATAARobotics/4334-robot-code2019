package ca.fourthreethreefour.settings;

import java.io.File;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Settings class that contains all shuffleboard required code so it's all in a 
 * centralized locaton.
 * 
 * @author Cool Kornak
 */
public class Settings {

    //Teleop teleop;

	static SettingsFile settingsFile = new SettingsFile(new File("/settings.txt"));
	String settingsActive = settingsFile.toString();

    static public boolean LOGGING_ENABLED = settingsFile.getBooleanProperty("LOGGING_ENABLED", false);

    static public int DRIVER_CONTROLLER_PORT = settingsFile.getIntProperty("DRIVER_CONTROLLER_PORT", 0);

    static public int FRONT_LEFT_MOTOR_PORT = settingsFile.getIntProperty("FRONT_LEFT_MOTOR_PORT", 0);
    static public int REAR_LEFT_MOTOR_PORT = settingsFile.getIntProperty("REAR_LEFT_MOTOR_PORT", 1);
    static public int FRONT_RIGHT_MOTOR_PORT = settingsFile.getIntProperty("FRONT_RIGHT_MOTOR_PORT", 2);
    static public int REAR_RIGHT_MOTOR_PORT = settingsFile.getIntProperty("REAR_RIGHT_MOTOR_PORT", 3);

    static public int CARGO_OUTTAKE_LEFT_PORT = settingsFile.getIntProperty("CARGO_OUTTAKE_LEFT_PORT", 6);
    static public int CARGO_OUTTAKE_RIGHT_PORT = settingsFile.getIntProperty("CARGO_OUTTAKE_RIGHT_PORT", 4);
    static public int INTAKE_ROTATE_PORT_1 = settingsFile.getIntProperty("INTAKE_ROTATE_PORT_1", 5);
    static public int INTAKE_ROTATE_PORT_2 = settingsFile.getIntProperty("INTAKE_ROTATE_PORT_2", 8);
    static public int MECHANUM_MOTOR_PORT = settingsFile.getIntProperty("MECHANUM_MOTOR_PORT", 7);
    static public int HATCH_SIDEWEAVER_PORT = settingsFile.getIntProperty("HATCH_SIDEWEAVER_PORT", 9);

    static public int CARGO_BUTTON_DIGITAL_PORT = settingsFile.getIntProperty("CARGO_BUTTON_DIGITAL_PORT", 3);
    static public int ARM_INNER_LIMITSWITCH_DIGITAL_PORT = settingsFile.getIntProperty("ARM_INNER_LIMITSWITCH_DIGITAL_PORT", 5);
    static public int HALL_EFFECT_LEFT_DIGITAL_PORT = settingsFile.getIntProperty("HALL_EFFECT_LEFT_DIGITAL_PORT", 0);
    static public int HALL_EFFECT_RIGHT_DIGITAL_PORT = settingsFile.getIntProperty("HALL_EFFECT_RIGHT_DIGITAL_PORT", 1);
    static public int HALL_EFFECT_CENTER_DIGITAL_PORT = settingsFile.getIntProperty("HALL_EFFECT_CENTER_DIGITAL_PORT", 2);

    static public int ARM_POTENTIOMETER_ANALOG_PORT = settingsFile.getIntProperty("ARM_POTENTIOMETER_ANALOG_PORT", 1);
    static public int HATCH_POTENTIOMETER_ANALOG_PORT = settingsFile.getIntProperty("HATCH_POTENTIOMETER_ANALOG_PORT", 2);

    static public int GEAR_SOLENOID_PORT_1 = settingsFile.getIntProperty("GEAR_SOLENOID_PORT_1", 3);
    static public int GEAR_SOLENOID_PORT_2 = settingsFile.getIntProperty("GEAR_SOLENOID_PORT_2", 2);

    static public int HATCH_SOLENOID_PORT_1 = settingsFile.getIntProperty("HATCH_SOLENOID_PORT_1", 0);
    static public int HATCH_SOLENOID_PORT_2 = settingsFile.getIntProperty("HATCH_SOLENOID_PORT_2", 1);

    static public int MECHANUM_SOLENOID_PORT_1 = settingsFile.getIntProperty("MECHANUM_SOLENOID_PORT_1", 7);
    static public int MECHANUM_SOLENOID_PORT_2 = settingsFile.getIntProperty("MECHANUM_SOLENOID_PORT_2", 6);

    static public boolean REVERSABLE_CONTROLS = settingsFile.getBooleanProperty("REVERSABLE_CONTROLS", false);

    static public int ARM_POTENTIOMETER_RANGE = settingsFile.getIntProperty("ARM_POTENTIOMETER_RANGE", 1800);
    static public int ARM_POTENTIOMETER_OFFSET = settingsFile.getIntProperty("ARM_POTENTIOMETER_OFFSET", -642);
    static public int ARM_POTENTIOMETER_BASE = settingsFile.getIntProperty("ARM_POTENTIOMETER_BASE", 13);
    static public int ARM_PID_TOLERANCE = settingsFile.getIntProperty("ARM_PID_TOLERANCE", 2);
    static public int ARM_PID_HATCH_SETPOINT = settingsFile.getIntProperty("ARM_PID_HATCH_SETPOINT", 90);
    static public int ARM_PID_SHOOTING_SETPOINT = settingsFile.getIntProperty("ARM_PID_SHOOTING_SETPOINT", 110);
    static public int ARM_PID_GROUND_SETPOINT = settingsFile.getIntProperty("ARM_PID_GROUND_SETPOINT", 155);
    static public int ARM_PID_INTAKE_SETPOINT = settingsFile.getIntProperty("ARM_PID_INTAKE_SETPOINT", 10);

    static public int HATCH_ARM_PID_THRESHOLD = settingsFile.getIntProperty("HATCH_ARM_PID_THRESHOLD", 89);

    static public int HATCH_POTENTIOMETER_RANGE = settingsFile.getIntProperty("HATCH_POTENTIOMETER_RANGE", 600);
    static public int HATCH_POTENTIOMETER_OFFSET = settingsFile.getIntProperty("HATCH_POTENTIOMETER_OFFSET", -544);
    static public double HATCH_PID_MIDDLE_SETPOINT = settingsFile.getDoubleProperty("HATCH_PID_MIDDLE_SETPOINT", 29);
    static public int HATCH_POTENTIOMETER_MAX_BASE = settingsFile.getIntProperty("HATCH_POTENTIOMETER_MAX_BASE", 63);
    static public int HATCH_POTENTIOMETER_MID_BASE = settingsFile.getIntProperty("HATCH_POTENTIOMETER_MID_BASE", 29);

    static public double DRIVE_SPEED = settingsFile.getDoubleProperty("DRIVE_SPEED", 1);
    static public double CAM_SPEED = settingsFile.getDoubleProperty("TURN_SPEED", 0.8);
    static public double TURN_BASE = settingsFile.getDoubleProperty("TURN_BASE", 0.7);
    static public double TURN_EXPONENT = settingsFile.getDoubleProperty("TURN_EXPONENT", 4);
    static public double INTAKE_ROTATE_SPEED = settingsFile.getDoubleProperty("INTAKE_ROTATE_SPEED", 0.6);
    static public double LOW_GEAR_THRESHOLD = settingsFile.getDoubleProperty("LOW_GEAR_THRESHOLD", 0.1);

    static public double BALL_VELOCITY_METERS_PER_SECOND = settingsFile.getDoubleProperty("BALL_VELOCITY_METERS_PER_SECOND", 43.34);
    static public double ROCKET_EQUATION_CORRECTION = settingsFile.getDoubleProperty("ROCKET_EQUATION_CORRECTION", 0);
    static public double SHIP_EQUATION_CORRECTION = settingsFile.getDoubleProperty("SHIP_EQUATION_CORRECTION", 0);

    static public boolean CAM_TURN = settingsFile.getBooleanProperty("CAM", true);


    static public NetworkTableEntry DRIVE_DIRECTION_ENTRY;

    
    /**
     * Creates the specified objects for shuffleboard, and updates the variables that only
     * needs to be updated at initialization.
     */
    public void ShuffleInit() {
        // UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture("Microsoft LifeCam HD-3000", 0);

        // camera1.setResolution(320, 240);
        // camera1.setFPS(30);
    }

    /**
     * Updates the variables designed to be updated while the robot is powered.
     */
    public void settingsValueUpdate() {
        LOGGING_ENABLED = settingsFile.getBooleanProperty("LOGGING_ENABLED", false);
        DRIVE_SPEED = settingsFile.getDoubleProperty("DRIVE_SPEED", 1);
        CAM_SPEED = settingsFile.getDoubleProperty("TURN_SPEED", 0.8); // Custom speed for Cam.
        TURN_BASE = settingsFile.getDoubleProperty("TURN_BASE", 0.7);
        TURN_EXPONENT = settingsFile.getDoubleProperty("TURN_EXPONENT", 4);
        INTAKE_ROTATE_SPEED = settingsFile.getDoubleProperty("INTAKE_ROTATE_SPEED", 0.6);
        LOW_GEAR_THRESHOLD = settingsFile.getDoubleProperty("LOW_GEAR_THRESHOLD", 0.1);

        ARM_POTENTIOMETER_OFFSET = settingsFile.getIntProperty("ARM_POTENTIOMETER_OFFSET", -642);
        HATCH_POTENTIOMETER_OFFSET = settingsFile.getIntProperty("HATCH_POTENTIOMETER_OFFSET", -544);

        HATCH_ARM_PID_THRESHOLD = settingsFile.getIntProperty("HATCH_ARM_PID_THRESHOLD", 89);

        HATCH_POTENTIOMETER_MAX_BASE = settingsFile.getIntProperty("HATCH_POTENTIOMETER_MAX_BASE", 63);
        HATCH_POTENTIOMETER_MID_BASE = settingsFile.getIntProperty("HATCH_POTENTIOMETER_MID_BASE", 29);

        BALL_VELOCITY_METERS_PER_SECOND = settingsFile.getDoubleProperty("BALL_VELOCITY_METERS_PER_SECOND", 43.34);
        ROCKET_EQUATION_CORRECTION = settingsFile.getDoubleProperty("ROCKET_EQUATION_CORRECTION", 0);
        SHIP_EQUATION_CORRECTION = settingsFile.getDoubleProperty("SHIP_EQUATION_CORRECTION", 0);

        CAM_TURN = settingsFile.getBooleanProperty("CAM", true);
    }

    public void settingsPeriodic() {
        try {
			settingsFile.reload();
		} catch (NullPointerException e) {
			Timer.delay(0.20);
        }
        
        if (!settingsActive.equalsIgnoreCase(settingsFile.toString())) {
            System.out.println("Reloading Settings");
            settingsValueUpdate();
            settingsActive = settingsFile.toString();
        }
    }

}