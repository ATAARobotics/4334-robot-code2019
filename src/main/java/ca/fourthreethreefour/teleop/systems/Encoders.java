package ca.fourthreethreefour.teleop.systems;

import com.kauailabs.navx.frc.AHRS;

import ca.fourthreethreefour.commands.debug.Logging;
import ca.fourthreethreefour.settings.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.Solenoid;



public class Encoders {

    //public static double potentiometerOffset = -1445;
    //private double hatchPotentiometerOffset = -5.6;
    //private int hatchPotentiometerScale = 6000; //Todo: Tune scale

    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_DIGITAL_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(Settings.ARM_INNER_LIMITSWITCH_DIGITAL_PORT);
    public Servo test = new Servo(7);
    public DigitalInput hatchHallEffectLeft = new DigitalInput(Settings.HALL_EFFECT_LEFT_DIGITAL_PORT);
    public DigitalInput hatchHallEffectRight = new DigitalInput(Settings.HALL_EFFECT_RIGHT_DIGITAL_PORT);

    private Potentiometer armPotentiometer;
    private Potentiometer hatchPotentiometer;

    public void potentiometerInit() {
        armPotentiometer = new AnalogPotentiometer(Settings.ARM_POTENTIOMETER_ANALOG_PORT, Settings.ARM_POTENTIOMETER_RANGE);
        hatchPotentiometer = new AnalogPotentiometer(Settings.HATCH_POTENTIOMETER_ANALOG_PORT, Settings.HATCH_POTENTIOMETER_RANGE);
    }

    public double armPotentiometerGet() {
        return armPotentiometer.get() + Settings.ARM_POTENTIOMETER_OFFSET;
    }

    public double hatchPotentiometerGet() {
        return hatchPotentiometer.get() + Settings.HATCH_POTENTIOMETER_OFFSET;
    }

    public void printPotentiometer() {
        //Logging.log("Arm Potentiometer Value" + armPotentiometerGet() + "   Hatch Potentiometer Value" + hatchPotentiometerGet());
        Logging.logf("Arm Potentiometer Value: %4.2f Hatch Potentiometer Value: %.2f", armPotentiometerGet(), hatchPotentiometerGet());
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
