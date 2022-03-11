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
import frc.robot.commands.AutoDriveIntakeShoot;
import frc.robot.commands.AutoDriveNoShoot;
import frc.robot.commands.AutoDrivePickupShoot;
import frc.robot.commands.AutoDriveShoot;
import frc.robot.commands.AutoDriveShootLow;
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
  public final SolenoidSubsystem m_intakeSolenoid = new SolenoidSubsystem(0,1);
  public static BallShooterSubsystem m_ballShooter = new BallShooterSubsystem();
  public static BallIndexerSubsystem m_ballIndexer = new BallIndexerSubsystem();
  private final IntakeSubsystem m_ballIntaker = new IntakeSubsystem();
  private final BallRejectSubsystem m_ballRejecter = new BallRejectSubsystem();
  //private final ClimbSubsystem m_climb = new ClimbSubsystem();
  public static LightsSubsystem m_blinkies = new LightsSubsystem();
  public static UsbCamera usbCamera;

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


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    usbCamera = CameraServer.startAutomaticCapture();
    usbCamera.setVideoMode(PixelFormat.kMJPEG, 160, 120, 15);

    m_commandChooser.setDefaultOption("None", null);
    m_commandChooser.addOption("Drive only", new AutoDriveNoShoot());
    m_commandChooser.addOption("Drive and Shoot Low", new AutoDriveShootLow());
    m_commandChooser.addOption("Drive and Shoot 1 High", new AutoDriveShoot());
    m_commandChooser.addOption("Drive and Shoot 2 balls", new AutoDriveIntakeShoot());
    m_commandChooser.addOption("Drive n' Shoot, Drive n' Shoot", new AutoDrivePickupShoot());
    SmartDashboard.putData("Autonomous Choices", m_commandChooser);

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
    intakeButton.whileHeld(() -> IntakeSubsystem.enableIntaker()).whenReleased(() -> IntakeSubsystem.disableIntaker());

    intakeController = new JoystickButton(controller, 3);
    intakeController.whileHeld(() -> IntakeSubsystem.enableIntaker()).whenReleased(() -> IntakeSubsystem.disableIntaker());
    
    climbUpButton = new JoystickButton(controller, 5);
    climbUpButton.whileHeld(() -> ClimbSubsystem.enableClimb(true)).whenReleased(() -> ClimbSubsystem.disableClimb());
    
    climbDownButton = new JoystickButton(controller, 6);
    climbDownButton.whileHeld(() -> ClimbSubsystem.enableClimb(false)).whenReleased(() -> ClimbSubsystem.disableClimb());
  }
}
