package ca.fourthreethreefour.teleop.systems;

import com.kauailabs.navx.frc.AHRS;

import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import ca.fourthreethreefour.commands.debug.Logging;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;



public class Encoders {

    // public static double potentiometerOffset = -1445;
    private double hatchPotentiometerOffset = -5.6;
    private int hatchPotentiometerScale = 60; //Todo: Tune scale

    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_DIGITAL_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(Settings.ARM_INNER_LIMITSWITCH_DIGITAL_PORT);
    public Servo test = new Servo(7);
    public DigitalInput hatchHallEffectLeft = new DigitalInput(0);
    public DigitalInput hatchHallEffectRight = new DigitalInput(1);

    private Potentiometer armPotentiometer;
    private Potentiometer hatchPotentiometer;

    public void potentiometerInit() {
        armPotentiometer = new AnalogPotentiometer(Settings.ARM_POTENTIOMETER_ANALOG_PORT, Settings.ARM_POTENTIOMETER_RANGE);
        hatchPotentiometer = new AnalogPotentiometer(4, hatchPotentiometerScale);
    }

    public double armPotentiometerGet() {
        return armPotentiometer.get() + Settings.ARM_POTENTIOMETER_OFFSET;
    }

    public double hatchPotentiometerGet() {
        return hatchPotentiometer.get() + hatchPotentiometerOffset;
    }

    public void printArmPotentiometer() {
        Logging.log("Arm Potentiometer Value" + armPotentiometerGet());
    }

    public void printHatchPotentiometer() {
        Logging.log("Hatch Potentiometer Value" + hatchPotentiometerGet());
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
