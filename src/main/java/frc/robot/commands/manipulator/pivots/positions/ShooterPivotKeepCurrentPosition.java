// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.positions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;

public class ShooterPivotKeepCurrentPosition extends Command {
  ShooterPivot shooterPivot;

  double currentAngle;
  double currentTarget;

  PIDController targetPIDController;

  /** Creates a new ShooterPivotKeepCurrentPosition. */
  public ShooterPivotKeepCurrentPosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    shooterPivot = Robot.getRobotContainer().getShooterPivot();

    targetPIDController = new PIDController(0.0520, 0.005, 0.0);

    addRequirements(shooterPivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (shooterPivot.humanInteraction()) {
      shooterPivot.setCurrentTarget(shooterPivot.getAbsoluteEncoderDeg());
    }

    currentAngle = shooterPivot.getAbsoluteEncoderDeg();
    currentTarget = shooterPivot.getCurrentTarget();

    shooterPivot.driveShooterPivot(targetPIDController.calculate(currentAngle, currentTarget));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
