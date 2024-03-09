// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.manipulator.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;

public class Intake extends SubsystemBase {
  HighAltitudeMotorGroup intakeMotors;
  ColorSensorV3 intakeColorSensor;

  /** Creates a new Intake. */
  public Intake() {

    intakeMotors = new HighAltitudeMotorGroup(RobotMap.INTAKE_MOTOR_PORTS, RobotMap.INTAKE_INVERTED_MOTORS_PORTS,
        RobotMap.INTAKE_MOTOR_TYPES);

    intakeColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  }

  public void driveIntake(double speed) {
    intakeMotors.setAll(speed);
  }

  public void Hold() {
    intakeMotors.setAll(0.05);
  }

  public void stopIntake() {
    intakeMotors.setAll(0);
  }

  public RawColor detectedColor() {
    return intakeColorSensor.getRawColor();
  }

  public double getDetectedColorRed() {
    return intakeColorSensor.getRawColor().red;
  }

  public int getProximity() {
    return intakeColorSensor.getProximity();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Detected Color", detectedColor().red);
    SmartDashboard.putNumber("Proximity", getProximity());
    // This method will be called once per scheduler run
  }
}