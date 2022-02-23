package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.utils.Constants;
import frc.robot.utils.LimeLight;

import java.lang.module.ModuleDescriptor.Requires;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallShooterSubsystem extends SubsystemBase {
  public static CANSparkMax shooterMotor = new CANSparkMax(Constants.ShooterMotorID, MotorType.kBrushless);
  public static RelativeEncoder shooterMotorEncoder = shooterMotor.getEncoder();

  public static boolean override = false;

  // distance from hub = hubHeight/tan(limelightAngle)

  public static BallShooterSubsystem INST;
  public BallShooterSubsystem() {
    shooterMotorEncoder.setPosition(0);
    setShooterIdleMode(IdleMode.kCoast);
    INST = this;
  }

  public void enableShooterMotor() {
    if(override) {
      return;
    }
    /*
    convert shooter rpm to speed:
    Speed=(0.000198*rpm)+0.00137

    */
    //double shooterRPM = 804+528*dist+-35.6*dist*dist;
    //double joyStickThrottle = joystick.getThrottle();
    double shooterThrottle = (-0.5*(RobotContainer.joystick.getThrottle()))+0.5;
    shooterMotor.set(shooterThrottle);
    SmartDashboard.putNumber("ShooterThrottle", shooterThrottle);
    //SmartDashboard.putNumber("ShooterRPM", shooterMotorEncoder.getVelocity());
    
  }

  public void disableShooterMotor() {
    if(override) {
      return;
    }
    shooterMotor.set(0);
  }

  public void setShooterIdleMode(IdleMode idleMode) {
    shooterMotor.setIdleMode(idleMode);
  }

}
