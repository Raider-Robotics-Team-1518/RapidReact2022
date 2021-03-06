package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class DriveTrain extends SubsystemBase {
  private final static WPI_TalonFX rightFront = new WPI_TalonFX(Constants.RightFrontID);
  private final static WPI_TalonFX rightRear = new WPI_TalonFX(Constants.RightRearID);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightFront, rightRear);

  private final static WPI_TalonFX leftFront = new WPI_TalonFX(Constants.LeftFrontID);
  private final static WPI_TalonFX leftRear = new WPI_TalonFX(Constants.LeftRearID);
  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftFront, leftRear);
  private final DifferentialDrive m_drive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);

  private boolean reversed = false;
 
  public double m_leftEncoder = 0;
  public double m_rightEncoder = 0;
  public AHRS gyro;

  public DriveTrain() {
    leftMotorGroup.setInverted(true);
    gyro = new AHRS(SerialPort.Port.kUSB);
    gyro.reset();
    resetAllEncoders();

  }

  @Override
  public void periodic() {
    if(RobotState.isTeleop()) {
     driveByStick(RobotContainer.joystick);
    }
    updateEncoders();
  }

  @Override
  public void simulationPeriodic() {
  }

  public void driveByStick(Joystick stick) {
    double x = stick.getY()*SmartDashboard.getNumber("X Factor", 0.9d);
    double z = -stick.getZ()*SmartDashboard.getNumber("Z Factor", 0.65d);
    m_drive.arcadeDrive(reversed ? -x : x, z);
  }

  public void driveByStick(double x, double z) {
    m_drive.arcadeDrive(x, z);
  }

  public void autonomousDrive(final double liveX, final double liveZ) {
    m_drive.arcadeDrive(liveX, liveZ);
  }

  public void switchDriveDirection() {
    reversed = !reversed;
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
    //return ((Math.abs(leftFront.getSelectedSensorPosition()) + Math.abs(rightFront.getSelectedSensorPosition())) / 2);
    return (leftFront.getSelectedSensorPosition() - rightFront.getSelectedSensorPosition()) / 2;
  }

  public void setNeutralMode(NeutralMode mode){
    leftFront.setNeutralMode(mode);
    leftRear.setNeutralMode(mode);
    rightFront.setNeutralMode(mode);
    rightRear.setNeutralMode(mode);
  }

  public void updateEncoders(){
    //m_leftEncoder = leftFront.getSelectedSensorPosition();
    //m_rightEncoder = rightFront.getSelectedSensorPosition();
    SmartDashboard.putNumber("ENC_Average", getEncoderAverage());
    SmartDashboard.putNumber("ENC_RightFront", rightFront.getSelectedSensorPosition());
    SmartDashboard.putNumber("ENC_LeftFront", leftFront.getSelectedSensorPosition());
    //SmartDashboard.putNumber("ENC_RightRear", rightRear.getSelectedSensorPosition());
    //SmartDashboard.putNumber("ENC_LeftRear", leftRear.getSelectedSensorPosition());

  }

}
