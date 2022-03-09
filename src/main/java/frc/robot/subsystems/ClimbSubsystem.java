package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {
    public static CANSparkMax climbMotor = new CANSparkMax(Constants.ClimbMotorID, MotorType.kBrushless);

    public ClimbSubsystem() {
        setClimbIdleMode(IdleMode.kBrake);
    }

    public static void enableClimb(boolean up) {
        climbMotor.set(up ? Constants.ClimbSpeed : -Constants.ClimbSpeed);
        climbMotor.setIdleMode(IdleMode.kBrake);
    }

    public static void disableClimb() {
        climbMotor.set(0);
    }

    public void setClimbIdleMode(IdleMode idleMode) {
        climbMotor.setIdleMode(idleMode);
    }
}

