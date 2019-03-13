package ca.fourthreethreefour.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ReverseSolenoid {

	//Declares solenoid and default direction variables
    private final DoubleSolenoid solenoid;
    private final DoubleSolenoid.Value defaultDirection;

	public ReverseSolenoid (DoubleSolenoid solenoid) {
		this.solenoid = solenoid;
		this.defaultDirection = DoubleSolenoid.Value.kForward;
    }

    //Constructor that reverses a solenoid to a its default direction
    public ReverseSolenoid (DoubleSolenoid solenoid, DoubleSolenoid.Value defaultDirection) {
		this.solenoid = solenoid;
		this.defaultDirection = defaultDirection;
    }
		
	//Sets the solenoid direction to a default direction if it is off,
	//otherwise it reverses its direction	
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