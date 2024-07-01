// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.primitives;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class ShooterPivotMaintainTarget extends Command {
  /** Creates a new ShooterPivotMaintainTarget. */

  double maxPower;

  public ShooterPivotMaintainTarget(double maxPower) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getRobotContainer().getShooterPivot());
    this.maxPower = maxPower;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.getRobotContainer().getShooterPivot().maintainTarget(maxPower);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.getRobotContainer().getShooterPivot().driveShooterPivot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
