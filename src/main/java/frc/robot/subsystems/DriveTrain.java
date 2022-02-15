package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Constants;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class DriveTrain extends SubsystemBase {
  private final static WPI_TalonFX rightFront = new WPI_TalonFX(Constants.RightFrontID);
  private final static WPI_TalonFX rightRear = new WPI_TalonFX(Constants.RightRearID);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightFront, rightRear);

  private final static WPI_TalonFX leftFront = new WPI_TalonFX(Constants.LeftFrontID);
  private final static WPI_TalonFX leftRear = new WPI_TalonFX(Constants.LeftRearID);
  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftFront, leftRear);
  private final DifferentialDrive m_drive;
  private final double deadband = 0.1d;
 
  public double m_leftEncoder = 0;
  public double m_rightEncoder = 0;
  public AHRS gyro;

  public DriveTrain() {
    m_drive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
    leftMotorGroup.setInverted(true);
    gyro = new AHRS(SerialPort.Port.kUSB);
    gyro.reset();

  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }


  public void driveByStick(final double liveX, final double liveZ) {
    double fixedLiveZ = Math.abs(liveZ) < deadband ? 0.0d : liveZ;
    m_drive.arcadeDrive(liveX, fixedLiveZ);
  }

  public void autonomousDrive(final double liveX, final double liveZ) {
    m_drive.arcadeDrive(liveX, liveZ);
  }

  public static WPI_TalonFX getMotor(Motor m) {
    return m.getTalonFx();
  }

  public enum Motor {
    RIGHT_FRONT(rightFront),
    RIGHT_REAR(rightRear),
    LEFT_FRONT(leftFront),
    LEFT_REAR(leftRear);

    private final WPI_TalonFX wFx;
    private Motor(WPI_TalonFX wFx) {
      this.wFx = wFx;
    }

    public WPI_TalonFX getTalonFx() {
     return this.wFx;
    }
  }

  public void resetLeftFrontEncoder(){
    leftFront.setSelectedSensorPosition(0);
  }

  public void resetLeftRearEncoder(){
    leftRear.setSelectedSensorPosition(0);
  }

  public void resetRightFrontEncoder(){
    rightFront.setSelectedSensorPosition(0);
  }

  public void resetRightRearEncoder(){
    rightRear.setSelectedSensorPosition(0);
  }

  public void resetAllEncoders() {
    resetLeftFrontEncoder();
    resetLeftRearEncoder();
    resetRightFrontEncoder();
    resetRightRearEncoder();
  }

  public double getEncoderAverage(){
    updateEncoders();
    return Math.abs(leftFront.getSelectedSensorPosition()) + Math.abs(rightFront.getSelectedSensorPosition()) / 2;
  }

  public void driveByStick(final double liveX, final double liveY, final double liveZ) {
    m_drive.arcadeDrive(liveX, liveZ);
  }

  public void updateEncoders(){
    m_leftEncoder = leftFront.getSelectedSensorPosition();
    m_rightEncoder = rightFront.getSelectedSensorPosition();

  }

}
