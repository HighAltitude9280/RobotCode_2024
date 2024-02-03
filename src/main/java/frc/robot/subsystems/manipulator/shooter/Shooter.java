// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.manipulator.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HighAltitudeConstants;
import frc.robot.RobotMap;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;

public class Shooter extends SubsystemBase {
  HighAltitudeMotorGroup shooterMotors;

  /** Creates a new Shooter. */
  public Shooter() {
    shooterMotors = new HighAltitudeMotorGroup(RobotMap.SHOOTER_MOTOR_PORTS, RobotMap.SHOOTER_INVERTED_MOTORS_PORTS,
     RobotMap.SHOOTER_MOTOR_TYPES);
     
     shooterMotors.setEncoderInverted(RobotMap.SHOOTER_ENCODER_IS_INVERTED);
     shooterMotors.setBrakeMode(HighAltitudeConstants.SHOOTER_MOTORS_BRAKING_MODE);
  }

  public void driveShooter(double speed) {
    shooterMotors.setAll(speed);
  }

  public void stopShooter() {
    shooterMotors.setAll(0);
  }

  public void driveRollers(double speed) {
    shooterMotors.setSpecificMotorSpeed(16, speed);
  }

  public void stopRollers() {
    shooterMotors.setSpecificMotorSpeed(16, 0);
  }

  public void driveTop(double speed) {
    shooterMotors.setSpecificMotorSpeed(15, speed);
  }

  public void stopTop() {
    shooterMotors.setSpecificMotorSpeed(15, 0);
  }

  public void driveBottom(double speed) {
    shooterMotors.setSpecificMotorSpeed(14, speed);
  }
  
  public void stopBottom() {
    shooterMotors.setSpecificMotorSpeed(14, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
