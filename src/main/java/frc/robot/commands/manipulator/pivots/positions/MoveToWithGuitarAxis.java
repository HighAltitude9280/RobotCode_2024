// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.positions;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;
import frc.robot.resources.math.Math;

public class MoveToWithGuitarAxis extends Command {
  IntakePivot intakePivot;
  double maxPower;
  double target;
  double error;

  double breakingDegrees = 200;

  /** Creates a new IntakePivotMoveTo. */
  public MoveToWithGuitarAxis(double maxPower) {
    intakePivot = Robot.getRobotContainer().getIntakePivot();
    this.maxPower = maxPower;

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
    error = target - intakePivot.getIntakePivotPositionDegrees();
    double power = error / (maxPower * maxPower * breakingDegrees);

    power = Math.clamp(power, -1, 1) * maxPower * -1; // Change the sign if needed

    if (-OI.getInstance().getCopilotG().getRawAxis(5) > 0.3) {
      target = 0;
      intakePivot.driveIntakePivot(power);
    } else if (-OI.getInstance().getCopilotG().getRawAxis(5) < -0.3) {
      target = 160;
      intakePivot.driveIntakePivot(power);
    } else {
      intakePivot.driveIntakePivot(0);
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
    if (Math.abs(error) < 7.5) {
      return true;
    }
    return false;
  }
}
