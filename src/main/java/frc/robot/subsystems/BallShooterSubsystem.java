package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
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
    SmartDashboard.putNumber("ShooterRPM", shooterRPM);
  }

  public void enableShooterMotor() {
    if(override) {
      return;
    }
    double shooterThrottle = (-0.5*(RobotContainer.joystick.getThrottle()))+0.5;
    double rpmToSpeed = MathHelper.rpmToSpeed(SmartDashboard.getNumber("DB/Slider 0", shooterThrottle));
    shooterMotor.set(rpmToSpeed);
    SmartDashboard.putNumber("ShooterThrottle", shooterThrottle);
  }

  public void disableShooterMotor() {
    if(override) {
      return;
    }
    shooterMotor.set(0);
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
