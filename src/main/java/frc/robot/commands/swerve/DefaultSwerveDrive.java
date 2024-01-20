// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.HighAltitudeConstants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.swerve.SwerveDriveTrain;

public class DefaultSwerveDrive extends Command {
  private SlewRateLimiter speedLimiter, strafeLimiter, turnLimiter;
  SwerveDriveTrain swerveDriveTrain;

  /** Creates a new DriveSwerve. */
  public DefaultSwerveDrive() {
    speedLimiter = new SlewRateLimiter(HighAltitudeConstants.SWERVE_MAX_ACCELERATION_UNITS_PER_SECOND);
    strafeLimiter = new SlewRateLimiter(HighAltitudeConstants.SWERVE_MAX_ACCELERATION_UNITS_PER_SECOND);
    turnLimiter = new SlewRateLimiter(HighAltitudeConstants.SWERVE_MAX_ANGULAR_ACCELERATION_UNITS_PER_SECOND);

    swerveDriveTrain = Robot.getRobotContainer().getSwerveDriveTrain();
    addRequirements(swerveDriveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // 1. Read input
    double speed = OI.getInstance().getDefaultSwerveDriveSpeed();
    double strafe = OI.getInstance().getDefaultSwerveDriveStrafe();
    double turn = OI.getInstance().getDefaultSwerveDriveTurn();

    // 2. Limit the inputs' acceleration as to make driving smoother
    speed = speedLimiter.calculate(speed);
    strafe = strafeLimiter.calculate(strafe);
    turn = turnLimiter.calculate(turn);

    // 3. Scale input to teleop max speed
    speed *= HighAltitudeConstants.SWERVE_DRIVE_TELEOP_MAX_SPEED_METERS_PER_SECOND;
    strafe *= HighAltitudeConstants.SWERVE_DRIVE_TELEOP_MAX_SPEED_METERS_PER_SECOND;
    turn *= HighAltitudeConstants.SWERVE_DIRECTION_TELEOP_MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;

    // 4. Construct the chassis speeds
    ChassisSpeeds chassisSpeeds;
    if (swerveDriveTrain.getIsFieldOriented()) {
      chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(speed, strafe, turn,
          swerveDriveTrain.getPose().getRotation());
    } else {
      chassisSpeeds = new ChassisSpeeds(speed, strafe, turn);
    }

    // 5. Set the states to the swerve modules
    SwerveModuleState[] moduleStates = HighAltitudeConstants.SWERVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
    swerveDriveTrain.setModuleStates(moduleStates);
/*
    6. Print for debugging
    Robot.debugNumberSmartDashboard("processed speed", speed);
    Robot.debugNumberSmartDashboard("processed strafe", strafe);
    Robot.debugNumberSmartDashboard("processed turn", turn);
    Robot.debugNumberSmartDashboard("FrontLeftSpeed",
    moduleStates[0].speedMetersPerSecond);
    Robot.debugNumberSmartDashboard("FrontLeftAngle",
    moduleStates[0].angle.getDegrees());
    Robot.debugNumberSmartDashboard("FrontRightSpeed",
    moduleStates[1].speedMetersPerSecond);
    Robot.debugNumberSmartDashboard("FrontRightAngle",
    moduleStates[1].angle.getDegrees());
    Robot.debugNumberSmartDashboard("BackLeftSpeed",
    moduleStates[2].speedMetersPerSecond);
    Robot.debugNumberSmartDashboard("BackLeftAngle",
    moduleStates[2].angle.getDegrees());
    Robot.debugNumberSmartDashboard("BackRightSpeed",
    moduleStates[3].speedMetersPerSecond);
    Robot.debugNumberSmartDashboard("BackRightAngle",
    moduleStates[3].angle.getDegrees());
*/
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerveDriveTrain.stopModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
