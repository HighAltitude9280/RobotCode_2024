// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.primitives;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.resources.joysticks.HighAltitudeJoystick.AxisType;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;

public class AxisDriveIntakePivot extends Command {
  IntakePivot intakePivot;
  double axis;

  /** Creates a new AxisDriveShooterPivot. */
  public AxisDriveIntakePivot() {
    intakePivot = Robot.getRobotContainer().getIntakePivot();

    addRequirements(intakePivot);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    axis = -OI.getInstance().getCopilot().getAxis(AxisType.LEFT_Y);

    if (axis > 0) {
      intakePivot.driveIntakePivot(0.25);
    }
    if (axis < 0) {
      intakePivot.driveIntakePivot(-0.25);
    } else {
      intakePivot.driveIntakePivot(0);
    }

    System.out.println("AAAAAAAA");
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
