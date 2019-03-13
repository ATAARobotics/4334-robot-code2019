package ca.fourthreethreefour.vision;

import ca.fourthreethreefour.teleop.systems.Ultrasonics;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
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

    // Creates Encoders Object
    private Encoders encoders;

    // Initializes Ultrasonics Object
    private Ultrasonics ultrasonics = new Ultrasonics();

    // Creates NetworkTable Items
    private NetworkTableEntry VISION_DRIVE_VALUE;
    private NetworkTableEntry VISION_SPEED_VALUE;
    private NetworkTableEntry VISION_ERROR_NOTARGET;
    NetworkTable table = inst.getTable("datatable");

    //Initialize VisionAssist PID Objects
    private PIDSubsystem visionAlignPID;

    public boolean PIDEnabled = false;

    public Vision(Teleop teleop) {
        VISION_DRIVE_VALUE = table.getEntry("VISION_DRIVE_VALUE");
        VISION_SPEED_VALUE = table.getEntry("VISION_SPEED_VALUE");
        VISION_ERROR_NOTARGET = table.getEntry("VISION_ERROR_NOTARGET");
        this.teleop = teleop;
        this.encoders = teleop.encoders;

        //Configure Vision Align PID
        visionAlignPID = new PIDSubsystem("AlignPID", -0.03, 0.0, 0.01) {
            @Override
            protected double returnPIDInput() {return encoders.getNavXAngle(); }
            @Override
            protected void usePIDOutput(double output) { teleop.ExtArcadeDrive(0, output); }
            @Override
            protected void initDefaultCommand() { }
        };
        visionAlignPID.setAbsoluteTolerance(0.5);
        visionAlignPID.getPIDController().setContinuous(false);
        visionAlignPID.setOutputRange(-1,1);
    }


    public void startVision() throws visionErrorException {
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(true);
        ledRelay.set(Value.kForward);
        if(!piOnline()){
            throw new visionErrorException("Could not verify Pi Online");
        }
    }

    public void stopVision() {
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(false);
        ledRelay.set(Value.kReverse);
    }


    //Alignment PID Commands
    private Double angleGoal;
    public void startAlignPID() {
        angleGoal = encoders.getNavXAngle() + getPiRotation();
        visionAlignPID.setSetpoint(angleGoal);
        visionAlignPID.enable();
        PIDEnabled = true;
    }

    public void stopAlignPID() {
        visionAlignPID.disable();
        PIDEnabled = false;
    }

    public boolean alignDrive() throws visionTargetDetectionException {
        teleop.ExtArcadeDrive(getPiSpeed(), getPiRotation());
        if (VISION_ERROR_NOTARGET.getBoolean(true)) {
            throw new visionTargetDetectionException("Unable to locate more than 1 target");
        }
        if (visionAlignPID.onTarget()) {
            visionAlignPID.disable();
            visionAlignPID.free();
            return (true);
        } else {
            return (false);
        }
    }


    // Access Rotation From NetworkTable
    private double getPiRotation() {
        return (VISION_DRIVE_VALUE.getDouble(0));
    }

    // Access Speed From NetworkTable
    private double getPiSpeed() {
        return (VISION_SPEED_VALUE.getDouble(0.5));
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