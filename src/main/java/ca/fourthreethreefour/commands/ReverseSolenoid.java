package ca.fourthreethreefour.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ReverseSolenoid {

    private final DoubleSolenoid solenoid;
    private final DoubleSolenoid.Value defaultDirection;

	public ReverseSolenoid (DoubleSolenoid solenoid) {
		this.solenoid = solenoid;
		this.defaultDirection = DoubleSolenoid.Value.kForward;
    }

    public ReverseSolenoid (DoubleSolenoid solenoid, DoubleSolenoid.Value defaultDirection) {
		this.solenoid = solenoid;
		this.defaultDirection = defaultDirection;
    }
    
	public void reverse() {
		if (solenoid.get() == DoubleSolenoid.Value.kOff) {
			solenoid.set(defaultDirection);
		} else if (solenoid.get() == DoubleSolenoid.Value.kReverse) {
			solenoid.set(DoubleSolenoid.Value.kForward);
		} else {
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
	}

}