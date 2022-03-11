package frc.robot.utils;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class DualSolenoid {
    public final Solenoid solenoid1, solenoid2;

    public DualSolenoid(PneumaticsModuleType moduleType, int channel1, int channel2) {
        this.solenoid1 = new Solenoid(moduleType, channel1);
        this.solenoid2 = new Solenoid(moduleType, channel2);
    }

    public void setup() {
        this.solenoid1.set(true);
        this.solenoid2.set(false);
    }

    public void toggleSwitch() {
        this.solenoid1.set(!this.solenoid1.get());
        this.solenoid2.set(!this.solenoid2.get());
    }

    public Solenoid getSolenoidOne() {
        return this.solenoid1;
    }

    public Solenoid getSolenoidTwo() {
        return this.solenoid2;
    }
}
