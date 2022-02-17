package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Constants;

public class IntakeSubsystem extends SubsystemBase {
    public static CANSparkMax intakeMotor = new CANSparkMax(Constants.BallIntakerID, MotorType.kBrushless);

    public IntakeSubsystem() {
        setIntakeIdleMode(IdleMode.kBrake);
    }

    public void enableIntaker() {
        intakeMotor.set(0.65d);
    }

    public void disableIntaker() {
        intakeMotor.set(0d);
    }

    public void setIntakeIdleMode(IdleMode idleMode) {
        intakeMotor.setIdleMode(idleMode);
    }
}

