// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.primitiveAutos;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.HighAltitudeConstants;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;

public class MoveUntilLimit extends Command {
  IntakePivot intakePivot;
  double speed;

  /** Creates a new MoveUntilLimit. */
  public MoveUntilLimit(double speed) {
    intakePivot = Robot.getRobotContainer().getIntakePivot();
    this.speed = speed;

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
    intakePivot.driveIntakePivot(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakePivot.driveIntakePivot(0);
    System.out.println("AAAAA");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (intakePivot.getIntakePivotPositionDegrees() < HighAltitudeConstants.INTAKE_PIVOT_UPPER_LIMIT && speed > 0) {
      return true;
    } else if (intakePivot.getIntakePivotPositionDegrees() > HighAltitudeConstants.INTAKE_PIVOT_LOWER_LIMIT
        && speed < 0) {
      return true;
    } else {
      return false;
    }
  }
}
