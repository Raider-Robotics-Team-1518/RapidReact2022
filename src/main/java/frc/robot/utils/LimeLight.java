package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

public class LimeLight {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    //x location of the target
    private static NetworkTableEntry tx = table.getEntry("tx");
    //y location of the target
    private static NetworkTableEntry ty = table.getEntry("ty");
    //area of the target
    private static NetworkTableEntry ta = table.getEntry("ta");
    //does the limelight have a target
    private static NetworkTableEntry tv = table.getEntry("tv");

    private static double limelightMountAngleDegrees = 13.0;

    // distance from the center of the Limelight lens to the floor
    private static double limelightHeightInches = 12.5;

    public static double getX(){
        return tx.getDouble(0.0);
    }
    
    public static double getY(){
        return ty.getDouble(0.0);
    }

    
    public static double getDistance(){
        double heightOfGoal = 104;

        double angleToGoalDegrees = limelightMountAngleDegrees + getY();
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
        double distanceFromLimelightToGoalInches = (heightOfGoal - limelightHeightInches)/Math.tan(angleToGoalRadians);
        return distanceFromLimelightToGoalInches;
    }
    
    public static double getArea(){
        return ta.getDouble(0.0);
    }
    
    public static boolean isTargetAvalible(){
        return tv.getDouble(0.0d) == 1.0d;
    }

    // is to the right of the deadzone
    public static boolean rightOfDeadZone() {
        return (LimeLight.getX() > Constants.CameraDeadZone);
    }

    // is to the left of the deadzone
    public static boolean leftOfDeadZone() {
        return (LimeLight.getX() < -Constants.CameraDeadZone);
    }
        
    public static boolean isOutsideDeadZone() {
        return (LimeLight.getX() > Constants.CameraDeadZone) || (LimeLight.getX() < -Constants.CameraDeadZone);
    }
}