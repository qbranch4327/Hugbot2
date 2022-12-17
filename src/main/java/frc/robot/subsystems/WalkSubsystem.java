package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WalkSubsystem extends SubsystemBase {
    private final XboxController controller;
    private final VictorSP walkMotor;
    private final int LeftY = XboxController.Axis.kLeftY.value;

    public WalkSubsystem(XboxController controller, VictorSP walkMotor) {
        this.controller = controller;
        this.walkMotor = walkMotor;
    }

    private double calculateSpeed(double rawAxis) {
        return (Math.abs(rawAxis) < 0.1d) ? 0 : rawAxis * 0.6;
    }

    public void walk() {
        double speed = calculateSpeed(controller.getRawAxis(LeftY));
        walkMotor.set(speed);
    }
}
