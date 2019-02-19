package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Encoders {
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public Potentiometer armPotentiometer = new AnalogPotentiometer(0, 360);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);
}
