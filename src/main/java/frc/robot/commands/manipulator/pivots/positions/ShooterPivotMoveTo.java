// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.positions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;
import frc.robot.resources.math.Math;

public class ShooterPivotMoveTo extends Command {
  ShooterPivot shooterPivot;

  double maxPower;
  double target;
  double error;

  double breakingDegrees = 150;

  /** Creates a new ShooterPivotMoveTo. */
  public ShooterPivotMoveTo(double maxPower, double target) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooterPivot = Robot.getRobotContainer().getShooterPivot();
    this.target = target;
    this.maxPower = maxPower;

    addRequirements(shooterPivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterPivot.setCurrentTarget(target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    error = target - shooterPivot.getShooterPivotPositionInDegrees();
    double power = error / (maxPower * maxPower * breakingDegrees);

    power = Math.clamp(power, -1, 1) * maxPower * 1;
    shooterPivot.driveShooterPivot(power);
    SmartDashboard.putNumber("CurrentPower", Math.abs(power));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterPivot.driveShooterPivot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooterPivot.getShooterPivotTopLimitSwitch() == true || shooterPivot.getShooterPivotBottomLimitSwitch() == true) {
      return true;
    } else {
      return false;
    }
  }
}
