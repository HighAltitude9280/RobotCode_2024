// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.manipulator.pivots;

import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HighAltitudeConstants;
import frc.robot.RobotMap;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;

public class ShooterPivot extends SubsystemBase {
  HighAltitudeMotorGroup shooterPivotMotors;
  double currentShooterPivotEncoderPosition, shooterPivotPositionDegrees;

  DigitalInput topLimitSwitch;
  DigitalInput bottomLimitSwitch;

  CANcoder absoluteEncoderController;

  /** Creates a new ShooterPivot. */
  public ShooterPivot() {

    shooterPivotMotors = new HighAltitudeMotorGroup(RobotMap.SHOOTER_PIVOT_MOTOR_PORTS,
        RobotMap.SHOOTER_PIVOT_INVERTED_MOTORS_PORTS,
        RobotMap.SHOOTER_PIVOT_MOTOR_TYPES);

    shooterPivotMotors.setBrakeMode(HighAltitudeConstants.SHOOTER_PIVOT_MOTOR_BRAKING_MODE);
    shooterPivotMotors.setEncoderInverted(RobotMap.SHOOTER_PIVOT_ENCODER_IS_INVERTED);

    absoluteEncoderController = new CANcoder(RobotMap.SHOOTER_PIVOT_ENCODED_TALON_PORT);

    if (RobotMap.SHOOTER_PIVOT_TOP_LIMIT_SWITCH_IS_AVAILABLE) {
      topLimitSwitch = new DigitalInput(RobotMap.SHOOTER_PIVOT_TOP_LIMIT_SWITCH_PORT);
    }

    if (RobotMap.SHOOTER_PIVOT_BOTTOM_LIMIT_SWITCH_IS_AVAILABLE) {
      topLimitSwitch = new DigitalInput(RobotMap.SHOOTER_PIVOT_BOTTOM_LIMIT_SWITCH_PORT);
    }
  }

  public double getAbsoluteEncoderDeg() {
    double angle = absoluteEncoderController.getPosition().getValueAsDouble()
        - RobotMap.SHOOTER_PIVOT_ENCODER_OFFSET_PULSES;
    return angle * HighAltitudeConstants.SHOOTER_PIVOT_ABSOLUTE_ENCODER_DEGREES_PER_PULSE
        * (RobotMap.SHOOTER_PIVOT_ENCODED_TALON_INVERTED ? -1.0 : 1.0);
  }

  public void driveShooterPivot(double speed) {
    shooterPivotMotors.setAll(speed);
  }

  public boolean getShooterPivotTopLimitSwitch() {
    if (RobotMap.SHOOTER_PIVOT_TOP_LIMIT_SWITCH_IS_AVAILABLE) {
      return topLimitSwitch.get();
    } else {
      return false;
    }
  }

  public boolean getShooterPivotBottomLimitSwitch() {
    if (RobotMap.SHOOTER_PIVOT_BOTTOM_LIMIT_SWITCH_IS_AVAILABLE) {
      return bottomLimitSwitch.get();
    } else {
      return false;
    }
  }

  public void resetEncoders() {
    shooterPivotMotors.resetEncoder();
  }

  public double getShooterPivotPositionInDegres() {
    return shooterPivotPositionDegrees;
  }

  @Override
  public void periodic() {
    currentShooterPivotEncoderPosition = shooterPivotMotors.getEncoderPosition();
    shooterPivotPositionDegrees = currentShooterPivotEncoderPosition
        * HighAltitudeConstants.SHOOTER_PIVOT_DEGREES_PER_REVOLUTION;

    SmartDashboard.putNumber("Raw Shooter Pivot Encoder", shooterPivotMotors.getEncoderPosition());
    SmartDashboard.putNumber("Shooter Pivot Raw Abs Encoder",
        absoluteEncoderController.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Shooter Pivot Deegres", getAbsoluteEncoderDeg());
  }
}
