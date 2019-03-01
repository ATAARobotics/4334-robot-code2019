package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Notifier;
//import jaci.pathfinder.PathfinderFRC;
//import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

//import java.io.IOException;

public class Encoders {
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public EncoderFollower drivetrainLeftController = new EncoderFollower();
    public EncoderFollower drivetrainRightController = new EncoderFollower();
    public Notifier autoController;
    public final String pathWeaverPathName = "Straight1";
}
