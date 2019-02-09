package ca.fourthreethreefour.teleop.systems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;

public class Encoders {
    
    public static final Encoder leftEncoder = new Encoder(0, 1);
    public static final Encoder rightEncoder = new Encoder(2, 3);
    public static final AnalogGyro gyro = new AnalogGyro(0);
}
