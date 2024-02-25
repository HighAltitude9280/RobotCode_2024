// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.primitives;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;
import frc.robot.subsystems.manipulator.pivots.IntakePivot.IntakePivotPosition;

public class ToggleIntakePivot extends Command {
  IntakePivot intakePivot;

  /** Creates a new ToggleIntakePivot. */
  public ToggleIntakePivot() {
    intakePivot = Robot.getRobotContainer().getIntakePivot();

    addRequirements(intakePivot);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakePivot.toggleIntakePivotDirection();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (intakePivot.getCurrentPosition() == IntakePivotPosition.STORED) {
      intakePivot.driveIntakePivot(-0.20);
    } else {
      intakePivot.driveIntakePivot(0.20);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakePivot.driveIntakePivot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
