package ca.fourthreethreefour.teleop.systems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

public class Encoders {
    
    //Creates left and right encoder objects and sets
    //them to their respective digital input channels
    public Encoder leftEncoder = new Encoder(0, 1);
    public Encoder rightEncoder = new Encoder(2, 3);
    public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    // public AHRS navX;

    // public void initalizeNavX() {
    //     try
    //     {
    //         // Initializes the navX object on the roboRIO's MXP port and resets itw
    //         navX = new AHRS(SPI.Port.kMXP);
    //         navX.reset();
    //     } catch (RuntimeException ex)
    //     {
    //         DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
    //     }
    // }
}
