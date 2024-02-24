// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.primitives;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.resources.joysticks.HighAltitudeJoystick.AxisType;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;

public class AxisDriveShooterPivot extends Command {
  ShooterPivot shooterPivot;
  double axis;

  /** Creates a new AxisDriveShooterPivot. */
  public AxisDriveShooterPivot() {
    shooterPivot = Robot.getRobotContainer().getShooterPivot();

    addRequirements(shooterPivot);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    axis = -OI.getInstance().getCopilot().getAxis(AxisType.RIGHT_Y);

    if (axis > 0) {
      shooterPivot.driveShooterPivot(0.25);
    }
    if (axis < 0) {
      shooterPivot.driveShooterPivot(-0.25);
    } else {
      shooterPivot.driveShooterPivot(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterPivot.driveShooterPivot(0);
    shooterPivot.setCurrentTarget(shooterPivot.getAbsoluteEncoderDeg());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
