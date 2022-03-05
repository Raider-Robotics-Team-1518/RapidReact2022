package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {
    public static CANSparkMax climbMotor = new CANSparkMax(Constants.ClimbMotorID, MotorType.kBrushless);
    private final DigitalInput bottomLimiter = new DigitalInput(0);
    private final DigitalInput topLimiter = new DigitalInput(1);

    public ClimbSubsystem() {
        setClimbIdleMode(IdleMode.kBrake);
    }

    public void enableUp() {
        if(topLimiter.get()) {
            climbMotor.set(0);
            return;
        }
        climbMotor.set(-Constants.ClimbSpeed);
    }

    public void enableDown() {
        if(bottomLimiter.get()) {
            climbMotor.set(0);
            return;
        }
        climbMotor.set(Constants.ClimbSpeed);
    }

    public void disableClimb() {
        climbMotor.set(0);
    }

    public void setClimbIdleMode(IdleMode idleMode) {
        climbMotor.setIdleMode(idleMode);
    }
}

