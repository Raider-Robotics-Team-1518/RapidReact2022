// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoDriveNoShoot;
import frc.robot.commands.FirePiston;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallRejectSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PivoterSubsystem;
import frc.robot.subsystems.SolenoidSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SolenoidSubsystem m_solenoid = new SolenoidSubsystem(0,1);
  private final BallShooterSubsystem m_ballShooter = new BallShooterSubsystem();
  private final BallIndexerSubsystem m_ballIndexer = new BallIndexerSubsystem();
  private final IntakeSubsystem m_ballIntake = new IntakeSubsystem();
  private final BallRejectSubsystem m_ballRejecter = new BallRejectSubsystem();
  private final PivoterSubsystem m_limelightPivoter = new PivoterSubsystem();
  public static DriveTrain m_driveTrain = new DriveTrain();

  private final Command m_autoCommand = new AutoDriveNoShoot();

  public static Joystick joystick = new Joystick(0);
  public JoystickButton shootButton;
  public JoystickButton indexButton;
  public JoystickButton intakeButton;
  public JoystickButton switchPressure;


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
  private void configureButtonBindings() {
    switchPressure = new JoystickButton(joystick, 7);
    switchPressure.whenPressed(new FirePiston(m_solenoid));

    shootButton  = new JoystickButton(joystick, 2);
    shootButton.whileHeld(() -> m_ballShooter.enableShooterMotor()).whenReleased(() -> m_ballShooter.disableShooterMotor());

    indexButton = new JoystickButton(joystick, 1);
    indexButton.whileHeld(() -> m_ballIndexer.enableIndexer()).whenReleased(() -> m_ballIndexer.disableIndexer());

    intakeButton = new JoystickButton(joystick, 4);
    intakeButton.whileHeld(() -> m_ballIntake.enableIntaker()).whenReleased(() -> m_ballIntake.disableIntaker());

  }

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
