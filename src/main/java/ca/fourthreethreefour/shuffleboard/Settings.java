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
    private ShuffleboardTab exampleTab = Shuffleboard.getTab("Example");
    public ShuffleboardTab settingsTab = Shuffleboard.getTab("Settings");
    private ShuffleboardTab dashboardTab = Shuffleboard.getTab("Dashboard");

    // Example of port (non-dynamic) entries setup
    private NetworkTableEntry EXAMPLE_PORT_ENTRY;
    static public int EXAMPLE_PORT;

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
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            dashboardTab.add(camera);
            dashboardTab.add(teleop.drive.driveTrain);

        EXAMPLE_PORT_ENTRY = exampleTab.addPersistent("Example Port", 0).getEntry();
        EXAMPLE_PORT = (int) EXAMPLE_PORT_ENTRY.getDouble(0);
        LOGGING_ENABLED_ENTRY = settingsTab.addPersistent("Logging", true)
            .withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        DRIVE_SPEED_ENTRY = settingsTab.addPersistent("Drive Speed", 1)
            .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("Min", 0, "Max", 1)).getEntry();
        TURN_CURVE_ENTRY = settingsTab.addPersistent("Turn Curve", 1.5)
            .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("Min", 1, "Max", 10)).getEntry();
        INTAKE_ROTATE_SPEED_ENTRY = settingsTab.addPersistent("Intake Rotate Speed", 0.25)
            .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("Min", 0, "Max", 1)).getEntry();
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