package ca.fourthreethreefour.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Settings {

    private ShuffleboardTab testTab = Shuffleboard.getTab("Test");

    private NetworkTableEntry TEST_ENTRY;
    public double TEST;

    private NetworkTableEntry TEST_ENTRY_2;
    public double TEST_2;

    public void ShuffleInit() {
        TEST_ENTRY = testTab.addPersistent("Test", 0).getEntry();
        TEST = TEST_ENTRY.getDouble(0);
        TEST_ENTRY_2 = testTab.addPersistent("Test 2", 1).getEntry();
    }

    public void ShufflePeriodic() {
        TEST_2 = TEST_ENTRY_2.getDouble(1);
    }


}