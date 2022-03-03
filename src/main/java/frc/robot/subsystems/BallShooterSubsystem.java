package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.utils.LimeLight;
import frc.robot.utils.MathHelper;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallShooterSubsystem extends SubsystemBase {
  public static CANSparkMax shooterMotor = new CANSparkMax(Constants.ShooterMotorID, MotorType.kBrushless);
  public static RelativeEncoder shooterMotorEncoder = shooterMotor.getEncoder();

  public static boolean override = false;

  public static double shooterRPM = 0.0d;
  private double lastPos1, lastPos2, posDifference;

  public static BallShooterSubsystem INST;
  public BallShooterSubsystem() {
    shooterMotorEncoder.setPosition(0);
    setShooterIdleMode(IdleMode.kCoast);
    posDifference = 0;
    lastPos1 = lastPos2 = -1;
    INST = this;
  }

  @Override
  public void periodic() {
    doShooterRPM();
    doShooterDisplay();
  }

  public void enableShooterMotor() {
    if(override) {
      return;
    }

    // auto correct to center to target
    if(LimeLight.isTargetAvalible()) {
      if(LimeLight.getX() > Constants.CameraDeadZone) {
        RobotContainer.m_driveTrain.driveByStick(0, -((LimeLight.getX()/12)*Constants.AUTO_MAX_Z)+Constants.AUTO_MIN_Z);
      } else if (LimeLight.getX() < -Constants.CameraDeadZone) {
        RobotContainer.m_driveTrain.driveByStick(0, -((LimeLight.getX()/12)*Constants.AUTO_MAX_Z)+Constants.AUTO_MIN_Z);
      }
    }

    //double shooterThrottle = (-0.5*(RobotContainer.joystick.getThrottle()))+0.5;
    double motorSpeed = MathHelper.distanceToMotorSpeed(LimeLight.getDistance(), true);
    shooterMotor.set(motorSpeed);
  }

  public void disableShooterMotor() {
    if(override) {
      return;
    }
    shooterMotor.set(0);
  }

  public void doShooterDisplay() {
    double motorSpeed = MathHelper.distanceToMotorSpeed(LimeLight.getDistance(), true);
    SmartDashboard.putNumber("AutoThrottle", motorSpeed);
    SmartDashboard.putNumber("ShooterRPM", shooterRPM);
    SmartDashboard.putNumber("TargetDistance", LimeLight.getDistance());
    SmartDashboard.putBoolean("Target Locked", LimeLight.isTargetAvalible());
  }


  public void doShooterRPM() {
    // counts per revolution = 42
    // 60 secs in a min
    // 42*60=2520
    if(lastPos1 == -1 && lastPos2 == -1) {
      lastPos1 = shooterMotorEncoder.getPosition();
    } else if(lastPos1 != -1 && lastPos2 == -1) {
      lastPos2 = shooterMotorEncoder.getPosition();
    } else if(lastPos1 != -1 && lastPos2 != -1) {
      posDifference = Math.abs(lastPos2-lastPos1)*2520;
      lastPos1 = lastPos2 = -1;
    }
    shooterRPM = posDifference;
  }

  public void setShooterIdleMode(IdleMode idleMode) {
    shooterMotor.setIdleMode(idleMode);
  }

}
