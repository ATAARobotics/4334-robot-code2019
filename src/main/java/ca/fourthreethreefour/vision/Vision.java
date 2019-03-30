package ca.fourthreethreefour.vision;

import java.io.IOException;
import java.net.InetAddress;

import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.teleop.systems.Encoders;
import ca.fourthreethreefour.vision.exceptions.visionErrorException;
import ca.fourthreethreefour.vision.exceptions.visionTargetDetectionException;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

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
    public Relay ledRelay = new Relay(0);

    // Creates Teleop Object
    private Teleop teleop;

    // Creates Encoders Object
    private Encoders encoders;

    // Initializes Ultrasonics Object
    //private Ultrasonics ultrasonics = new Ultrasonics();

    // Creates NetworkTable Items
    private NetworkTableEntry VISION_DRIVE_VALUE;
    private NetworkTableEntry VISION_SPEED_VALUE;
    private NetworkTableEntry VISION_ERROR_NOTARGET;
    NetworkTable table = inst.getTable("datatable");

    //Initialize VisionAssist PID Objects
    private PIDSubsystem visionAlignPID;

    public boolean PIDEnabled = false;
    boolean isEnabled;
    public boolean visionActive = false;

    public Vision(Teleop teleop) {
        //Create Shuffle Board Objects
        VISION_DRIVE_VALUE = table.getEntry("VISION_DRIVE_VALUE");
        VISION_SPEED_VALUE = table.getEntry("VISION_SPEED_VALUE");
        VISION_ERROR_NOTARGET = table.getEntry("VISION_ERROR_NOTARGET");

        //Sets Teleop Object
        this.teleop = teleop;

        //Sets Encoders Object
        this.encoders = teleop.encoders;

        //Configure Vision Align PID
        visionAlignPID = new PIDSubsystem("AlignPID", -0.03, 0.0, 0.01) {
            @Override
            protected double returnPIDInput() {
                return encoders.getNavXAngle(); 
            }
            @Override
            protected void usePIDOutput(double output) { 
                teleop.ExtArcadeDrive(0, output); 
            }
            @Override
            protected void initDefaultCommand() {

            }

            @Override
            public void enable() {
                //Enabled PID
                super.enable();
                //Set enabled variable to true
                isEnabled = true;
            }

            @Override
            public void disable() {
                //Disable PID
                super.disable();
                //Set enabled variable to false
                isEnabled = false;
            }
        };

        //Configures then disables the PID Controller
        visionAlignPID.setAbsoluteTolerance(0.5);
        visionAlignPID.getPIDController().setContinuous(false);
        visionAlignPID.setOutputRange(-1,1);
        visionAlignPID.disable();
    }


    public void startVision() throws visionErrorException {
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(true);
        //Enabled Relay for LED Ring
        ledRelay.set(Value.kOn);
        ledRelay.set(Value.kReverse);

        //Attempts to ping RaspberryPi to verify connection
        if(!piOnline()){
            //Throw exception if Pi is offline
            throw new visionErrorException("Could not verify Pi Online");
        } else {
            visionActive = true;
        }
    }

    public void stopVision() {
        VISION_ACTIVE_ENTRY_SHUFFLE.setBoolean(false);
        ledRelay.set(Value.kOff);
        visionActive = false;
    }


    //Alignment PID Commands
    private Double angleGoal;
    public void startAlignPID() {
        //Calculates the goal NAVX angle
        angleGoal = encoders.getNavXAngle() + getPiRotation();

        //Sets the PID controller setpoint
        visionAlignPID.setSetpoint(angleGoal);

        //Enables the PID controller
        visionAlignPID.enable();

        PIDEnabled = true;
    }

    public void stopAlignPID() {
        //Disables the PID Controller
        visionAlignPID.disable();
        PIDEnabled = false;
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public boolean checkAlign() throws visionTargetDetectionException {
        if (VISION_ERROR_NOTARGET.getBoolean(true)) {
            //Throws exception if unable to detect at least two targets
            throw new visionTargetDetectionException("Unable to locate more than 1 target");
        }
        if (visionAlignPID.onTarget()) {
            //Disable the PID controller
            visionAlignPID.disable();
            return true;
        } else {
            return false;
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
        //Try to ping the RaspberryPi
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