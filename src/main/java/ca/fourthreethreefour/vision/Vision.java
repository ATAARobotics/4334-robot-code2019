package ca.fourthreethreefour.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import ca.fourthreethreefour.teleop.*;
import ca.fourthreethreefour.teleop.systems.Encoders;


/**
 * Vision
 */
public class Vision {
    
    //Create NetworkTable instance
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();

    //Vision Values
    private ShuffleboardTab dynamicSettingsTab = Shuffleboard.getTab("Dynamic Settings");
    
    //Creates Shuffleboard Items for Vision
    private NetworkTableEntry VISION_ACTIVE_ENTRY_SHUFFLE = dynamicSettingsTab.addPersistent("Vision Active", false).getEntry();

    //Creates object for LedRing Relay
    private Relay ledRelay = new Relay(1);

    //Creates Teleop Object
    private Teleop teleop;

    //Creates PID Object
    private VisionPID visionPID;

    //Creates Encoders Object
    private Encoders encoders = new Encoders();

    //Creates NetworkTable Items
    private NetworkTableEntry VISION_DRIVE_VALUE;
    private NetworkTableEntry VISION_SPEED_VALUE;
    private NetworkTableEntry VISION_PID_P;
    private NetworkTableEntry VISION_PID_I;
    private NetworkTableEntry VISION_PID_D;
    private NetworkTable table = inst.getTable("datatable");

    public Vision(Teleop teleop){
        VISION_DRIVE_VALUE = table.getEntry("VISION_DRIVE_VALUE");
        VISION_SPEED_VALUE = table.getEntry("VISION_SPEED_VALUE");
        VISION_PID_P = table.getEntry("VISION_PID_P");
        VISION_PID_I = table.getEntry("VISION_PID_I");
        VISION_PID_D = table.getEntry("VISION_PID_D");
        this.teleop = teleop;
    }

    //Starts Vision on Pi and Enables LED Ring
    public void startVision(){
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(true);
        ledRelay.set(Value.kForward);
        encoders.initalizeNavX();
        encoders.navX.reset();
        visionPID = new VisionPID(teleop, encoders);
        
    }

    //Stops Vision on Pi and Disables LED Ring 
    public void stopVision(){
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(false);
        ledRelay.set(Value.kReverse);
    }

    //Access Rotation From NetworkTable
    private double getPiRotation(){
        return(VISION_DRIVE_VALUE.getDouble(0));
    }
    
    //Access Speed From NetworkTable
    private double getPiSpeed(){
        return(VISION_SPEED_VALUE.getDouble(0.5));
    }

    private Double angleGoal;

    public void startPIDDrive(){
        encoders.navX.reset();
        angleGoal = encoders.getNavXAngle() + getPiRotation();
        visionPID.setSetpoint(angleGoal);
        visionPID.enable();
    }

    public boolean drive(){
        if(visionPID.onTarget()){
            visionPID.disable();
            visionPID.free();
            return(true);
        } else {
            return(false);
        }
    }

}