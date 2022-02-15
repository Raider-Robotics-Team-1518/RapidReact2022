package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SolenoidSubsystem;

public class FirePiston extends CommandBase {
    private final SolenoidSubsystem solenoidSubsystem;

    public FirePiston(SolenoidSubsystem solenoidSubsystem) {
        this.solenoidSubsystem = solenoidSubsystem;
        addRequirements(solenoidSubsystem);
    }

    @Override
    public void initialize() {
        solenoidSubsystem.dualSolenoid.toggleSwitch();
        System.out.println("Piston fired");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
