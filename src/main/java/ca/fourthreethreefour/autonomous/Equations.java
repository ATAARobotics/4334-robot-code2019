package ca.fourthreethreefour.autonomous;

import ca.fourthreethreefour.settings.Settings;

public class Equations {

    public double shootFormula(double distance) {
        double sinValue = (19.62*distance) / Math.pow(Settings.BALL_VELOCITY_METERS_PER_SECOND, 2);
        double result = Math.toDegrees(Math.asin(sinValue));
        result = result * 1/2;
        return result;
    }

}