package ca.fourthreethreefour.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;


/**
 * VisionProcessing
 */
public class VisionProcessing {
    
    //Create NetworkTable instance
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();

    //Vision Values
    private ShuffleboardTab dynamicSettingsTab = Shuffleboard.getTab("Dynamic Settings");
    private ShuffleboardTab visionSettingsTab = Shuffleboard.getTab("Vision Settings");
    
    //Creates Shuffleboard Items for Vision
    private NetworkTableEntry VISION_ACTIVE_ENTRY_SHUFFLE = dynamicSettingsTab.addPersistent("Vision Active", false).getEntry();


    //Creates NetworkTable Items
    private NetworkTableEntry VISION_DRIVE_VALUE;
    private NetworkTableEntry VISION_SPEED_VALUE;
    NetworkTable table = inst.getTable("datatable");

    //Creates object for LedRing Relay
    private Relay ledRelay = new Relay(1);


    public VisionProcessing(){
        VISION_DRIVE_VALUE = table.getEntry("VISION_DRIVE_VALUE");
        VISION_SPEED_VALUE = table.getEntry("VISION_SPEED_VALUE");
    }

    //Starts Vision on Pi and Enables LED Ring
    public void startVision(){
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(true);
        ledRelay.set(Value.kForward);
        
    }

    //Stops Vision on Pi and Disables LED Ring 
    public void stopVision(){
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(false);
        ledRelay.set(Value.kReverse);
    }

    //Access Rotation From Shuffleboard
    public double getVisionRotation(){
        return(VISION_DRIVE_VALUE.getDouble(0));
    }
    
    //Access Speed From Shuffleboard
    public double getVisionSpeed(){
        return(VISION_SPEED_VALUE.getDouble(0.5));
    }
}