package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AutoSubsystem;

public class AutoDriveNoShoot extends CommandBase {
  private static final double distanceToDrive = 90; // inches
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
    System.out.println("AutoDriveNoShoot ---> execute()");
    if (!isFinished()) {
      //DeployIntake.fire();
      System.out.println("AutoDriveNoShoot ---> Driving...");
      auto.driveforward(distanceToDrive);
      auto.stop();
      end(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    isDone = true;
    System.out.println("AutoDriveNoShoot ---> end()");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
