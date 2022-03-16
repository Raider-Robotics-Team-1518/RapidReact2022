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
  public static CANSparkMax backSpinMotor = new CANSparkMax(Constants.BackSpinMotorID, MotorType.kBrushless);
  public static RelativeEncoder shooterMotorEncoder = shooterMotor.getEncoder();

  public static boolean override = false;

  public static double shooterRPM = 0.0d;
  public static double desiredRPM = 0.0d;
  private double autoThrottle = 0.0d;

  public static boolean pivoting = false;
  
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

  public boolean isPivoting() {
    return (!override && LimeLight.isTargetAvalible() && shooterMotor.get() > 0.1d && LimeLight.isOutsideDeadZone());
  }

  public void enableShooterMotor() {
    if(override) {
      return;
    }
    double shooterPower = (-0.5*RobotContainer.joystick.getThrottle())+0.5;
    shooterMotor.set(shooterPower);
    backSpinMotor.set(shooterPower/2);
    // auto center
    if(LimeLight.isTargetAvalible() && LimeLight.isOutsideDeadZone()) {
      if(LimeLight.rightOfDeadZone()) {
        RobotContainer.m_driveTrain.driveByStick(0, MathHelper.getLimeLightRamping());
      } else if(LimeLight.leftOfDeadZone()) {
        RobotContainer.m_driveTrain.driveByStick(0, -MathHelper.getLimeLightRamping());
      }
    }

    /*

    double motorSpeed = MathHelper.distanceToMotorSpeed(LimeLight.getDistance(), true);
    desiredRPM = MathHelper.distanceToRPM(LimeLight.getDistance(), true);
    if(motorSpeed < 1.1d && LimeLight.getDistance() < 245) {
      autoThrottle = motorSpeed;
    }
    shooterMotor.set(autoThrottle+0.50d);*/
  }

  public void shooterManualMode() {
    shooterMotor.set(0.50d); // 0.55d 
    backSpinMotor.set(0.25d);
  }

  public void disableShooterMotor() {
    if(override) {
      return;
    }
    shooterMotor.set(0.0d);
    backSpinMotor.set(0.0d);
  }

  public void doShooterDisplay() {
    SmartDashboard.putNumber("AutoThrottle", autoThrottle);
    SmartDashboard.putNumber("ShooterRPM", shooterRPM);
    SmartDashboard.putNumber("TargetDistance", LimeLight.getDistance());
    SmartDashboard.putBoolean("Target Locked", LimeLight.isTargetAvalible());
  }

  public static boolean upToRPM() {
    return shooterRPM > desiredRPM-100;
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
    if(posDifference < 200) return;
    shooterRPM = posDifference;
    SmartDashboard.putNumber("Shooter Velocity", shooterMotor.getEncoder().getVelocity());
  }

  public void setShooterIdleMode(IdleMode idleMode) {
    shooterMotor.setIdleMode(idleMode);
  }

}
