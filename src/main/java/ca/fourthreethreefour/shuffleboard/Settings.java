package ca.fourthreethreefour.shuffleboard;

import java.util.Map;

import ca.fourthreethreefour.teleop.Teleop;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
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

    // Example of ShuffleboardTab setup.
    private ShuffleboardTab portsTab = Shuffleboard.getTab("Ports");
    public ShuffleboardTab settingsTab = Shuffleboard.getTab("Settings");
    private ShuffleboardTab dashboardTab = Shuffleboard.getTab("Dashboard");
    private ShuffleboardTab outputTab = Shuffleboard.getTab("Output");

    // Example of port (non-dynamic) entries setup
    private NetworkTableEntry DRIVER_CONTROLLER_PORT_ENTRY;
    static public int DRIVER_CONTROLLER_PORT;

    private NetworkTableEntry CARGO_BUTTON_PORT_ENTRY;
    static public int CARGO_BUTTON_PORT;

    private NetworkTableEntry GEAR_SOLENOID_PORT_1_ENTRY;
    static public int GEAR_SOLENOID_PORT_1;
    private NetworkTableEntry GEAR_SOLENOID_PORT_2_ENTRY;
    static public int GEAR_SOLENOID_PORT_2;

    private NetworkTableEntry FRONT_LEFT_MOTOR_PORT_ENTRY;
    static public int FRONT_LEFT_MOTOR_PORT;
    private NetworkTableEntry REAR_LEFT_MOTOR_PORT_ENTRY;
    static public int REAR_LEFT_MOTOR_PORT;
    private NetworkTableEntry FRONT_RIGHT_MOTOR_PORT_ENTRY;
    static public int FRONT_RIGHT_MOTOR_PORT;
    private NetworkTableEntry REAR_RIGHT_MOTOR_PORT_ENTRY;
    static public int REAR_RIGHT_MOTOR_PORT;

    private NetworkTableEntry CARGO_OUTTAKE_LEFT_PORT_ENTRY;
    static public int CARGO_OUTTAKE_LEFT_PORT;
    private NetworkTableEntry CARGO_OUTTAKE_RIGHT_PORT_ENTRY;
    static public int CARGO_OUTTAKE_RIGHT_PORT;
    private NetworkTableEntry INTAKE_ROTATE_PORT_1_ENTRY;
    static public int INTAKE_ROTATE_PORT_1;
    private NetworkTableEntry INTAKE_ROTATE_PORT_2_ENTRY;
    static public int INTAKE_ROTATE_PORT_2;

    private NetworkTableEntry HATCH_SOLENOID_PORT_1_ENTRY;
    static public int HATCH_SOLENOID_PORT_1;
    private NetworkTableEntry HATCH_SOLENOID_PORT_2_ENTRY;
    static public int HATCH_SOLENOID_PORT_2;

    private NetworkTableEntry MECHANUM_MOTOR_PORT_ENTRY;
    static public int MECHANUM_MOTOR_PORT;
    private NetworkTableEntry MECHANUM_SOLENOID_PORT_1_ENTRY;
    static public int MECHANUM_SOLENOID_PORT_1;
    private NetworkTableEntry MECHANUM_SOLENOID_PORT_2_ENTRY;
    static public int MECHANUM_SOLENOID_PORT_2;

    NetworkTableEntry REVERSABLE_CONTROLS_ENTRY;
    static public boolean REVERSABLE_CONTROLS;

    // Dynamic settings
    NetworkTableEntry LOGGING_ENABLED_ENTRY;
    static public boolean LOGGING_ENABLED;
    NetworkTableEntry DRIVE_SPEED_ENTRY;
    static public double DRIVE_SPEED;
    NetworkTableEntry TURN_CURVE_ENTRY;
    static public double TURN_CURVE;
    NetworkTableEntry INTAKE_ROTATE_SPEED_ENTRY;
    static public double INTAKE_ROTATE_SPEED;

    static public NetworkTableEntry DRIVE_DIRECTION_ENTRY;

    /**
     * Creates the specified objects for shuffleboard, and updates the variables that only
     * needs to be updated at initialization.
     */
    public void ShuffleInit(Teleop teleop) {
        UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture("Microsoft LifeCam HD-3000 (Image Analysis)", 0);

        camera1.setResolution(320, 240);
        camera1.setFPS(30);
            dashboardTab.add(camera1);
            
            dashboardTab.add(teleop.arm);
            //dashboardTab.add(teleop.drive.driveTrain);

        DRIVE_DIRECTION_ENTRY = dashboardTab.addPersistent("Drive Direction", true).getEntry();

        DRIVER_CONTROLLER_PORT_ENTRY = portsTab.addPersistent("Driver Controller", 0).getEntry();
            DRIVER_CONTROLLER_PORT = (int) DRIVER_CONTROLLER_PORT_ENTRY.getDouble(0);

        CARGO_BUTTON_PORT_ENTRY = portsTab.addPersistent("Cargo Button", 0).getEntry();
            CARGO_BUTTON_PORT = (int) CARGO_BUTTON_PORT_ENTRY.getDouble(0);

        GEAR_SOLENOID_PORT_1_ENTRY = portsTab.addPersistent("Gear Solenoid 1", 2).getEntry();
            GEAR_SOLENOID_PORT_1 = (int) GEAR_SOLENOID_PORT_1_ENTRY.getDouble(2);
        GEAR_SOLENOID_PORT_2_ENTRY = portsTab.addPersistent("Gear Solenoid 2", 3).getEntry();
            GEAR_SOLENOID_PORT_2 = (int) GEAR_SOLENOID_PORT_2_ENTRY.getDouble(3);

        FRONT_LEFT_MOTOR_PORT_ENTRY = portsTab.addPersistent("Front Left Motor", 0).getEntry();
            FRONT_LEFT_MOTOR_PORT = (int) FRONT_LEFT_MOTOR_PORT_ENTRY.getDouble(0);
        REAR_LEFT_MOTOR_PORT_ENTRY = portsTab.addPersistent("Rear Left Motor", 1).getEntry();
            REAR_LEFT_MOTOR_PORT = (int) REAR_LEFT_MOTOR_PORT_ENTRY.getDouble(1);
        FRONT_RIGHT_MOTOR_PORT_ENTRY = portsTab.addPersistent("Front Right Motor", 2).getEntry();
            FRONT_RIGHT_MOTOR_PORT = (int) FRONT_RIGHT_MOTOR_PORT_ENTRY.getDouble(2);
        REAR_RIGHT_MOTOR_PORT_ENTRY = portsTab.addPersistent("Rear Right Motor", 3).getEntry();
            REAR_RIGHT_MOTOR_PORT = (int) REAR_RIGHT_MOTOR_PORT_ENTRY.getDouble(3);
    
        CARGO_OUTTAKE_LEFT_PORT_ENTRY = portsTab.addPersistent("Cargo Outtake Left Motor", 2).getEntry();
            CARGO_OUTTAKE_LEFT_PORT = (int) CARGO_OUTTAKE_LEFT_PORT_ENTRY.getDouble(2);
        CARGO_OUTTAKE_RIGHT_PORT_ENTRY = portsTab.addPersistent("Cargo Outtake Right Motor", 3).getEntry();
            CARGO_OUTTAKE_RIGHT_PORT = (int) CARGO_OUTTAKE_RIGHT_PORT_ENTRY.getDouble(3);
        INTAKE_ROTATE_PORT_1_ENTRY = portsTab.addPersistent("Shooter Arm Motor 1", 0).getEntry();
            INTAKE_ROTATE_PORT_1 = (int) INTAKE_ROTATE_PORT_1_ENTRY.getDouble(0);
        INTAKE_ROTATE_PORT_2_ENTRY = portsTab.addPersistent("Shooter Arm Motor 2", 1).getEntry();
            INTAKE_ROTATE_PORT_2 = (int) INTAKE_ROTATE_PORT_2_ENTRY.getDouble(1);
        
        HATCH_SOLENOID_PORT_1_ENTRY = portsTab.addPersistent("Hatch Solenoid 1", 0).getEntry();
            HATCH_SOLENOID_PORT_1 = (int) HATCH_SOLENOID_PORT_1_ENTRY.getDouble(0);
        HATCH_SOLENOID_PORT_2_ENTRY = portsTab.addPersistent("Hatch Solenoid 2", 1).getEntry();
            HATCH_SOLENOID_PORT_2 = (int) HATCH_SOLENOID_PORT_2_ENTRY.getDouble(1);

        MECHANUM_MOTOR_PORT_ENTRY = portsTab.addPersistent("Mechanum Motor", 4).getEntry();
            MECHANUM_MOTOR_PORT = (int) MECHANUM_MOTOR_PORT_ENTRY.getDouble(4);
        MECHANUM_SOLENOID_PORT_1_ENTRY = portsTab.addPersistent("Mechanum Solenoid 1", 5).getEntry();
            MECHANUM_SOLENOID_PORT_1 = (int) MECHANUM_SOLENOID_PORT_1_ENTRY.getDouble(5);
        MECHANUM_SOLENOID_PORT_2_ENTRY = portsTab.addPersistent("Mechanum Solenoid 2", 6).getEntry();
            MECHANUM_SOLENOID_PORT_2 = (int) MECHANUM_SOLENOID_PORT_2_ENTRY.getDouble(6);

        REVERSABLE_CONTROLS_ENTRY = settingsTab.addPersistent("Reversable Controls", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
            REVERSABLE_CONTROLS = REVERSABLE_CONTROLS_ENTRY.getBoolean(true);


        LOGGING_ENABLED_ENTRY = settingsTab.addPersistent("Logging", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        DRIVE_SPEED_ENTRY = settingsTab.addPersistent("Drive Speed", 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
        TURN_CURVE_ENTRY = settingsTab.addPersistent("Turn Curve", 1.5).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 1, "max", 10)).getEntry();
        INTAKE_ROTATE_SPEED_ENTRY = settingsTab.addPersistent("Intake Rotate Speed", 0.25).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    }

    /**
     * Updates the variables designed to be updated while the robot is powered.
     */
    public void ShufflePeriodic() {
        LOGGING_ENABLED = LOGGING_ENABLED_ENTRY.getBoolean(false);
        DRIVE_SPEED = DRIVE_SPEED_ENTRY.getDouble(1);
        TURN_CURVE = TURN_CURVE_ENTRY.getDouble(1.5);
        INTAKE_ROTATE_SPEED = INTAKE_ROTATE_SPEED_ENTRY.getDouble(0.25);
    }


}