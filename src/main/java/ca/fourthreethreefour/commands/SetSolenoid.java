package ca.fourthreethreefour.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SetSolenoid {

    private final DoubleSolenoid solenoid;
    private final DoubleSolenoid.Value direction;

    public SetSolenoid(DoubleSolenoid solenoid, DoubleSolenoid.Value direction) {
        this.solenoid = solenoid;
        this.direction = direction;
    };

    public void set() {
        solenoid.set(direction);
    }   
}