package ca.fourthreethreefour.teleop.systems;

import com.kauailabs.navx.frc.AHRS;

import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;



public class Encoders {

    // public static double potentiometerOffset = -1445;

    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_DIGITAL_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(Settings.ARM_INNER_LIMITSWITCH_DIGITAL_PORT);

    private Potentiometer armPotentiometer;

    public void potentiometerInit() {
        armPotentiometer = new AnalogPotentiometer(Settings.ARM_POTENTIOMETER_ANALOG_PORT, Settings.ARM_POTENTIOMETER_RANGE);
    }

    public double potentiometerGet() {
        return armPotentiometer.get() + Settings.ARM_POTENTIOMETER_OFFSET;
    }

    public void printPotentiometer() {
        Logging.log("Potentiometer value" + potentiometerGet());
    }

    public AHRS navX;

    public void initalizeNavX() {
        try
        {
            // Initializes the navX object on the roboRIO's MXP port and resets itw
            navX = new AHRS(SPI.Port.kMXP);
            navX.reset();
        } catch (RuntimeException ex)
        {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }
    }

    public void resetNavX() {
        navX.reset();
    }

    public double getNavXAngle() {
        return navX.getAngle();
    }
}
