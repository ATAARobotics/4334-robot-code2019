package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Encoders {

    double potentiometerOffset;

    public Encoders(double offSet) {
        potentiometerOffset = offSet;
    }
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);

    public Potentiometer armPotentiometer = new AnalogPotentiometer(0, 1800, -1506);

    public void printPotentiometer() {
        System.out.println(armPotentiometer.get());
    }
}
