// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DeployIntake;
import frc.robot.subsystems.BallIndexerSubsystem;
import frc.robot.subsystems.BallRejectSubsystem;
import frc.robot.subsystems.BallShooterSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;
import frc.robot.subsystems.SolenoidSubsystem;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public final SendableChooser<Command> m_commandChooser = new SendableChooser<>();

  // The robot's subsystems and commands are defined here...
  public static DriveTrain m_driveTrain = new DriveTrain();
  public static SolenoidSubsystem m_intakeSolenoid = new SolenoidSubsystem(0,1);
  public static BallShooterSubsystem m_ballShooter = new BallShooterSubsystem();
  public static BallIndexerSubsystem m_ballIndexer = new BallIndexerSubsystem();
  public static IntakeSubsystem m_ballIntaker = new IntakeSubsystem();
  public static BallRejectSubsystem m_ballRejecter = new BallRejectSubsystem();
  public static ClimbSubsystem m_climb = new ClimbSubsystem();
  public static LightsSubsystem m_blinkies = new LightsSubsystem();

  public static Joystick joystick = new Joystick(0);
  public static XboxController controller = new XboxController(1);
  public JoystickButton shootButton;
  public JoystickButton shootManButton;
  public JoystickButton directionButton;
  public JoystickButton indexButton;
  public JoystickButton indexManButton;
  public JoystickButton intakeController;
  public JoystickButton backfeedButton;
  public JoystickButton intakeButton;
  public JoystickButton switchPressureJ;
  public JoystickButton switchPressureC;
  public JoystickButton climbUpButton;
  public JoystickButton climbDownButton;

  public static Alliance allianceColor = null;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_commandChooser.setDefaultOption("None", null);
    m_commandChooser.addOption("1. Drive only", new AutoDriveNoShoot());
    m_commandChooser.addOption("2. Shoot Low and Drive", new AutoDriveShootLow());
    m_commandChooser.addOption("3. Shoot Low and Drive + Get ball", new AutoDriveShootLowIntake());
    m_commandChooser.addOption("4a. Shoot Low and Drive + Get ball + Shoot Low", new AutoDriveShootLowIntakeLow());
    m_commandChooser.addOption("4b. Shoot Low and Drive + Get ball + Shoot Low (Wall side)", new AutoDriveShootLowIntakeLowShort());
    m_commandChooser.addOption("5. Shoot Low and Drive + Get ball + Shoot High", new AutoDriveShootLowIntakeHigh());
    m_commandChooser.addOption("6. Shoot High and Drive + Get ball + Shoot High", new AutoDriveShootHighIntakeHigh());
    SmartDashboard.putData("Auto Options:", m_commandChooser);
    SmartDashboard.putNumber("X Factor", 0.9d);
    SmartDashboard.putNumber("Z Factor", 0.65d);
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
    switchPressureJ = new JoystickButton(joystick, 3);
    switchPressureJ.whenPressed(new DeployIntake(m_intakeSolenoid));

    switchPressureC = new JoystickButton(controller, 7);
    switchPressureC.whenPressed(new DeployIntake(m_intakeSolenoid));

    directionButton = new JoystickButton(joystick, 2);
    directionButton.whenPressed(() -> m_driveTrain.switchDriveDirection());

    shootButton  = new JoystickButton(controller, 8);
    shootButton.whileHeld(() -> m_ballShooter.enableShooterMotor()).whenReleased(() -> m_ballShooter.disableShooterMotor());

    indexButton = new JoystickButton(controller, 2);
    indexButton.whileHeld(() -> m_ballIndexer.enableIndexer()).whenReleased(() -> m_ballIndexer.disableIndexer());

    shootManButton  = new JoystickButton(controller, 9);
    shootManButton.whileHeld(() -> m_ballShooter.shooterManualMode()).whenReleased(() -> m_ballShooter.disableShooterMotor());

    indexManButton = new JoystickButton(controller, 10);
    indexManButton.whileHeld(() -> m_ballIndexer.enableManIndexer()).whenReleased(() -> m_ballIndexer.disableIndexer());

    backfeedButton = new JoystickButton(controller, 1);
    backfeedButton.whileHeld(() -> m_ballIndexer.backfeedIndexer()).whenReleased(() -> m_ballIndexer.disableIndexer());

    intakeButton = new JoystickButton(joystick, 1);
    intakeButton.whileHeld(() -> m_ballIntaker.enableIntaker()).whenReleased(() -> m_ballIntaker.disableIntaker());

    intakeController = new JoystickButton(controller, 3);
    intakeController.whileHeld(() -> m_ballIntaker.enableIntaker()).whenReleased(() -> m_ballIntaker.disableIntaker());
    
    climbUpButton = new JoystickButton(controller, 5);
    climbUpButton.whileHeld(() -> m_climb.enableClimb(true)).whenReleased(() -> m_climb.disableClimb());
    
    climbDownButton = new JoystickButton(controller, 6);
    climbDownButton.whileHeld(() -> m_climb.enableClimb(false)).whenReleased(() -> m_climb.disableClimb());
  }
}
