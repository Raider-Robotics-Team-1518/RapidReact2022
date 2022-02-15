// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoDriveNoShoot;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallRejectSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.PivoterSubsystem;
import frc.robot.subsystems.SolenoidSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final AutoSubsystem m_examplSubsystem = new AutoSubsystem();
  private final SolenoidSubsystem m_solenoid = new SolenoidSubsystem(2,3);
  private final BallShooterSubsystem m_ballShooter = new BallShooterSubsystem();
  private final BallIndexerSubsystem m_ballIndexer = new BallIndexerSubsystem();
  private final BallRejectSubsystem m_ballRejecter = new BallRejectSubsystem();
  private final PivoterSubsystem m_limelightPivoter = new PivoterSubsystem();
  public static DriveTrain m_driveTrain = new DriveTrain();

  private final Command m_autoCommand = new AutoDriveNoShoot();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
