package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveShootHighIntakeHigh extends CommandBase{
    private static final double distanceToDrive = 42; // inches
    private static final double backwardDistance = 36; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    public AutoDriveShootHighIntakeHigh() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
    }


    @Override
    public void execute() {
        System.out.println("AutoDriveShootHighIntakeHigh ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDriveShootHighIntakeHigh ---> Driving...");
            auto.shootBallHigh();
            auto.deployIntakeArms();
            auto.enableIntake();
            auto.driveforwardBall(distanceToDrive);

            auto.waitForBall();

            auto.disableIntakeSystem();
            auto.gyroTurn(-auto.readGyro());
            auto.drivebackward(backwardDistance);
            auto.shootBallHigh();
            auto.retractIntakeArms();
            auto.driveforward(distanceToDrive);
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        auto.disableAllMotors();
        System.out.println("AutoDriveShootHighIntakeHigh ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}
