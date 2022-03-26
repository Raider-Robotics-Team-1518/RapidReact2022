package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveShootHigh extends CommandBase{
    private static final double distanceToDrive = 42; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootHigh() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("AutoDriveShootHigh ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShootHigh ---> Driving...");
            auto.shootBallHigh();
            auto.driveforward(distanceToDrive);
            auto.stop();
            auto.deployIntakeArms();
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        auto.disableAllMotors();
        System.out.println("AutoDriveShootHigh ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}

