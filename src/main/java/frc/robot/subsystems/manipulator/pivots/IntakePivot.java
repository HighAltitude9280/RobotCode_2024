// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.manipulator.pivots;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HighAltitudeConstants;
import frc.robot.RobotMap;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;

public class IntakePivot extends SubsystemBase {
  HighAltitudeMotorGroup intakePivotMotors;
  double currentIntakePivotEncoderPosition, intakePivotPositionDegrees;
  DigitalInput topLimitSwitch;
  DigitalInput bottomLimitSwitch;

  IntakePosition currentPosition;

  public enum IntakePosition {
    LOWERED, STORED
  }

  /** Creates a new IntakePivot. */
  public IntakePivot() {

    intakePivotMotors = new HighAltitudeMotorGroup(RobotMap.INTAKE_PIVOT_MOTOR_PORTS,
        RobotMap.INTAKE_PIVOT_INVERTED_MOTORS_PORTS,
        RobotMap.INTAKE_PIVOT_MOTOR_TYPES);

    intakePivotMotors.setBrakeMode(HighAltitudeConstants.INTAKE_PIVOT_MOTOR_BRAKING_MODE);
    intakePivotMotors.setEncoderInverted(RobotMap.INTAKE_PIVOT_ENCODER_IS_INVERTED);

    if (RobotMap.INTAKE_PIVOT_TOP_LIMIT_SWITCH_IS_AVAILABLE) {
      topLimitSwitch = new DigitalInput(RobotMap.INTAKE_PIVOT_TOP_LIMIT_SWITCH_PORT);
    }

    if (RobotMap.INTAKE_PIVOT_BOTTOM_LIMIT_SWITCH_IS_AVAILABLE) {
      topLimitSwitch = new DigitalInput(RobotMap.INTAKE_PIVOT_BOTTOM_LIMIT_SWITCH_PORT);
    }
  }

  public void driveIntakePivot(double speed) {
    intakePivotMotors.setAll(speed);
  }

  public boolean getIntakePivotTopLimitSwitch() {
    if (RobotMap.INTAKE_PIVOT_TOP_LIMIT_SWITCH_IS_AVAILABLE)
      return topLimitSwitch.get();
    return false;
  }

  public boolean getIntakePivotBottomLimitSwitch() {
    if (RobotMap.INTAKE_PIVOT_BOTTOM_LIMIT_SWITCH_IS_AVAILABLE)
      return bottomLimitSwitch.get();
    return false;
  }

  public void resetEncoders() {
    intakePivotMotors.resetEncoder();
  }

  public double getIntakePivotPositionInDegres() {
    return intakePivotPositionDegrees;
  }

  @Override
  public void periodic() {
    currentIntakePivotEncoderPosition = intakePivotMotors.getEncoderPosition();
    intakePivotPositionDegrees = currentIntakePivotEncoderPosition
        * HighAltitudeConstants.INTAKE_PIVOT_DEGREES_PER_REVOLUTION;

    SmartDashboard.putNumber("Raw Intake Pivot Encoder", intakePivotMotors.getEncoderPosition());
  }
}
