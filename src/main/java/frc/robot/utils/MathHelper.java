package frc.robot.utils;

public class MathHelper {

    public static double distanceToRPM(double distance, boolean upperHub) {
        return 0.0d;
    }

    public static double rpmToSpeed(double rpm) {
        return 0.0002*rpm;
    }

    public static double distanceToMotorSpeed(double distance, boolean upperHub) {
        double rpm = distanceToRPM(distance, upperHub);
        return rpmToSpeed(rpm);
    }
    
}
