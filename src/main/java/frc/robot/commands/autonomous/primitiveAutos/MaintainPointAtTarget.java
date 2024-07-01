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

public class MaintainPointAtTarget extends Command {
  /** Creates a new MaintainPointAtTarget. */
  Translation3d target;
  double maxPower;

  public MaintainPointAtTarget(Translation3d target, double maxPower) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.getRobotContainer().getSwerveDriveTrain());
    addRequirements(Robot.getRobotContainer().getShooterPivot());

    this.maxPower = maxPower;
    this.target = target;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Pose2d pose = Robot.getRobotContainer().getSwerveDriveTrain().getPose();

    double deltaX = target.getX() - pose.getX();
    double deltaY = target.getY() - pose.getY();
    double deltaZ = target.getZ() - RobotMap.SHOOTER_HEIGHT;

    double yaw = java.lang.Math.signum(deltaY) * 90;

    if (deltaX != 0) {
      yaw = Math.toDegrees(Math.atan(Math.abs(deltaY / deltaX)));

      if (deltaY < 0 && deltaX > 0)
        yaw *= -1;
      else if (deltaY > 0 && deltaX < 0)
        yaw = 180 - yaw;
      else if (deltaY < 0 && deltaX < 0)
        yaw = yaw - 180;

    }

    yaw = Math.getOppositeAngle(yaw);

    double distanceToTarget = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    double pitch = Math.toDegrees(Math.atan(deltaZ / distanceToTarget));

    Robot.getRobotContainer().getShooterPivot().setAngleTarget(pitch);
    Robot.getRobotContainer().getShooterPivot().maintainTarget(maxPower);
    Robot.getRobotContainer().getSwerveDriveTrain().turnToAngle(yaw, maxPower, false);

    SmartDashboard.putNumber("TargetPitch", pitch);

    SmartDashboard.putNumber("TargetYaw", yaw);
    SmartDashboard.putNumber("deltaX", deltaX);
    SmartDashboard.putNumber("deltaZzzz", target.getZ() - RobotMap.SHOOTER_HEIGHT);
    SmartDashboard.putNumber("deltaY", deltaY);

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
