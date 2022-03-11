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
import frc.robot.subsystems.SolenoidSubsystem;

public class AutoDriveShootLowIntake extends CommandBase{
    private static final double distanceToDrive = 42; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootLowIntake() {
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
            RobotContainer.m_ballShooter.shooterMotor.set(0.5d);
            Timer.delay(1.25d);
            RobotContainer.m_ballIndexer.indexMotor.set(Constants.IndexSpeed);
            Timer.delay(0.5d);
            RobotContainer.m_ballIndexer.indexMotor.set(0.0d);
            RobotContainer.m_ballShooter.shooterMotor.set(0.0d);

            RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid1.set(false);
            RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid2.set(true);
            Timer.delay(1.0d);

            RobotContainer.m_ballIntaker.intakeMotor.set(Constants.IntakeSpeed);
            auto.driveforward(distanceToDrive);
            auto.stop();

            while(RobotContainer.m_ballRejecter.getBallColorName(RobotContainer.m_ballRejecter.m_colorSensor.getColor()).equalsIgnoreCase("None") && RobotState.isAutonomous()) {
                RobotContainer.m_ballIndexer.indexMotor.set(Constants.IndexSpeed);
                Timer.delay(0.1d);
            }
    
            RobotContainer.m_ballIntaker.intakeMotor.set(0);
            RobotContainer.m_ballIndexer.indexMotor.set(0);
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        RobotContainer.m_ballShooter.shooterMotor.set(0.0d);
        RobotContainer.m_ballIndexer.indexMotor.set(0.0d);
        RobotContainer.m_ballIntaker.intakeMotor.set(0.0d);
        System.out.println("AutoDriveShoot ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
