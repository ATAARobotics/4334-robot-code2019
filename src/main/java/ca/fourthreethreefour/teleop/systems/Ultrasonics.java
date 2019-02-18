package ca.fourthreethreefour.teleop.systems;

import ca.fourthreethreefour.commands.debug.Logging;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ultrasonics extends Subsystem {

    // Assigns all the DIO sensors to their respective objects (the number in brackets is the port # of what is connected where)
    Ultrasonic ultrasonicSensorFront = new Ultrasonic(4, 5);
    // Ultrasonic ultrasonicSensorLeft = new Ultrasonic(2, 3);
    Ultrasonic ultrasonicSensorBack = new Ultrasonic(0, 1);
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
  
                ultrasonicSensorFront.ping();
                // ultrasonicSensorLeft.ping();
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
        ultrasonicSensorFront.setEnabled(true);
        // ultrasonicSensorLeft.setEnabled(true);
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
        Logging.log("Ultrasonic Front" + String.valueOf((ultrasonicSensorFront.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Front", 999.0) : ultrasonicSensorFront.getRangeInches()));
        // Logging.log("Ultrasonic Left" + String.valueOf((ultrasonicSensorLeft.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Left", 999.0) : ultrasonicSensorLeft.getRangeInches()));
        Logging.log("Ultrasonic Back" + String.valueOf((ultrasonicSensorBack.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Back", 999.0) : ultrasonicSensorBack.getRangeInches()));
        // Logging.log("Ultrasonic Right"+ String.valueOf( (ultrasonicSensorRight.isRangeValid() == false) ? SmartDashboard.getNumber("Ultrasonic Right", 999.0) : ultrasonicSensorRight.getRangeInches()));
    }
    @Override
    protected void initDefaultCommand() {
  
    }
}
