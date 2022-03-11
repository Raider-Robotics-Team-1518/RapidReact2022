package frc.robot;

public class Constants {
    public static final int BallIndexerID = 12;
    public static final int BallIntakerID = 13;
    public static final int ClimbMotorID = 14;
    public static final int ShooterMotorID = 15;
    
    public static final int RightFrontID = 2;
    public static final int RightRearID = 1;

    public static final int LeftFrontID = 3;
    public static final int LeftRearID = 4;

    public static final double IntakeSpeed = 0.85d;
    public static final double IndexSpeed = -0.5d;
    public static final double ClimbSpeed = 1.0d;
    public static final double RejectSpeed = 0.35d;

    public static final double CameraDeadZone = 1.0d;
    public static final double CameraDerivative = 20; // 25 is good for non-carpet, need less for carpet

    public static final double AUTO_MAX_X = 0.55;  // Maximum power for forward/back
    public static final double AUTO_MAX_Z = 0.60;  // Maximum power to rotate

    public static final double AUTO_MIN_Z = 0.50;  // Minimum power to rotate

    public static final double GYRO_DEADZONE = 5;
}
