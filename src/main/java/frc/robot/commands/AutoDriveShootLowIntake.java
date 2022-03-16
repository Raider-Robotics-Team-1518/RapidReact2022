package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

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
        System.out.println("AutoDriveShootLowIntake ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShootLowIntake ---> Driving...");
            auto.shootBallLow();
            auto.deployIntakeArms();
            auto.enableIntake();
            auto.driveforward(distanceToDrive);
            auto.waitForBall();
            auto.disableIntakeSystem();
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        auto.disableAllMotors();
        System.out.println("AutoDriveShootLowIntake ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
