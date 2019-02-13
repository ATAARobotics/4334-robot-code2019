package ca.fourthreethreefour.teleop.systems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ultrasonics extends Subsystem {

    // Assigns all the DIO sensors to their respective objects (the number in brackets is the port # of what is connected where)
    Ultrasonic ultrasonicSensorFront = new Ultrasonic(4, 5);
    Ultrasonic ultrasonicSensorLeft = new Ultrasonic(2, 3);
    Ultrasonic ultrasonicSensorBack = new Ultrasonic(0, 1);
    Ultrasonic ultrasonicSensorRight = new Ultrasonic(6, 7);

    // Function to start a new thread to poll the ultrasonic sensors
    public void ultrasonicPollingThread()
    {
        // Sets up a new thread that polls at a set interval
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted())
            {
                // Pings the ultrasonic sensors
                ultrasonicSensorFront.ping();
                ultrasonicSensorLeft.ping();
                ultrasonicSensorBack.ping();
                ultrasonicSensorRight.ping();
                Timer.delay(.1);
            }
        });
        // Starts the thread
        thread.start();
    }

    // Enables the ultrasonic sensors to calculate distances (need to be polled to give a reading)
    public void enable() {
        ultrasonicSensorFront.setEnabled(true);
        ultrasonicSensorLeft.setEnabled(true);
        ultrasonicSensorBack.setEnabled(true);
        ultrasonicSensorRight.setEnabled(true);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
