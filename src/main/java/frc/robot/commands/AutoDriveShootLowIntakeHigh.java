package frc.robot.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallRejectSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoDriveShootLowIntakeHigh extends CommandBase{
    private static final double distanceToDrive = 42; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootLowIntakeHigh() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
        //DeployIntake.fire();
    }
    @Override
    public void execute() {
        System.out.println("AutoDriveShoot ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShoot ---> Driving...");
            RobotContainer.m_ballShooter.shooterManualMode();
            Timer.delay(1.25);
            RobotContainer.m_ballIndexer.enableManIndexer();
            Timer.delay(0.75);
            RobotContainer.m_ballIndexer.indexMotor.set(0);
            RobotContainer.m_ballShooter.shooterMotor.set(0);

            RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid1.set(false);
            RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid2.set(true);

            
            Timer.delay(1.0d);
            IntakeSubsystem.intakeMotor.set(Constants.IntakeSpeed);
            auto.driveforward(distanceToDrive);
            auto.stop();
            while(RobotContainer.m_ballRejecter.getBallColorName(RobotContainer.m_ballRejecter.m_colorSensor.getColor()).equalsIgnoreCase("None") && RobotState.isAutonomous()) {
                IntakeSubsystem.intakeMotor.set(Constants.IntakeSpeed);
                RobotContainer.m_ballIndexer.indexMotor.set(Constants.IndexSpeed);
                Timer.delay(0.1d);
            }
            IntakeSubsystem.intakeMotor.set(0);
            BallIndexerSubsystem.indexMotor.set(0);
            auto.drivebackward(distanceToDrive);
            auto.stop();
            RobotContainer.m_ballShooter.enableShooterMotor();
            while (!BallShooterSubsystem.upToRPM()) {
                Timer.delay(0.1);
            }
            RobotContainer.m_ballIndexer.enableManIndexer();
            Timer.delay(0.75);
            RobotContainer.m_ballIndexer.disableIndexer();
            RobotContainer.m_ballShooter.disableShooterMotor();
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        BallShooterSubsystem.shooterMotor.set(0.0d);
        BallIndexerSubsystem.indexMotor.set(0.0d);
        IntakeSubsystem.intakeMotor.set(0.0d);
        System.out.println("AutoDriveShoot ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
