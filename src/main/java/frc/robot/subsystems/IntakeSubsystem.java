package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.utils.BallLimeLight;
import frc.robot.utils.MathHelper;

public class IntakeSubsystem extends SubsystemBase {
    public static CANSparkMax intakeMotor = new CANSparkMax(Constants.BallIntakerID, MotorType.kBrushless);

    public boolean autoCenter = true;

    public IntakeSubsystem() {
        setIntakeIdleMode(IdleMode.kBrake);
    }

    public void enableIntaker() {
        if(autoCenter && BallLimeLight.isTargetAvalible() && BallLimeLight.isOutsideDeadZone()) {
            if(BallLimeLight.rightOfDeadZone()) {
              RobotContainer.m_driveTrain.driveByStick(0, MathHelper.getBallLimeLightRamping());
            } else if(BallLimeLight.leftOfDeadZone()) {
              RobotContainer.m_driveTrain.driveByStick(0, -MathHelper.getBallLimeLightRamping());
            }
        }
        if(!BallRejectSubsystem.getCurrentColorBall().equalsIgnoreCase("None")) {
            intakeMotor.set(Constants.IntakeSpeed);
            BallIndexerSubsystem.indexMotor.set(0);
            return;
        } else {
            intakeMotor.set(Constants.IntakeSpeed);
            BallIndexerSubsystem.indexMotor.set(Constants.IndexSpeed);
        }
        //intakeMotor.set(Constants.IntakeSpeed);
    }

    public void disableIntaker() {
        intakeMotor.set(0);
        BallIndexerSubsystem.indexMotor.set(0);
    }

    public void setIntakeIdleMode(IdleMode idleMode) {
        intakeMotor.setIdleMode(idleMode);
    }

    public void toggleAutoCenter() {
        this.autoCenter = !this.autoCenter;
    }
}

