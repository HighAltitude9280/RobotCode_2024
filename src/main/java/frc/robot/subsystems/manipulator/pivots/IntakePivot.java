// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.manipulator.pivots;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HighAltitudeConstants;
import frc.robot.RobotMap;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;

public class IntakePivot extends SubsystemBase {
  HighAltitudeMotorGroup intakePivotMotors;
  double currentIntakePivotEncoderPosition, intakePivotPositionDegrees;
  DigitalInput topLimitSwitch;
  DigitalInput bottomLimitSwitch;

  /** Creates a new IntakePivot. */
  public IntakePivot() {

    intakePivotMotors = new HighAltitudeMotorGroup(RobotMap.INTAKE_PIVOT_MOTOR_PORTS, RobotMap.INTAKE_PIVOT_INVERTED_MOTORS_PORTS,
     RobotMap.INTAKE_PIVOT_MOTOR_TYPES);

    intakePivotMotors.setBrakeMode(HighAltitudeConstants.INTAKE_PIVOT_MOTOR_BRAKING_MODE);

    if (RobotMap.INTAKE_PIVOT_TOP_LIMIT_SWITCH_IS_AVAILABLE){
      topLimitSwitch = new DigitalInput(RobotMap.INTAKE_PIVOT_TOP_LIMIT_SWITCH_PORT);
    }

    if (RobotMap.INTAKE_PIVOT_BOTTOM_LIMIT_SWITCH_IS_AVAILABLE){
      topLimitSwitch = new DigitalInput(RobotMap.INTAKE_PIVOT_BOTTOM_LIMIT_SWITCH_PORT);
    }
  }

  public void driveIntakePivot(double speed){
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

  @Override
  public void periodic() {
    currentIntakePivotEncoderPosition = intakePivotMotors.getEncoderPosition();
    intakePivotPositionDegrees = currentIntakePivotEncoderPosition * HighAltitudeConstants.INTAKE_PIVOT_DEGREES_PER_PULSE;
  }
}
