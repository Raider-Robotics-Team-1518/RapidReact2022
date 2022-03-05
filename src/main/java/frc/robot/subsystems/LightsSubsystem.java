package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightsSubsystem extends SubsystemBase {
  public LEDState lightState = LEDState.DEFAULT;
  public final DigitalOutput dOutput1 = new DigitalOutput(1);
  public final DigitalOutput dOutput2 = new DigitalOutput(2);
  public final DigitalOutput dOutput3 = new DigitalOutput(3);

  public LightsSubsystem() {
  }

  @Override
  public void periodic() {
    if(RobotState.isDisabled()) {
      setLEDState(LEDState.DEFAULT);
      return;
    }
    if(BallRejectSubsystem.getCurrentColorBall().equalsIgnoreCase("None")) {
      switch(BallRejectSubsystem.teamColor.toLowerCase()) {
        case "Blue":
          setLEDState(LEDState.BLUE);
          return;
        case "Red":
          setLEDState(LEDState.RED);
          return;
      }
    } else {
      switch(BallRejectSubsystem.teamColor.toLowerCase()) {
        case "Blue":
          setLEDState(LEDState.PULSE_BLUE);
          return;
        case "Red":
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
      DEFAULT(false, false, false),
      BLUE(false, false, true),
      RED(false, true, false),
      PULSE_BLUE(false, true, true),
      PULSE_RED(true, false, true);
  
      private final boolean do1, do2, do3;
      private LEDState(boolean do1, boolean do2, boolean do3) {
        this.do1 = do1;
        this.do2 = do2;
        this.do3 = do3;
      }
  }
}
