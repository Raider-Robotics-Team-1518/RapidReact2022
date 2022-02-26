package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.utils.LimeLight;
import frc.robot.utils.MathHelper;
import edu.wpi.first.wpilibj.RobotState;

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
  double AUTO_MAX_Y = 0.35;  // Maximum power for strafe
  double AUTO_MAX_X = 0.35;  // Maximum power for forward/back
  double AUTO_MAX_Z = 0.40;  // Maximum power to rotate

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

  protected boolean hasDrivenFarEnough(double startPos, double distance) {
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

   
    public void shootBall(String className) {
        double motorSpeed = MathHelper.distanceToMotorSpeed(LimeLight.getDistance(), true);
        double neededRPM = MathHelper.distanceToRPM(LimeLight.getDistance(), true)-250;
        printShooterInfo(className, LimeLight.getDistance(), motorSpeed, neededRPM);
        while (BallShooterSubsystem.shooterRPM < neededRPM) {
            BallShooterSubsystem.shooterMotor.set(motorSpeed);
        }
        System.out.println(className + " ---> Shooting ball!");
        BallIndexerSubsystem.indexMotor.set(Constants.IndexSpeed);
        System.out.println(className + " ---> Ball shot");
    }

    private void printShooterInfo(String className, double dist, double motorSpeed, double neededRPM) {
        System.out.println(className + " ---> ----------------------------");
        System.out.println(className + " ---> Shooter Info: ");
        System.out.println(className + " ---> Distance: " + dist);
        System.out.println(className + " ---> Speed: " + motorSpeed);
        System.out.println(className + " ---> Needed RPM: " + neededRPM);
        System.out.println(className + " ---> ----------------------------");
    }

    protected boolean gyroTurn(double degrees) {
		a_drive.gyro.reset();
		while ((RobotState.isAutonomous() == true) && (Math.abs(readGyro()) < Math.abs(degrees))) {
			RobotContainer.m_driveTrain.autonomousDrive(0, calcP(degrees));
		}
		stop();
		return true;
	}
    
	protected boolean gyroDrive(double distance) {
		RobotContainer.m_driveTrain.gyro.reset();
		RobotContainer.m_driveTrain.resetAllEncoders();
		startPosition = RobotContainer.m_driveTrain.getEncoderAverage();
		//double targetPosition = (distance / circumferenceInInches * pulsesPerRotation);
		//System.out.println(hasDrivenFarEnough(startPosition, distance));
		while (hasDrivenFarEnough(startPosition, distance) == false) {
			RobotContainer.m_driveTrain.updateEncoders();
			double drift = readGyro() / 100;
			drift = Math.min(drift, 0.1);
			if (distance > 0) {
				// a_drive.drive();
				RobotContainer.m_driveTrain.autonomousDrive(-AUTO_MAX_X, drift);  // FORWARD
			} else {
				// a_drive.drive();
				RobotContainer.m_driveTrain.autonomousDrive(AUTO_MAX_X, drift);  // REVERSE
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
		double angle = RobotContainer.m_driveTrain.gyro.getAngle();
		return angle;
	}
	
	protected double calcP(double tAngle) {
		double p = (AUTO_MAX_Z * 1-((Math.abs(tAngle) - Math.abs(RobotContainer.m_driveTrain.gyro.getAngle())) / Math.abs(tAngle)));	
		if (tAngle < 0) {
			return p;
		}
		
		else {
			return (p * -1);
		}
		
	}
	
	public void stop() {

		RobotContainer.m_driveTrain.autonomousDrive(0, 0);
    	//taskDone = true;
    	
    }
}
