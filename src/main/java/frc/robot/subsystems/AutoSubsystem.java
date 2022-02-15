package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain.Motor;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SerialPort;

public class AutoSubsystem extends SubsystemBase {
  /**
   * @author MorinehtarBlue
   *
   */
  double circumferenceInInches = 25.0;
  int pulsesPerRotation = 21934;
  double distanceToTravel = 0;
  double startPosition = 0;
  double currentAngle = 0;
  double currentPosition = 0;
  double targetPulseCount = 0;
  double targetPosition = 0;
  double drivePower = 0;
  double AUTO_MAX_Y = 0.15;  // Maximum power for strafe
  double AUTO_MAX_X = 0.15;  // Maximum power for forward/back
  double AUTO_MAX_Z = 0.25;  // Maximum power to rotate

  private DriveTrain a_drive = RobotContainer.m_driveTrain;

  public AutoSubsystem() {
    setNeutralMode(NeutralMode.Coast);
    resetMotorPositions();
  }

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {

  }

  public double getZ(double angleToRotateTo) {
    if(Math.abs(a_drive.gyro.getAngle() - angleToRotateTo) > 5.0d) {
      if(a_drive.gyro.getAngle() - angleToRotateTo < 0) {
        return 0.2d;
      } else {
        return 0.2d;
      }
    }
    return 0.0d;
  }

  public void resetMotorPositions() {
    a_drive.getMotor(Motor.RIGHT_FRONT).setSelectedSensorPosition(0.0d);
    a_drive.getMotor(Motor.LEFT_FRONT).setSelectedSensorPosition(0.0d);
  }

  public void setNeutralMode(NeutralMode mode) {
    a_drive.getMotor(Motor.RIGHT_FRONT).setNeutralMode(mode);
    a_drive.getMotor(Motor.RIGHT_REAR).setNeutralMode(mode);
    a_drive.getMotor(Motor.LEFT_FRONT).setNeutralMode(mode);
    a_drive.getMotor(Motor.LEFT_REAR).setNeutralMode(mode);
  }

  public double getStartPos() {
    return a_drive.getMotor(Motor.RIGHT_FRONT).getSelectedSensorPosition();
  }

  protected boolean hasDrivenFarEnough(double startPos, double distance) {
		//currentPosition = ((Robot.rm.lift.getSensorCollection().getQuadraturePosition() + Robot.rm.climb.getSensorCollection().getQuadraturePosition()) / 2) ;
		currentPosition = (a_drive.getEncoderAverage());
		targetPulseCount = (distance / circumferenceInInches) * pulsesPerRotation ;
		targetPosition = startPos + targetPulseCount;
		if (RobotState.isAutonomous() == true) {
			if (distance > 0) { // Driving FORWARD
				if (currentPosition >= targetPosition) {
					return true;
				}
				else {
					return false;
				}
			}
			else { // Driving REVERSE
				if (currentPosition <= targetPosition) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else {
			return true;
		}
	}

   

    protected boolean gyroTurn(double targetAngle) {
		a_drive.gyro.reset();
		while ((RobotState.isAutonomous() == true) && (Math.abs(readGyro()) < Math.abs(targetAngle)) && (Math.abs(calcP(targetAngle)) > 0.25)) {
			// a_drive.drive(0, 0, calcP(targetAngle));//(0, calcP(targetAngle));
		}
		stop();	
		return true;
	}
    
	protected boolean gyroDrive(double distance) {
		a_drive.gyro.reset();
		a_drive.resetAllEncoders();
		startPosition = a_drive.getEncoderAverage();
		//double targetPosition = (distance / circumferenceInInches * pulsesPerRotation);
		System.out.println(hasDrivenFarEnough(startPosition, distance));
		while (hasDrivenFarEnough(startPosition, distance) == false) {
			double drift = readGyro() / 100;
			drift = Math.min(drift, 0.1);
			if (distance > 0) {
				// a_drive.drive();
				a_drive.driveByStick(0, AUTO_MAX_X, -drift);  // FORWARD
			} else {
				// a_drive.drive();
				a_drive.driveByStick(0, -AUTO_MAX_X, -drift);  // REVERSE
			}
			
			//System.out.println("Gyro Heading: " + drift);
		}
		
		stop();
		return true;
	}

	//Drive Directions
	public void driveforward(double distance) {
		gyroDrive(distance);
	}
	
	public void drivebackward(double distance) {
		gyroDrive(-Math.abs(distance));
	}
	
	public void turnleft(double degrees) {
		gyroTurn(-degrees);
	}
	
	public void turnright(double degrees) {
		gyroTurn(degrees);
	}
	
	//--------------------------------------

	protected double readGyro() {
		double angle = a_drive.gyro.getAngle();
		return angle;
	}
	
	protected double calcP(double tAngle) {
		double p = AUTO_MAX_Z * ((1-(Math.abs(readGyro()) / Math.abs(tAngle))) - 0.05);	
		if (tAngle > 0) {
			return p;
		}
		
		else {
			return (p * -1);
		}
		
	}
	
	public void stop() {

		// a_drive.drive(0, 0, 0);
    	//taskDone = true;
    	
    }
}
