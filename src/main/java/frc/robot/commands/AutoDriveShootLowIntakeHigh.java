package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveShootLowIntakeHigh extends CommandBase{
    private static final double distanceToDrive = 42; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootLowIntakeHigh() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        System.out.println("AutoDriveShootLowIntakeHigh ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShootLowIntakeHigh ---> Driving...");
            auto.shootBallLow();
            auto.deployIntakeArms();
            auto.enableIntake();
            auto.driveforward(distanceToDrive);
            auto.stop();
            auto.waitForBall();
            auto.drivebackward(distanceToDrive+8);
            auto.shootBallHigh();
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        auto.disableAllMotors();
        System.out.println("AutoDriveShootLowIntakeHigh ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
