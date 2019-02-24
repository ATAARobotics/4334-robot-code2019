package ca.fourthreethreefour.vision;

import ca.fourthreethreefour.teleop.Teleop;
import ca.fourthreethreefour.teleop.systems.Encoders;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * VisionPID
 */
public class VisionPID extends PIDSubsystem{

    Encoders encoders;
    Teleop teleop;

    VisionPID(Teleop teleop, Encoders encoders){
        super("Rotation", 2.0, 0.0, 0.0);// The constructor passes a name for the subsystem and the P, I and D constants that are useed when computing the motor output
        setAbsoluteTolerance(0.05);
        getPIDController().setContinuous(false); //manipulating the raw internal PID Controller
        setOutputRange(-1, 1);
        this.teleop = teleop;
        this.encoders = encoders;
    }

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

    
}