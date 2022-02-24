package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallIndexerSubsystem extends SubsystemBase {
    public static CANSparkMax indexMotor = new CANSparkMax(Constants.BallIndexerID, MotorType.kBrushless);

    public static boolean override = false;

    public BallIndexerSubsystem() {
        setIndexIdleMode(IdleMode.kBrake);
    }

    public void enableIndexer() {
        if(override) {
            return;
        }
        indexMotor.set(Constants.IndexSpeed);
    }

    public void disableIndexer() {
        if(override) {
            return;
        }
        indexMotor.set(0);
    }

    public void setIndexIdleMode(IdleMode idleMode) {
        indexMotor.setIdleMode(idleMode);
    }
}
