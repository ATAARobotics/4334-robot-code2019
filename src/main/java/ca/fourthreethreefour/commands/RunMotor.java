package ca.fourthreethreefour.commands;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class RunMotor extends TimedCommand {

    private final SpeedController motor;
    private final double speed;

    public RunMotor(SpeedController motor, double speed, double timeout) {
        super(timeout);
        this.motor = motor;
        this.speed = speed;
    }

    public RunMotor(SpeedController motor, double timeout) {
        super(timeout);
        this.motor = motor;
        this.speed = 1;
    }

    public void execute() {
        motor.set(speed);
    }
}
