package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallRejectSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.LimeLight;

public class AutoDrivePickupShoot extends CommandBase {
    private static final double distanceToDrive = 90; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

    private boolean shotFirstBall = false;

    public AutoDrivePickupShoot() {
        auto = new AutoSubsystem();
    }

    @Override
    public void initialize() {
        DeployIntake.fire();
    }

    @Override
    public void execute() {
        System.out.println("AutoDrivePickupShoot ---> execute()");
        if (!isFinished()) {
            System.out.println("AutoDrivePickupShoot ---> Driving...");
            auto.driveforward(distanceToDrive);
            auto.stop();
            if(LimeLight.isTargetAvalible()) {
                System.out.println("AutoDrivePickupShoot ---> Hub found!");
                auto.shootBall("AutoDrivePickupShoot");
                shotFirstBall = true;
                if(shotFirstBall) {
                    RobotContainer.m_ballIntaker.enableIntaker();
                    auto.driveforward(25); // inches
                    auto.stop();
                    while(BallRejectSubsystem.getCurrentColorBall().equalsIgnoreCase("None")) {
                        RobotContainer.m_ballIntaker.enableIntaker();
                    }
                    auto.shootBall("AutoDrivePickupShoot");
                }
            }
            end(false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        isDone = true;
        shotFirstBall = false;
        BallShooterSubsystem.shooterMotor.set(0.0d);
        BallIndexerSubsystem.indexMotor.set(0.0d);
        System.out.println("AutoDrivePickupShoot ---> end()");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}

