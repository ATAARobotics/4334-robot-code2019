package ca.fourthreethreefour.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetSolenoid {

    //Declares solenoid and direction variables
    private final DoubleSolenoid solenoid;
    private final DoubleSolenoid.Value direction;

    //Constructor to set a solenoid to a specified direction
    public SetSolenoid(DoubleSolenoid solenoid, DoubleSolenoid.Value direction) {
        this.solenoid = solenoid;
        this.direction = direction;
    };

    //Sets the solenoid to a specified direction
    public void set() {
        solenoid.set(direction);
    }   
}