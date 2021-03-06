package frc.robot;

public class Constants {
    public static final int BallIndexerID = 12;
    public static final int BallIntakerID = 13;
    public static final int ClimbMotorID = 14;
    public static final int ShooterMotorID = 15;
    public static final int ShooterMotorID2 = 16;
    
    public static final int RightFrontID = 2;
    public static final int RightRearID = 1;

    public static final int LeftFrontID = 3;
    public static final int LeftRearID = 4;

    public static final double IntakeSpeed = 0.85d;
    public static final double IndexSpeed = -0.65d;
    public static final double AutoIndexSpeed = -0.30d;
    public static final double ClimbSpeed = 1.0d;
    public static final double RejectSpeed = 0.45d;

    public static final double CameraDeadZone = 1.0d;
    public static final double BallCameraDeadZone = 2.5d;

    public static final double AUTO_MAX_X = 0.55;  // Maximum power for forward/back
    public static final double AUTO_MAX_Z = 0.60;  // Maximum power to rotate

    public static final double AUTO_MIN_X = 0.3;  // Minimum power to rotate
    public static final double AUTO_MIN_Z = 0.35;  // Minimum power to rotate

    public static final double GYRO_DEADZONE = 10;
}
