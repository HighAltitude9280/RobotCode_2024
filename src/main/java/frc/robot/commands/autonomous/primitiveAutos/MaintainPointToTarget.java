// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.primitiveAutos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.resources.math.Math;

public class MaintainPointToTarget extends Command {
  /** Creates a new MaintainPointAtTarget. */
  double shooterMaxPower, turnMaxPower;

  public MaintainPointToTarget(double shooterMaxPower, double turnMaxPower) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getRobotContainer().getSwerveDriveTrain());
    addRequirements(Robot.getRobotContainer().getShooterPivot());

    this.shooterMaxPower = shooterMaxPower;
    this.turnMaxPower = turnMaxPower;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Robot.getRobotContainer().getShooterPivot().pointToSpeaker(shooterMaxPower);
    Robot.getRobotContainer().getSwerveDriveTrain().pointToSpeaker(shooterMaxPower);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
