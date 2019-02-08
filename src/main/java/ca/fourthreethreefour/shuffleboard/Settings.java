package ca.fourthreethreefour.shuffleboard;

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
    ShuffleboardTab settingsTab = Shuffleboard.getTab("Settings");

    // Example of port (non-dynamic) entries setup
    private NetworkTableEntry EXAMPLE_PORT_ENTRY;
    public int EXAMPLE_PORT;

    // Dynamic settings
    NetworkTableEntry LOGGING_ENABLED_ENTRY;
    static public boolean LOGGING_ENABLED;

    /**
     * Creates the specified objects for shuffleboard, and updates the variables that only
     * needs to be updated at initialization.
     */
    public void ShuffleInit() {
        EXAMPLE_PORT_ENTRY = exampleTab.addPersistent("Example Port", 0).getEntry();
        EXAMPLE_PORT = (int) EXAMPLE_PORT_ENTRY.getDouble(0);
        LOGGING_ENABLED_ENTRY = settingsTab.addPersistent("Logging", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
    }

    /**
     * Updates the variables designed to be updated while the robot is powered.
     */
    public void ShufflePeriodic() {
        LOGGING_ENABLED = LOGGING_ENABLED_ENTRY.getBoolean(false);
    }


}