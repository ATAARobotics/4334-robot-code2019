package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Encoders {

    // public static double potentiometerOffset = -1445;
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_DIGITAL_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(Settings.ARM_INNER_LIMITSWITCH_DIGITAL_PORT);

    public Potentiometer armPotentiometer;

    public void potentiometerInit(double potentiometerOffset) {
        armPotentiometer = new AnalogPotentiometer(Settings.ARM_POTENTIOMETER_ANALOG_PORT, Settings.ARM_POTENTIOMETER_RANGE, potentiometerOffset);
    }

    public void printPotentiometer() {
        Logging.log("Potentiometer value" + armPotentiometer.get());
    }
}
