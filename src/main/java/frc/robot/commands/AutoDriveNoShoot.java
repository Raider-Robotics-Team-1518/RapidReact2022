package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveNoShoot extends CommandBase {
    private static final double distanceToDrive = 40; // inches
    private static AutoSubsystem auto;
    private static boolean isDone = false;

  public AutoDriveNoShoot() {
    auto = new AutoSubsystem();
  }

  @Override
  public void initialize() {
  }
  @Override
  public void execute() {
    System.out.println("--> Auto_DriveOffLine::execute()");
    if (!isFinished()) {
      System.out.println("--> Auto_DriveOffLine::should be driving");
      auto.driveforward(distanceToDrive);
      auto.turnleft(90);
      auto.stop();
      end(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    isDone = true;

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
