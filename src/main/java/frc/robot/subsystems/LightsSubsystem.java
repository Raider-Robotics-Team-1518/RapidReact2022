package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightsSubsystem extends SubsystemBase {
    public LightsSubsystem() {
    }

    @Override
    public void periodic() {

    }


    public enum LEDState {
        DEFAULT(new DigitalOutput(1)),
        BLUE(new DigitalOutput(2)),
        RED(new DigitalOutput(2)),
        PULSE_BLUE(new DigitalOutput(3)),
        PULSE_RED(new DigitalOutput(3));
    
        private final DigitalOutput dOutput;
        private LEDState(DigitalOutput dOutput) {
          this.dOutput = dOutput;
        }
    
        public DigitalOutput getOutput() {
         return this.dOutput;
        }
      }
}
