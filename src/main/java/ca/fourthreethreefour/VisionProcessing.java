package ca.fourthreethreefour;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;


/**
 * VisionProcessing
 */
public class VisionProcessing {
    public VisionProcessing(){
    }

    //Vision Values
    ShuffleboardTab dynamicSettingsTab = Shuffleboard.getTab("Dynamic Settings");
    
    NetworkTableEntry DRIVE_VISION_ENTRY = dynamicSettingsTab.addPersistent("Drive Value", 0).getEntry();
    NetworkTableEntry SPEED_VISION_ENTRY = dynamicSettingsTab.addPersistent("Speed Value", 0.8).getEntry();
    

    private double getVisionRotation(){
        return(DRIVE_VISION_ENTRY.getDouble(0));
    }
    
    private double getVisionSpeed(){
        return(SPEED_VISION_ENTRY.getDouble(0.8));
    }

}