// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;
import frc.robot.resources.components.speedController.HighAltitudeMotorController.TypeOfMotor;

public class Climber extends SubsystemBase {
  HighAltitudeMotorGroup climberMotor;
  double positionToMaintain;
  PIDController PIDController;

  /** Creates a new Climber. */
  public Climber() {
    climberMotor = new HighAltitudeMotorGroup(RobotMap.CLIMBER_MOTOR_PORTS, RobotMap.CLIMBER_INVERTED_MOTOR_PORTS,
        RobotMap.CLIMBER_MOTOR_TYPES);
    PIDController = new PIDController(0.1, 0.0, 0.0);
  }

  public void driveClimber(double speed) {
    climberMotor.setAll(speed);
  }

  public void maintainPosition() {
    // Esto solo se ejecuta si tenemos un motor brushless (aka un NEO)
    if (climberMotor.getSpecificMotor(0).getType() == TypeOfMotor.CAN_SPARK_BRUSHLESS) {
      double power = PIDController.calculate(climberMotor.getEncoderPosition(), positionToMaintain);
      climberMotor.setAll(power);
    } else
      climberMotor.setAll(0);
  }

  public void setPositionToMaintain(double newPositionToMaintain) {
    positionToMaintain = newPositionToMaintain;
  }

  public double getCurrentPosition() {
    return climberMotor.getEncoderPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
