// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DeployIntake;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallRejectSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SolenoidSubsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final SolenoidSubsystem m_solenoid = new SolenoidSubsystem(0,1);
  private final BallShooterSubsystem m_ballShooter = new BallShooterSubsystem();
  private final BallIndexerSubsystem m_ballIndexer = new BallIndexerSubsystem();
  private final IntakeSubsystem m_ballIntaker = new IntakeSubsystem();
  private final BallRejectSubsystem m_ballRejecter = new BallRejectSubsystem();
  private final ClimbSubsystem m_climb = new ClimbSubsystem();
  public static DriveTrain m_driveTrain = new DriveTrain();

  public static Joystick joystick = new Joystick(0);
  public static XboxController controller = new XboxController(1);
  public JoystickButton shootButton;
  public JoystickButton indexButton;
  public JoystickButton intakeButton;
  public JoystickButton switchPressure;
  public JoystickButton climbUpButton;
  public JoystickButton climbDownButton;


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
    switchPressure.whenPressed(new DeployIntake(m_solenoid));

    shootButton  = new JoystickButton(joystick, 2);
    shootButton.whileHeld(() -> m_ballShooter.enableShooterMotor()).whenReleased(() -> m_ballShooter.disableShooterMotor());

    indexButton = new JoystickButton(joystick, 1);
    indexButton.whileHeld(() -> m_ballIndexer.enableIndexer()).whenReleased(() -> m_ballIndexer.disableIndexer());

    intakeButton = new JoystickButton(joystick, 4);
    intakeButton.whileHeld(() -> IntakeSubsystem.enableIntaker()).whenReleased(() -> IntakeSubsystem.disableIntaker());

    climbUpButton = new JoystickButton(controller, 8);
    climbUpButton.whileHeld(() -> m_climb.enableUp()).whenReleased(() -> m_climb.disableClimb());
    
    climbDownButton = new JoystickButton(controller, 7);
    climbUpButton.whileHeld(() -> m_climb.enableDown()).whenReleased(() -> m_climb.disableClimb());
  }
}
