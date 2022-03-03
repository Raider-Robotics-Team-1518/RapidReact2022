package frc.robot.utils;

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
    
}
