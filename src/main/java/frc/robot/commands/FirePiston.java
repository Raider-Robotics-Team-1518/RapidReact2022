package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SolenoidSubsystem;

public class FirePiston extends CommandBase {
    private static SolenoidSubsystem solenoidSubsystem;

    public FirePiston(SolenoidSubsystem solenoidSubsystem) {
        FirePiston.solenoidSubsystem = solenoidSubsystem;
        addRequirements(solenoidSubsystem);
    }

    @Override
    public void initialize() {
        fire();
        //System.out.println("Piston fired");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public static void fire() {
        solenoidSubsystem.dualSolenoid.toggleSwitch();
    }
}
