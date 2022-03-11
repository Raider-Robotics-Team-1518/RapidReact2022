package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.LimeLight;

public class AutoDriveIntakeShoot extends CommandBase{
    private static final double distanceToDrive = 117; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;
    private long startTime;

    public AutoDriveIntakeShoot() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("AutoDriveIntakeShoot ---> execute()");
        if (!isFinished()) {
            DeployIntake.fire();
            System.out.println("AutoDriveIntakeShoot ---> Intake engaged");
            IntakeSubsystem.enableIntaker();
            System.out.println("AutoDriveIntakeShoot ---> Driving...");
            auto.driveforward(distanceToDrive);
            auto.stop();
            if(LimeLight.isTargetAvalible()) {
                System.out.println("AutoDriveIntakeShoot ---> Hub found!");
                auto.shootBall("AutoDriveIntakeShoot");
            }
            startTime = System.currentTimeMillis();
            while(startTime+1000 > System.currentTimeMillis()) {
                BallIndexerSubsystem.indexMotor.set(Constants.IndexSpeed);
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
        System.out.println("AutoDriveIntakeShoot ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
