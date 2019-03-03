package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Encoders {

    // public static double potentiometerOffset = -1445;
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);

    public Potentiometer armPotentiometer;

    public void potentiometerInit(double potentiometerOffset) {
        armPotentiometer = new AnalogPotentiometer(0, 1800, potentiometerOffset);
    }

    public void printPotentiometer() {
        System.out.println(armPotentiometer.get());
    }
}
