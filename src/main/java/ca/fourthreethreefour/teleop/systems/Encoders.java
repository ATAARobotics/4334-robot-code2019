package ca.fourthreethreefour.teleop.systems;

import com.kauailabs.navx.frc.AHRS;
import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.DriverStation;



public class Encoders {

    public int hatchPotentiometerOffset = 0;
    public int hatchPotentiometerRange = 0;
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);
    public Potentiometer hatchPotentiometer =  new AnalogPotentiometer(4, hatchPotentiometerRange, hatchPotentiometerOffset); // TODO Update ports, scale, and offset
    public DigitalInput hatchHalleffectLeft = new DigitalInput(3); // TODO Update ports
    public DigitalInput hatchHalleffectRight = new DigitalInput(4); // TODO Update ports


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
