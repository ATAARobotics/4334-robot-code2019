package ca.fourthreethreefour.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
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

    // Example of port (non-dynamic) entries setup
    private NetworkTableEntry EXAMPLE_PORT_ENTRY;
    public int EXAMPLE_PORT;

    // Example of dynamic entry setup
    private NetworkTableEntry EXAMPLE_ENTRY;
    public double EXAMPLE;

    public void ShuffleInit() {
        // Creates the object on shuffleboard, and updates the port value
        EXAMPLE_PORT_ENTRY = exampleTab.addPersistent("Example Port", 0).getEntry();
        EXAMPLE_PORT = (int) EXAMPLE_PORT_ENTRY.getDouble(0);
        EXAMPLE_ENTRY = exampleTab.addPersistent("Example", 1).getEntry();
    }

    public void ShufflePeriodic() {
        // Updates the dynamic values
        EXAMPLE = EXAMPLE_ENTRY.getDouble(1);
    }


}