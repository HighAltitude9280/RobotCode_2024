// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve.Primitives;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.swerve.SwerveDriveTrain;

public class DriveToClosestNote extends Command {
  SwerveDriveTrain drivetrain;
  boolean fieldOriented;
  double maxPower;

  /** Drives to the closest note detected by the note cam */
  public DriveToClosestNote(double maxPower) {

    drivetrain = Robot.getRobotContainer().getSwerveDriveTrain();
    addRequirements(drivetrain);

    this.maxPower = maxPower;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    fieldOriented = drivetrain.getIsFieldOriented();
    drivetrain.setIsFieldOriented(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double yaw = Robot.getRobotContainer().getVision().getBiggestNoteTarget().getYaw();
    drivetrain.driveToTarget(yaw, maxPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setIsFieldOriented(true);
    drivetrain.stopModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
