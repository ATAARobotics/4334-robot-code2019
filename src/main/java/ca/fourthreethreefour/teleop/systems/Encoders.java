package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.commands.debug.Logging;
import com.kauailabs.navx.frc.AHRS;
import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;



public class Encoders {

    // public static double potentiometerOffset = -1445;
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);

    public Potentiometer armPotentiometer;

    public void potentiometerInit(double potentiometerOffset) {
        armPotentiometer = new AnalogPotentiometer(0, 1800, potentiometerOffset);
    }

    public void printPotentiometer() {
        Logging.log("Potentiometer value" + armPotentiometer.get());
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
