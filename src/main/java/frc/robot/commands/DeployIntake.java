package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SolenoidSubsystem;

public class DeployIntake extends CommandBase {
    private static SolenoidSubsystem solenoidSubsystem;

    public DeployIntake(SolenoidSubsystem solenoidSubsystem) {
        DeployIntake.solenoidSubsystem = solenoidSubsystem;
        addRequirements(solenoidSubsystem);
    }

    @Override
    public void initialize() {
        fire();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public static void fire() {
        solenoidSubsystem.dualSolenoid.toggleSwitch();
    }
}
