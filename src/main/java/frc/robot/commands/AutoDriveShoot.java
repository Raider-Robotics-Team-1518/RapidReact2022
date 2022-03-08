package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.utils.LimeLight;

public class AutoDriveShoot extends CommandBase{
    private static final double distanceToDrive = 90; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShoot() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
        DeployIntake.fire();
    }
    @Override
    public void execute() {
        System.out.println("AutoDriveShoot ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShoot ---> Driving...");
            auto.driveforward(distanceToDrive);
            auto.stop();
            if(LimeLight.isTargetAvalible()) {
                System.out.println("AutoDriveShoot ---> Hub found!");
                auto.shootBall("AutoDriveShoot");
            }
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
