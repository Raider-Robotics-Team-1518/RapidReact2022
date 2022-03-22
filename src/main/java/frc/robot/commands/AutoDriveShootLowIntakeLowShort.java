package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveShootLowIntakeLowShort extends CommandBase{
    private static final double distanceToDrive = 38; // inches
    private static final double backwardDistance = 36; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootLowIntakeLowShort() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        System.out.println("AutoDriveShootLowIntakeLowShort ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShootLowIntakeLowShort ---> Driving...");
            auto.shootBallLow();
            auto.deployIntakeArms();
            auto.enableIntake();
            auto.driveforward(distanceToDrive);
            auto.stop();
            auto.waitForBall();

            auto.disableIntakeSystem();
            auto.drivebackward(backwardDistance);
            auto.shootBallLow();
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        auto.disableAllMotors();
        System.out.println("AutoDriveShootLowIntakeLowShort ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
