package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

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
    
    public static double getX(){
        return tx.getDouble(0.0);
    }
    
    public static double getY(){
        return ty.getDouble(0.0);
    }
    
    public static double getDistance(){
        double heightOfCamera = 43;
        double heightOfTarget = 98;
        double angleOfCamera = -20;
        double angleofTarget =  ty.getDouble(0.0);
        return (heightOfTarget - heightOfCamera) / Math.tan(Math.toRadians(angleOfCamera + angleofTarget));
    }
    
    public static double getArea(){
        return ta.getDouble(0.0);
    }
    
    public static boolean isTargetAvalible(){
        return tv.getBoolean(false);
    }
}
    
