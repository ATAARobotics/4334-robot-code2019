package ca.fourthreethreefour.teleop.systems;

import com.kauailabs.navx.frc.AHRS;
import ca.fourthreethreefour.shuffleboard.Settings;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;



public class Encoders {
    
    public DigitalInput cargoButton = new DigitalInput(Settings.CARGO_BUTTON_PORT);
    public DigitalInput armInnerLimitSwitch = new DigitalInput(2);

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
