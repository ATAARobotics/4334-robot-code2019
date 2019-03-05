package ca.fourthreethreefour.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ca.fourthreethreefour.teleop.*;
import ca.fourthreethreefour.vision.exceptions.visionErrorException;
import ca.fourthreethreefour.vision.exceptions.visionTargetDetectionException;
import ca.fourthreethreefour.teleop.systems.Encoders;

/**
 * Vision
 */
public class Vision {

    // Create NetworkTable instance
    private NetworkTableInstance inst = NetworkTableInstance.getDefault();

    // Vision Values
    private ShuffleboardTab dynamicSettingsTab = Shuffleboard.getTab("Dynamic Settings");

    // Creates Shuffleboard Items for Vision
    private NetworkTableEntry VISION_ACTIVE_ENTRY_SHUFFLE = dynamicSettingsTab.addPersistent("Vision Active", false)
            .getEntry();

    // Creates object for LedRing Relay
    private Relay ledRelay = new Relay(1);

    // Creates Teleop Object
    private Teleop teleop;

    // Creates PID Object
    private VisionPID visionPID;

    // Creates Encoders Object
    private Encoders encoders;

    // Creates NetworkTable Items
    private NetworkTableEntry VISION_DRIVE_VALUE;
    private NetworkTableEntry VISION_SPEED_VALUE;
    private NetworkTableEntry VISION_ERROR_NOTARGET;
    NetworkTable table = inst.getTable("datatable");

    public boolean PIDEnabled = false;

    public Vision(Teleop teleop) {
        VISION_DRIVE_VALUE = table.getEntry("VISION_DRIVE_VALUE");
        VISION_SPEED_VALUE = table.getEntry("VISION_SPEED_VALUE");
        VISION_ERROR_NOTARGET = table.getEntry("VISION_ERROR_NOTARGET");
        this.teleop = teleop;
        this.encoders = teleop.encoders;
    }

    // Starts Vision on Pi and Enables LED Ring
    public void startVision() throws visionErrorException {
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(true);
        ledRelay.set(Value.kForward);
        visionPID = new VisionPID(teleop, encoders);
        if(!piOnline()){
            throw new visionErrorException("Could not verify Pi Online");
        }
    }

    // Stops Vision on Pi and Disables LED Ring
    public void stopVision() {
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(false);
        ledRelay.set(Value.kReverse);
    }

    // Access Rotation From NetworkTable
    private double getPiRotation() {
        return (VISION_DRIVE_VALUE.getDouble(0));
    }

    // Access Speed From NetworkTable
    private double getPiSpeed() {
        return (VISION_SPEED_VALUE.getDouble(0.5));
    }

    private Double angleGoal;

    public void startPIDDrive() {
        angleGoal = encoders.getNavXAngle() + getPiRotation();
        visionPID.setSetpoint(angleGoal);
        visionPID.enable();
        PIDEnabled = true;
    }

    public boolean drive() throws visionTargetDetectionException {
        teleop.ExtArcadeDrive(getPiSpeed(), getPiRotation());
        if (VISION_ERROR_NOTARGET.getBoolean(true) == true) {
            throw new visionTargetDetectionException("Unable to lcoate more than 1 target");
        }
        if (visionPID.onTarget()) {
            visionPID.disable();
            visionPID.free();
            return (true);
        } else {
            return (false);
        }
    }

    public void stopVisionPID() {
        visionPID.disable();
        PIDEnabled = false;
    }

    public boolean piOnline() {
        InetAddress address;
        boolean reachable;
        try {
            address = InetAddress.getByName("frcvision.local");
            reachable = address.isReachable(1500);
        } catch (IOException e) {
            e.printStackTrace();
            reachable = false;
        }
        return(reachable);
    }

}