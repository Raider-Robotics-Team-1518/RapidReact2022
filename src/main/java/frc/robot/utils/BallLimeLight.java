package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;

public class BallLimeLight {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-ball");
    //x location of the target
    private static NetworkTableEntry tx = table.getEntry("tx");
    //does the limelight have a target
    private static NetworkTableEntry tv = table.getEntry("tv");

    public static double getX(){
        return tx.getDouble(0.0);
    }

    public static boolean isTargetAvalible(){
        return tv.getDouble(0.0d) == 1.0d;
    }

    // is to the right of the deadzone
    public static boolean rightOfDeadZone() {
        return (getX() > Constants.BallCameraDeadZone);
    }

    // is to the left of the deadzone
    public static boolean leftOfDeadZone() {
        return (getX() < -Constants.BallCameraDeadZone);
    }
        
    public static boolean isOutsideDeadZone() {
        return (getX() > Constants.BallCameraDeadZone) || (getX() < -Constants.BallCameraDeadZone);
    }
}
    

/*  Limelight section below is for chasing the balls.
    Field of view on the camera is 60 degrees (+/- 30 degrees).
    Dead zone can be +/- 10 degrees
    Need to rename the tv, ta, tx, ta values above to indicate shooter camera.
    Replicate same below with names for chaser camera functions.
    Auto can then track left/right using tx from chaser camera.
    zPower set to proportion of tx/10 degrees.
*/
