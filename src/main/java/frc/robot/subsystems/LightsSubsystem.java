package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class LightsSubsystem extends SubsystemBase {
  public LEDState lightState = LEDState.DEFAULT;
  public final DigitalOutput dOutput1 = new DigitalOutput(1);
  public final DigitalOutput dOutput2 = new DigitalOutput(2);
  public final DigitalOutput dOutput3 = new DigitalOutput(3);
  public final DigitalOutput dOutput4 = new DigitalOutput(4);

  public LightsSubsystem() {
  }

  @Override
  public void periodic() {
    if(RobotState.isDisabled()) {
      setLEDState(LEDState.DEFAULT);
      return;
    }

    if(BallRejectSubsystem.getCurrentColorBall().equalsIgnoreCase("None")) {
      switch(RobotContainer.allianceColor.toString().toLowerCase()) {
        case "blue":
          setLEDState(LEDState.BLUE);
          return;
        case "red":
          setLEDState(LEDState.RED);
          return;
      }
    /*} else if(BallShooterSubsystem.shooterMotor.get() > 0.1d) {
      switch(RobotContainer.allianceColor.toString().toLowerCase()) {
        case "blue":
          setLEDState(LEDState.BLUE_WITH_RED_STRIPE);
          return;
        case "red":
          setLEDState(LEDState.RED_WITH_BLUE_STRIPE);
          return;
      }*/
    } else {
      switch(RobotContainer.allianceColor.toString().toLowerCase()) {
        case "blue":
          setLEDState(LEDState.PULSE_BLUE);
          return;
        case "red":
          setLEDState(LEDState.PULSE_RED);
          return;
      }
    }
  }

  public void setLEDState(LEDState state) {
    dOutput1.set(state.do1);
    dOutput2.set(state.do2);
    dOutput3.set(state.do3);
    this.lightState = state;
  }


  public enum LEDState {
      DEFAULT(false, true, true), //(false, false, false),
      BLUE(false, true, false),
      RED(true, false, false),
      PULSE_BLUE(false, false, true),
      PULSE_RED(true, true, false),
      RED_WITH_BLUE_STRIPE(false, true, true),
      BLUE_WITH_RED_STRIPE(true, false, true);
  
      private final boolean do1, do2, do3;
      private LEDState(boolean do1, boolean do2, boolean do3) {
        this.do1 = do1;
        this.do2 = do2;
        this.do3 = do3;
      }
  }
}
