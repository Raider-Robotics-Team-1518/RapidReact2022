package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.LimeLight;
import frc.robot.utils.MathHelper;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallShooterSubsystem extends SubsystemBase {
  public static CANSparkMax shooterMotor = new CANSparkMax(Constants.ShooterMotorID, MotorType.kBrushless);
  public static CANSparkMax shooterMotor2 = new CANSparkMax(Constants.ShooterMotorID2, MotorType.kBrushless);
  public static MotorControllerGroup shooterGroup = new MotorControllerGroup(shooterMotor, shooterMotor2);
  public static RelativeEncoder shooterMotorEncoder = shooterMotor.getEncoder();

  public static boolean override = false;

  public static double shooterRPM = 0.0d;
  public static double desiredRPM = 0.0d;

  public static boolean pivoting = false;
  
  private double lastPos1, lastPos2, posDifference;

  public static BallShooterSubsystem INST;
  public BallShooterSubsystem() {
    shooterMotorEncoder.setPosition(0);
    setShooterIdleMode(IdleMode.kCoast);
    shooterMotor2.setInverted(true);
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
    double shooterPower = 0.77d; //(-0.5*RobotContainer.joystick.getThrottle())+0.5;
    shooterGroup.set(shooterPower);
    desiredRPM = MathHelper.speedToRPM(shooterPower);
  }

  public void enableShooterMotorAuto() {
    if(override) {
      return;
    }
    double shooterPower = 0.80d;
    shooterGroup.set(shooterPower);
  }

  public void shooterManualMode() {
    shooterGroup.set(0.55d);
  }

  public void shooterManualModeAuto() {
    shooterGroup.set(0.45d);
  }

  public void disableShooterMotor() {
    if(override) {
      return;
    }
    shooterGroup.set(0.0d);
  }

  public void doShooterDisplay() {
    SmartDashboard.putNumber("ShooterRPM", shooterRPM);
  }

  public static boolean upToRPM() {
    return shooterRPM > desiredRPM-200;
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
    shooterMotor2.setIdleMode(idleMode);
  }

}
