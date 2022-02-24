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
    FirePiston.fire();
  }
  @Override
  public void execute() {
    auto.consolePrint(this.getClass().getSimpleName(), "execute()");
    if (!isFinished()) {
      auto.consolePrint(this.getClass().getSimpleName(), "Driving...");
      auto.drivebackward(distanceToDrive);
      auto.stop();
      end(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    isDone = true;
    auto.consolePrint(this.getClass().getSimpleName(), "end()");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
