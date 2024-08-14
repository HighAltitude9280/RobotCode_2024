// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.manipulator.intake.Intake;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;
import frc.robot.subsystems.manipulator.shooter.Shooter;
import frc.robot.subsystems.swerve.SwerveDriveTrain;

public class AutoAimShoot extends Command {
  /**
   * Aims the drivetrain, the pivot, drives the shooter at the ideal speed and
   * shoots when it's on target, then it stops.
   */

  boolean hasHadNote = false;

  Shooter shooter;
  Intake intake;
  SwerveDriveTrain drivetrain;
  ShooterPivot pivot;

  public AutoAimShoot() {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = Robot.getRobotContainer().getShooter();
    intake = Robot.getRobotContainer().getIntake();
    drivetrain = Robot.getRobotContainer().getSwerveDriveTrain();
    pivot = Robot.getRobotContainer().getShooterPivot();

    addRequirements(shooter, intake, drivetrain, pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    boolean swerveOnTarget = drivetrain.pointToSpeaker(0.5);
    pivot.pointToSpeaker(0.5);
    if (swerveOnTarget)
      shooter.shootToSpeakerAutoRollers();
    else
      shooter.driveRPMToSpeaker();

    if (shooter.hasNote())
      hasHadNote = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopRollers();
    shooter.stopShooter();
    drivetrain.stopModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return hasHadNote && !shooter.hasNote();
  }
}
