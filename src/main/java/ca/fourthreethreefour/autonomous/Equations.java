package ca.fourthreethreefour.autonomous;

import ca.fourthreethreefour.settings.Settings;

public class Equations {

    public double shootShipFormula(double distance) {
        double cosValueTop = ((distance * distance) * (10.81 - Math.sqrt(116.856-96.236*((distance * distance)+0.581))));
        double cosValueBottom = 2*(Settings.BALL_VELOCITY_METERS_PER_SECOND * Settings.BALL_VELOCITY_METERS_PER_SECOND)*((distance*distance)+0.581);
        double cosValue = Math.sqrt(cosValueTop/cosValueBottom);
        // double cosValue = (19.62*distance) / Math.pow(Settings.BALL_VELOCITY_METERS_PER_SECOND, 2);
        double result = Math.toDegrees(Math.acos(cosValue));
        // result = result * 1/2;
        return result;
    }

    public double shootRocketFormula(double distance) {
        double cosValueTop = ((distance - 0.3048) * (distance - 0.3048)) * (10.81 - Math.sqrt((116.856-96.236)* (0.1265 + ((distance - 0.3048) * (distance - 0.3048)))));
        double cosValueBottom = 2*(Settings.BALL_VELOCITY_METERS_PER_SECOND * Settings.BALL_VELOCITY_METERS_PER_SECOND)*(0.1265+((distance-0.3048)*(distance-0.3048)));
        double cosValue = Math.sqrt(cosValueTop/cosValueBottom);
        double result = Math.toDegrees(Math.acos(cosValue));
        return result;
    }

}