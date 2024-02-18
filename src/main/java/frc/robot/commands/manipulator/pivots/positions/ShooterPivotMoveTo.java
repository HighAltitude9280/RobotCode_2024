// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.positions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.HighAltitudeConstants;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;

public class ShooterPivotMoveTo extends Command {
  ShooterPivot shooterPivot;

  double target;
  double currentAngle;
  double currentTarget;

  PIDController targetPIDController;

  /** Creates a new ShooterPivotMoveTo. */
  public ShooterPivotMoveTo(double target) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooterPivot = Robot.getRobotContainer().getShooterPivot();

    targetPIDController = new PIDController(0.08, 0.0, 0.0);

    addRequirements(shooterPivot);
    this.target = target;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterPivot.setCurrentTarget(target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    currentAngle = shooterPivot.getAbsoluteEncoderDeg();
    currentTarget = shooterPivot.getCurrentTarget();

    shooterPivot.driveShooterPivot(targetPIDController.calculate(currentAngle, currentTarget));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterPivot.driveShooterPivot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (shooterPivot.getShooterPivotTopLimitSwitch() == true) {
      return false;
    } else {
      return true;
    }
  }
}
