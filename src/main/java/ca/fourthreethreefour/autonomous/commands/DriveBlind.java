package ca.fourthreethreefour.autonomous.commands;

import ca.fourthreethreefour.teleop.Teleop;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveBlind extends TimedCommand {

    Teleop teleop;
    double left;
    double right;

    public DriveBlind(double timeout) {
        super(timeout);
    }

    public DriveBlind(double left, double right, double timeout) {
        super(timeout);
        this.left = left;
        this.right = right;
    }

    protected void execute() {
        teleop.ExtDrive(left, right);
    }

}
