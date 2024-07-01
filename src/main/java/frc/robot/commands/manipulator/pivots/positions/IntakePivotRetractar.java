// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.positions;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.HighAltitudeConstants;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class IntakePivotRetractar extends Command {
  double maxPower;

  /** Creates a new IntakePivotUp. */
  public IntakePivotRetractar(double maxPower) {
    addRequirements(Robot.getRobotContainer().getIntakePivot());
    // Use addRequirements() here to declare subsystem dependencies.
    this.maxPower = maxPower;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println("23223243");
    return Robot.getRobotContainer().getIntakePivot().intakePivotMoveTo(maxPower, RobotMap.INTAKE_PIVOT_UP_POSITION);
  }
}
