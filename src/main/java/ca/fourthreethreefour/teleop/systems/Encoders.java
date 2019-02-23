package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.DigitalInput;

public class Encoders {
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);
}
