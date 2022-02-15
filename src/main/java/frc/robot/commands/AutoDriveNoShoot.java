package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveNoShoot extends CommandBase {
    private static final double distanceToDrive = 32; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;
  

  public AutoDriveNoShoot() {
    // Use addRequirements() here to declare subsystem dependencies.
    auto = new AutoSubsystem();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("--> Auto_DriveOffLine::execute()");
    if (!isDone) {
      System.out.println("--> Auto_DriveOffLine::should be driving");
      auto.drivebackward(distanceToDrive);
      end(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isDone = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
