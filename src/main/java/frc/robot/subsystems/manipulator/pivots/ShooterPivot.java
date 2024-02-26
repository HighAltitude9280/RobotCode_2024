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
  double ShooterPivotEncoderPosition, shooterPivotPositionDegrees, shooterPivotRawEncoder;
  double currentTarget;

  DigitalInput topLimitSwitch;
  DigitalInput bottomLimitSwitch;

  CANcoder absoluteEncoderController;

  double zeroValue = 0;

  boolean Override;

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
      bottomLimitSwitch = new DigitalInput(RobotMap.SHOOTER_PIVOT_BOTTOM_LIMIT_SWITCH_PORT);
    }

    currentTarget = getShooterPivotPositionInDegres();

    // TODO: Alex S: puse el override
    Override = true;
  }

  public double getAbsoluteEncoderDeg() {
    double angle = (getShooterPivotEncoderPosition()
        - RobotMap.SHOOTER_PIVOT_ENCODER_OFFSET_PULSES) * 360;
    return angle
        * (RobotMap.SHOOTER_PIVOT_ENCODED_TALON_INVERTED ? -1.0 : 1.0);
  }

  public void driveShooterPivot(double speed) {

    if (Override == false) {
      if (shooterPivotPositionDegrees > HighAltitudeConstants.SHOOTER_PIVOT_UPPER_LIMIT && speed > 0) {
        shooterPivotMotors.setAll(0);
      } else if (shooterPivotPositionDegrees < HighAltitudeConstants.SHOOTER_PIVOT_LOWER_LIMIT && speed < 0) {
        shooterPivotMotors.setAll(0);
      } else {
        shooterPivotMotors.setAll(speed);
      }
    } else {
      shooterPivotMotors.setAll(speed);
    }
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

  public void resetMotorEncoders() {
    shooterPivotMotors.resetEncoder();
  }

  public double getShooterPivotPositionInDegres() {
    return shooterPivotPositionDegrees;
  }

  public double getCurrentTarget() {
    return currentTarget;
  }

  public void setCurrentTarget(double Target) {
    currentTarget = Target;
  }

  public boolean getOverride() {
    return Override;
  }

  public void toggleOverride() {
    Override = !Override;
  }

  public void resetCanCoder() {
    zeroValue = absoluteEncoderController.getPosition().getValueAsDouble();
  }

  public double getShooterPivotRawEncoder() {
    return shooterPivotRawEncoder;
  }

  public double getShooterPivotEncoderPosition() {
    return ShooterPivotEncoderPosition;
  }

  @Override
  public void periodic() {
    ShooterPivotEncoderPosition = absoluteEncoderController.getAbsolutePosition().getValueAsDouble() - zeroValue;
    shooterPivotPositionDegrees = getAbsoluteEncoderDeg();
    shooterPivotRawEncoder = absoluteEncoderController.getPosition().getValueAsDouble();

    SmartDashboard.putNumber("Shooter Pivot Raw Abs Encoder", getShooterPivotRawEncoder());
    SmartDashboard.putNumber("Shooter Pivot Encoder Position", getShooterPivotEncoderPosition());
    SmartDashboard.putBoolean("Shooter_Override", Override);
  }
}
