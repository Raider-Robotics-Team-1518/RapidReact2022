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
        if(override/* || !BallShooterSubsystem.upToRPM()*/) {
            return;
        }
        indexMotor.set(Constants.IndexSpeed);
    }

    public void enableManIndexer() {
        indexMotor.set(Constants.IndexSpeed);
    }

    public void backfeedIndexer() {
        indexMotor.set(-Constants.IndexSpeed);
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
