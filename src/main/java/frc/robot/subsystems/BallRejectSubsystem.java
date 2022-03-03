package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallRejectSubsystem extends SubsystemBase {
    String teamColor;
    private static String currentBall = ""; 
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    public BallRejectSubsystem() {
    }
  
    @Override
    public void periodic() {
        teamColor = DriverStation.getAlliance().toString();
        Color detectedColor = m_colorSensor.getColor();
        currentBall = getBallColorName(detectedColor);

        if(!getCurrentColorBall().equalsIgnoreCase(teamColor) && !getCurrentColorBall().equalsIgnoreCase("None")) {
            BallShooterSubsystem.override = BallIndexerSubsystem.override = true;
            BallShooterSubsystem.shooterMotor.set(Constants.RejectSpeed);
            BallIndexerSubsystem.indexMotor.set(-1d);
        } else {
            if(BallShooterSubsystem.override) {
                BallShooterSubsystem.shooterMotor.set(0.0d);
                BallIndexerSubsystem.indexMotor.set(0.0d);
                BallShooterSubsystem.override = BallIndexerSubsystem.override = false;
            }
        }
    }
    public String getBallColorName(Color c) {
        double r = c.red;
        if(r > 0.4) {
          return "Red";
        } else if (r < 0.2) {
          return "Blue";
        }
        return "None";
      }
    
      public static String getCurrentColorBall() {
        return currentBall;
      }
  
    @Override
    public void simulationPeriodic() {
  
    }
}
