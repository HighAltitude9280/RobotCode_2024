// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static RobotContainer robotContainer;
  private Command m_autonomousCommand;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    getRobotContainer().ConfigureButtonBindings();

    getRobotContainer().getShooterPivot().resetCanCoder();
    getRobotContainer().getIntakePivot().resetEncoders();

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("Intake Pivot Degrees", robotContainer.getIntakePivot().getIntakePivotPositionInDegres());
    SmartDashboard.putNumber("Shooter Pivot Degrees",
        robotContainer.getShooterPivot().getShooterPivotPositionInDegres());

    SmartDashboard.putString("Pilot", robotContainer.getCurrentPilot().toString());
    SmartDashboard.putString("Copilot", robotContainer.getCurrentCopilot().toString());

    SmartDashboard.putBoolean("Field Oriented", robotContainer.getSwerveDriveTrain().getIsFieldOriented());

    SmartDashboard.putNumber("Drive Distance FL",
        robotContainer.getSwerveDriveTrain().getFrontLeft().getDriveDistance());
    SmartDashboard.putNumber("Drive Distance FR",
        robotContainer.getSwerveDriveTrain().getFrontRight().getDriveDistance());
    SmartDashboard.putNumber("Drive Distance BL",
        robotContainer.getSwerveDriveTrain().getBackLeft().getDriveDistance());
    SmartDashboard.putNumber("Drive Distance BR",
        robotContainer.getSwerveDriveTrain().getBackRight().getDriveDistance());

    SmartDashboard.putNumber("Odometry X", robotContainer.getSwerveDriveTrain().getPose().getX());
    SmartDashboard.putNumber("Odometry Y", robotContainer.getSwerveDriveTrain().getPose().getY());
    SmartDashboard.putNumber("Odometry angle",
        robotContainer.getSwerveDriveTrain().getPose().getRotation().getDegrees());

  }

  @Override
  public void autonomousInit() {
    getRobotContainer().getSwerveDriveTrain().setModulesBrakeMode(true);
    m_autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous comand (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    getRobotContainer().getSwerveDriveTrain().setModulesBrakeMode(true);
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void disabledInit() {
    getRobotContainer().generateAutos();
  }

  @Override
  public void disabledPeriodic() {
    getRobotContainer().putAutoChooser();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }

  public static RobotContainer getRobotContainer() {
    return robotContainer;
  }

  public static void putNumberInSmartDashboard(String name, double number) {
    SmartDashboard.putNumber(name, number);
  }
}
