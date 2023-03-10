package frc.robot.commands;


import edu.wpi.first.math.filter.SlewRateLimiter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrivetrain;

public class SwerveDriveCommand extends CommandBase {

  private final SwerveDrivetrain drivetrain;
  private final XboxController controller;

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter xspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter yspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter rotLimiter = new SlewRateLimiter(3);

  public SwerveDriveCommand(SwerveDrivetrain drivetrain, XboxController controller) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);

    this.controller = controller;
  }

  @Override
  public void execute() {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var xSpeed = -xspeedLimiter.calculate(controller.getLeftY()) * SwerveDrivetrain.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    final var ySpeed = -yspeedLimiter.calculate(controller.getLeftX()) * SwerveDrivetrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot = -rotLimiter.calculate(controller.getRightX()) * SwerveDrivetrain.kMaxAngularSpeed;
    
    boolean calibrate = controller.getLeftBumper();

    drivetrain.drive(getxSpeed(xSpeed) * 0.15, getySpeed(ySpeed) * 0.15, getrot(rot), true, calibrate);
  }

  private double getySpeed(double ySpeed) {
    return Math.abs(ySpeed) > .1 ? (double) ySpeed : 0d;
  }

  private double getxSpeed(double xSpeed) {
    return Math.abs(xSpeed) > .1 ? (double) xSpeed : 0d;
  }

  private double getrot(double rot) {
    return Math.abs(rot) > .2 ? (double) rot : 0d;
  }

}
