package frc.robot.utils;

import frc.robot.Constants;

public class MathHelper {

    public static double distanceToRPM(double distance, boolean upperHub) {
        double inchesToFeet = distance/12;
        if(upperHub) {
            return (5045d+-274d*(inchesToFeet)+14.9*Math.pow(inchesToFeet, 2));
        } else {
            return (1224+251*(inchesToFeet)+-5.15*Math.pow(inchesToFeet, 2));
        }
    }

    public static double rpmToSpeed(double rpm) {
        return 0.0002*rpm;
    }

    public static double distanceToMotorSpeed(double distance, boolean upperHub) {
        double rpm = distanceToRPM(distance, upperHub);
        return rpmToSpeed(rpm);
    }

    private static double getAbsoluteX() {
        return Math.abs(LimeLight.getX());
    }

    public static double getLimeLightRamping() {
        double rampedPower = 0.325*Math.pow(1.0215, getAbsoluteX()); // equation made using exponential regression for a more proper ramping
        return rampedPower < Constants.AUTO_MIN_Z ? Constants.AUTO_MIN_Z : rampedPower;
    }

    public static double getBallLimeLightRamping() {
        double rampedPower = 0.1723*Math.pow(1.0731, getAbsoluteX());
        return rampedPower < Constants.AUTO_MIN_Z ? Constants.AUTO_MIN_Z : rampedPower;
    }
    
}
