package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;

public class AutoSubsystem extends SubsystemBase {
  /**
   * @author MorinehtarBlue
   *
   */
  double circumferenceInInches = 18.4;
  int pulsesPerRotation = 21934; //2048;
  double distanceToTravel = 0;
  double startPosition = 0;
  double currentAngle = 0;
  double currentPosition = 0;
  double targetPulseCount = 0;
  double targetPosition = 0;
  double drivePower = 0;

  private DriveTrain a_drive = RobotContainer.m_driveTrain;

  public AutoSubsystem() {
	RobotContainer.m_driveTrain.setNeutralMode(NeutralMode.Coast);
    resetMotorPositions();
  }

  @Override
  public void periodic() {
	RobotContainer.m_driveTrain.updateEncoders();
  }

  @Override
  public void simulationPeriodic() {

  }

  public double getZ(double angleToRotateTo) {
    if(Math.abs(RobotContainer.m_driveTrain.gyro.getAngle() - angleToRotateTo) > 5.0d) {
      if(RobotContainer.m_driveTrain.gyro.getAngle() - angleToRotateTo < 0) {
        return 0.2d;
      } else {
        return 0.2d;
      }
    }
    return 0.0d;
  }

  public void resetMotorPositions() {
    RobotContainer.m_driveTrain.resetAllEncoders();
  }

  public double getStartPos() {
    return RobotContainer.m_driveTrain.getEncoderAverage();
  }

  public boolean hasDrivenFarEnough(double startPos, double distance) {
		//currentPosition = ((Robot.rm.lift.getSensorCollection().getQuadraturePosition() + Robot.rm.climb.getSensorCollection().getQuadraturePosition()) / 2) ;
		currentPosition = (RobotContainer.m_driveTrain.getEncoderAverage());
		targetPulseCount = distance / circumferenceInInches * pulsesPerRotation;
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
				//System.out.println("CurrentPosition: " + currentPosition);
				//System.out.println("TargetPosition: " + targetPosition);
				if (currentPosition >= targetPosition) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		else {
			return true;
		}
	}

	public void disableAllMotors() {
        BallShooterSubsystem.shooterMotor.set(0.0d);
		BallShooterSubsystem.shooterMotor2.set(0.0d);
        BallIndexerSubsystem.indexMotor.set(0.0d);
        IntakeSubsystem.intakeMotor.set(0.0d);
	}

	public void waitForBall() {
		while(RobotContainer.m_ballRejecter.getBallColorName(RobotContainer.m_ballRejecter.m_colorSensor.getColor()).equalsIgnoreCase("None") && RobotState.isAutonomous()) {
			IntakeSubsystem.intakeMotor.set(Constants.IntakeSpeed);
			BallIndexerSubsystem.indexMotor.set(Constants.IndexSpeed);
			Timer.delay(0.1d);
		}
	}

	public void disableIntakeSystem() {
		IntakeSubsystem.intakeMotor.set(0);
		BallIndexerSubsystem.indexMotor.set(0);
	}
	
	public void enableIntake() {
		IntakeSubsystem.intakeMotor.set(Constants.IntakeSpeed);
	}

	public void deployIntakeArms() {
		RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid1.set(false);
		RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid2.set(true);
		// delay to prevent intake running while arms are not fully out
		Timer.delay(1.25d);
	}

	public void retractIntakeArms() {
		RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid1.set(true);
		RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid2.set(false);
	}

	public void shootBallLow() {
		RobotContainer.m_ballShooter.shooterManualMode();
		Timer.delay(1.25);
		RobotContainer.m_ballIndexer.enableManIndexer();
		Timer.delay(0.75);
		BallIndexerSubsystem.indexMotor.set(0);
		BallShooterSubsystem.shooterMotor.set(0);
		BallShooterSubsystem.shooterMotor2.set(0);
	}

	public void shootBallHigh() {
		RobotContainer.m_ballShooter.enableShooterMotor();
		Timer.delay(1.25);
		RobotContainer.m_ballIndexer.enableManIndexer();
		Timer.delay(0.75);
		BallIndexerSubsystem.indexMotor.set(0);
		BallShooterSubsystem.shooterMotor.set(0);
		BallShooterSubsystem.shooterMotor2.set(0);
	}

    protected boolean gyroTurn(double degrees) {
		a_drive.gyro.reset();
		while ((RobotState.isAutonomous() == true) && outsideDeadzone(degrees)) {
			RobotContainer.m_driveTrain.autonomousDrive(0, calcP(degrees));
		}
		stop();
		return true;
	}

	private boolean outsideDeadzone(double deg) {
		return (readGyro() < (deg-Constants.GYRO_DEADZONE)) || (readGyro() > (deg+Constants.GYRO_DEADZONE));
	}
    
	protected boolean gyroDrive(double distance) {
		RobotContainer.m_driveTrain.gyro.reset();
		//RobotContainer.m_driveTrain.resetAllEncoders();
		startPosition = RobotContainer.m_driveTrain.getEncoderAverage();
		//double targetPosition = (distance / circumferenceInInches * pulsesPerRotation);
		//System.out.println(hasDrivenFarEnough(startPosition, distance));
		while (hasDrivenFarEnough(startPosition, distance) == false) {
			//RobotContainer.m_driveTrain.updateEncoders();
			double drift = readGyro() / 100;
			drift = Math.min(drift, 0.1);
			if (distance > 0) {
				// a_drive.drive();
				RobotContainer.m_driveTrain.autonomousDrive(-Constants.AUTO_MAX_X, drift);  // FORWARD
			} else {
				// a_drive.drive();
				RobotContainer.m_driveTrain.autonomousDrive(Constants.AUTO_MAX_X, drift);  // REVERSE
			}
			
			//System.out.println("Gyro Heading: " + drift);
		}
		
		stop();
		return true;
	}

	//Drive Directions
	public boolean driveforward(double distance) {
		gyroDrive(distance);
		return true;
	}
	
	public boolean drivebackward(double distance) {
		gyroDrive(-Math.abs(distance));
		return true;
	}
	
	public void turnleft(double degrees) {
		gyroTurn(-degrees);
	}
	
	public void turnright(double degrees) {
		gyroTurn(degrees);
	}
	
	//--------------------------------------

	protected double readGyro() {
		double angle = RobotContainer.m_driveTrain.gyro.getAngle();
		return angle;
	}
	
	protected double calcP(double tAngle) {
		return tAngle > readGyro() ? Constants.AUTO_MAX_Z : -Constants.AUTO_MAX_Z;
		
	}
	
	public void stop() {

		RobotContainer.m_driveTrain.autonomousDrive(0, 0);
    	//taskDone = true;
    	
    }
}
