package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.commands.debug.Logging;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ultrasonics extends Subsystem {

    // Assigns all the DIO sensors to their respective objects (the number in brackets is the port # of what is connected where)
    Ultrasonic ultrasonicSensorFrontLeft = new Ultrasonic(30, 29);
    Ultrasonic ultrasonicSensorFrontRight = new Ultrasonic(28, 27);
    Ultrasonic ultrasonicSensorBack = new Ultrasonic(26, 25);
    // Ultrasonic ultrasonicSensorRight = new Ultrasonic(6, 7);

  /** Constructor for initalizing
   * @return void
   */
    public Ultrasonics () {
        // enable();
        // setAutomaticMode(true);
        // ultrasonicPollingThread();
    }

    // Function to start a new thread to poll the ultrasonic sensors
    public void ultrasonicPollingThread()
    {
        // Sets up a new thread that polls at a set interval
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted())
            {
                // Pings the ultrasonic sensors
  
                ultrasonicSensorFrontLeft.ping();
                ultrasonicSensorFrontRight.ping();
                ultrasonicSensorBack.ping();
                // ultrasonicSensorRight.ping();
                Timer.delay(.1);
            }
        });
        // Starts the thread
        thread.start();
    }

    // Enables the ultrasonic sensors to calculate distances (need to be polled to give a reading)
    public void enable() {
        ultrasonicSensorFrontLeft.setEnabled(true);
        ultrasonicSensorFrontRight.setEnabled(true);
        ultrasonicSensorBack.setEnabled(true);
        // ultrasonicSensorRight.setEnabled(true);
    }

//  /** Enables automatic mode on sensors
//    *  @param enabling mode to set
//    * @return void
//    */
//     private void setAutomaticMode(boolean enabling){
//         ultrasonicSensorFront.setAutomaticMode(true);
//         ultrasonicSensorLeft.setAutomaticMode(true);
//         ultrasonicSensorBack.setAutomaticMode(true);
//         ultrasonicSensorRight.setAutomaticMode(true);
//     }

  /**
   *  Logging to shuffleboard
   * @return void
   */
    public void printValues()
    {
        Logging.log("--------------------------------------------------------------------------");
        Logging.log("Ultrasonic Front Left: " + String.valueOf((ultrasonicSensorFrontLeft.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Front Left", 999.0) : ultrasonicSensorFrontLeft.getRangeInches()));
        Logging.log("Ultrasonic Front Right" + String.valueOf((ultrasonicSensorFrontRight.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Front Right", 999.0) : ultrasonicSensorFrontRight.getRangeInches()));
        Logging.log("Ultrasonic Back: " + String.valueOf((ultrasonicSensorBack.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Back", 999.0) : ultrasonicSensorBack.getRangeInches()));
        // Logging.log("Ultrasonic Right"+ String.valueOf( (ultrasonicSensorRight.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Right", 999.0) : ultrasonicSensorRight.getRangeInches()));
        Logging.log("--------------------------------------------------------------------------");
    }
    @Override
    protected void initDefaultCommand() {
  
    }

    public double getUltrasonicFrontValue() {
        return ultrasonicSensorFront.isRangeValid() ? ultrasonicSensorFront.getRangeInches() : 999.0;
    }
}
