package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.DualSolenoid;

public class SolenoidSubsystem extends SubsystemBase {
    public DualSolenoid dualSolenoid;
    
    public SolenoidSubsystem(int channel1, int channel2) {
        dualSolenoid = new DualSolenoid(PneumaticsModuleType.CTREPCM, channel1, channel2);
        dualSolenoid.setup();
    }
}
