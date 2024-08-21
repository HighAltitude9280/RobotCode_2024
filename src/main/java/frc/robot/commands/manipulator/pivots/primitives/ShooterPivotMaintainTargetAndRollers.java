// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.primitives;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class ShooterPivotMaintainTargetAndRollers extends Command {
  double maxPower;
  double target;

  /** Creates a new ShooterPivotMaintainTargetAndRollers. */
  public ShooterPivotMaintainTargetAndRollers(double maxPower, double target) {
    addRequirements(Robot.getRobotContainer().getShooterPivot());
    this.maxPower = maxPower;
    this.target = target;
  }

  public ShooterPivotMaintainTargetAndRollers(double maxPower) {
    addRequirements(Robot.getRobotContainer().getShooterPivot());
    this.maxPower = maxPower;
    this.target = Double.NaN;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (target != Double.NaN)
      Robot.getRobotContainer().getShooterPivot().setAngleTarget(target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean onTarget = Robot.getRobotContainer().getShooterPivot().maintainTarget(maxPower);
    SmartDashboard.putBoolean("ShooterPivotOnTarget", onTarget);
    if (!onTarget) {
      if (Robot.getRobotContainer().getShooter().hasNote())
        Robot.getRobotContainer().getShooter().stopRollers();
      else
        Robot.getRobotContainer().getShooter().driveRollers(0.5);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.getRobotContainer().getShooter().stopRollers();
    Robot.getRobotContainer().getShooterPivot().driveShooterPivot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
