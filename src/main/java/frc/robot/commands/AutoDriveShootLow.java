package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;

public class AutoDriveShootLow extends CommandBase{
    private static final double distanceToDrive = 42; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootLow() {
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
            Timer.delay(1.75);
            RobotContainer.m_ballIndexer.enableManIndexer();
            Timer.delay(0.75);
            RobotContainer.m_ballIndexer.disableIndexer();
            RobotContainer.m_ballShooter.disableShooterMotor();
            auto.driveforward(distanceToDrive);
            auto.stop();
            //DeployIntake.fire();
            RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid1.set(false);
            RobotContainer.m_intakeSolenoid.dualSolenoid.solenoid2.set(true);
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
        System.out.println("AutoDriveShoot ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
