// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.pivots.pivotsParameters;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class toggleOverride extends InstantCommand {
  IntakePivot intakePivot;
  ShooterPivot shooterPivot;

  public toggleOverride() {
    intakePivot = Robot.getRobotContainer().getIntakePivot();
    shooterPivot = Robot.getRobotContainer().getShooterPivot();

    addRequirements(intakePivot);
    addRequirements(shooterPivot);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakePivot.toggleOverride();
    shooterPivot.toggleOverride();
  }
}
