// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.HighAltitudeConstants;
import frc.robot.OI;
import frc.robot.Robot;

public class ShooterDriveRPM extends Command {
  private int rpm;

  /** Creates a new ShooterDriveRPM. */
  public ShooterDriveRPM(int rpm) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getRobotContainer().getShooter());
    this.rpm = rpm;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.getRobotContainer().getShooter().setRPMPower(rpm * HighAltitudeConstants.SHOOTER_RPM_TO_POWER);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean shooterOnTarget = Robot.getRobotContainer().getShooter().shooterDriveRPM(rpm);
    if (shooterOnTarget)
      OI.getInstance().getPilot().getHaptics().rumble(1, 0.1);
    SmartDashboard.putBoolean(" Shooter On Target ", shooterOnTarget);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.getRobotContainer().getShooter().stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
