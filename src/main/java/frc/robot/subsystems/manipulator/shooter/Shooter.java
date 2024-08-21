// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.manipulator.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HighAltitudeConstants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.resources.components.speedController.HighAltitudeMotorGroup;
import frc.robot.resources.math.Math;

public class Shooter extends SubsystemBase {
  HighAltitudeMotorGroup shooterUpMotors;
  HighAltitudeMotorGroup shooterDownMotors;
  HighAltitudeMotorGroup indexerMotors;
  private double currentRPMPowerTop;
  private double currentRPMPowerBottom;

  private boolean rpmOnTarget = false;

  private AnalogInput proximitySensor;

  private PIDController topPidController;
  private PIDController bottomPidController;
  private SimpleMotorFeedforward topFeedforward;
  private SimpleMotorFeedforward bottomFeedforward;

  /** Creates a new Shooter. */
  public Shooter() {
    shooterUpMotors = new HighAltitudeMotorGroup(RobotMap.SHOOTER_UP_MOTOR_PORTS,
        RobotMap.SHOOTER_UP__INVERTED_MOTORS_PORTS,
        RobotMap.SHOOTER_UP_MOTOR_TYPES);

    shooterDownMotors = new HighAltitudeMotorGroup(RobotMap.SHOOTER_DOWN_MOTOR_PORTS,
        RobotMap.SHOOTER_DOWN__INVERTED_MOTORS_PORTS,
        RobotMap.SHOOTER_DOWN_MOTOR_TYPES);

    indexerMotors = new HighAltitudeMotorGroup(RobotMap.SHOOTER_INDEXER_MOTOR_PORTS,
        RobotMap.SHOOTER_INDEXER__INVERTED_MOTORS_PORTS,
        RobotMap.SHOOTER_INDEXER_MOTOR_TYPES);

    shooterUpMotors.setEncoderInverted(RobotMap.SHOOTER_UP__ENCODER_IS_INVERTED);
    shooterDownMotors.setEncoderInverted(RobotMap.SHOOTER_DOWN__ENCODER_IS_INVERTED);

    shooterUpMotors.setBrakeMode(HighAltitudeConstants.SHOOTER_MOTORS_BRAKING_MODE);
    shooterDownMotors.setBrakeMode(HighAltitudeConstants.SHOOTER_MOTORS_BRAKING_MODE);
    proximitySensor = new AnalogInput(RobotMap.SHOOTER_PROXIMITY_SENSOR_PORT);

    topFeedforward = new SimpleMotorFeedforward(HighAltitudeConstants.SHOOTER__TOP_kS,
        HighAltitudeConstants.SHOOTER__TOP_kV);

    topPidController = new PIDController(HighAltitudeConstants.SHOOTER_TOP_kP,
        0, HighAltitudeConstants.SHOOTER_TOP_kD);

    bottomFeedforward = new SimpleMotorFeedforward(HighAltitudeConstants.SHOOTER_BOTTOM_kS,
        HighAltitudeConstants.SHOOTER_BOTTOM_kV);

    bottomPidController = new PIDController(HighAltitudeConstants.SHOOTER_BOTTOM_kP,
        0, HighAltitudeConstants.SHOOTER_BOTTOM_kD);
  }

  public void driveShooter(double speed) {
    shooterUpMotors.setAll(speed);
    shooterDownMotors.setAll(speed);
  }

  public void stopShooter() {
    shooterUpMotors.setAll(0);
    shooterDownMotors.setAll(0);
  }

  public void driveRollers(double speed) {
    indexerMotors.setAll(speed);
  }

  public void rollersOut() {
    indexerMotors.setAll(0.7);
  }

  /**
   * Drives the rollers out if the shooter is on its RPM target.
   */
  public void autoRollersOutRPMTarget() {
    if (onRPMTarget())
      rollersOut();
  }

  public void stopRollers() {
    indexerMotors.setAll(0);
  }

  public void driveTop(double speed) {
    shooterUpMotors.setAll(speed);
  }

  public void stopTop() {
    shooterUpMotors.setAll(0);
  }

  public void driveBottom(double speed) {
    shooterDownMotors.setAll(speed);
  }

  public void stopBottom() {
    shooterDownMotors.setAll(0);
  }

  public double getShooterTopVel() {
    return shooterUpMotors.getEncoderVelocity();
  }

  public double getShooterBottomVel() {
    return shooterDownMotors.getEncoderVelocity();
  }

  public boolean shooterDriveRPM(int rpm) {
    double deltaTop = rpm - getShooterTopVel();
    double deltaBottom = rpm - getShooterBottomVel();
    this.currentRPMPowerTop += deltaTop * HighAltitudeConstants.SHOOTER_RPM_STEP;
    this.currentRPMPowerBottom += deltaBottom * HighAltitudeConstants.SHOOTER_RPM_STEP;

    driveTop(currentRPMPowerTop);
    driveBottom(currentRPMPowerBottom);

    SmartDashboard.putNumber(" Shooter Drive RPM Top Power", currentRPMPowerTop);
    SmartDashboard.putNumber(" Shooter Drive RPM Bottom Power", currentRPMPowerBottom);

    rpmOnTarget = (Math.abs(deltaBottom) <= HighAltitudeConstants.SHOOTER_ON_TARGET
        && Math.abs(deltaTop) <= HighAltitudeConstants.SHOOTER_ON_TARGET);

    return rpmOnTarget;
  }

  public boolean controlShooter(int rpm) {
    double topOutput = topFeedforward.calculate(rpm);
    topOutput += topPidController.calculate(getShooterTopVel(), rpm);
    driveTop(topOutput);

    double bottomOutput = bottomFeedforward.calculate(rpm);
    bottomOutput += bottomPidController.calculate(getShooterBottomVel(), rpm);
    driveBottom(bottomOutput);

    double deltaTop = rpm - getShooterTopVel();
    double deltaBottom = rpm - getShooterBottomVel();

    rpmOnTarget = (Math.abs(deltaBottom) <= HighAltitudeConstants.SHOOTER_ON_TARGET
        && Math.abs(deltaTop) <= HighAltitudeConstants.SHOOTER_ON_TARGET);

    SmartDashboard.putBoolean("Shooter onTarget", Math.abs(deltaTop) <= HighAltitudeConstants.SHOOTER_ON_TARGET);
    return rpmOnTarget;
  }

  public boolean driveRPMToSpeaker() {
    return shooterDriveRPM(distanceToRPM(Robot.getRobotContainer().getSwerveDriveTrain().distanceToSpeaker()));
  }

  public void shootToSpeakerAutoRollers() {
    if (driveRPMToSpeaker())
      rollersOut();
  }

  public void setRPMPower(double power) {
    currentRPMPowerTop = power;
    currentRPMPowerBottom = power;
  }

  public boolean hasNote() {
    return proximitySensor.getAverageValue() > 1000;
  }

  public boolean onRPMTarget() {
    return rpmOnTarget;
  }

  /**
   * Converts distance (in meters) to ideal RPM shooting power. This should be
   * mapped at each event with the actual field.
   * 
   * @param distance
   * @return
   */
  public static int distanceToRPM(double distance) {
    return 3000;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Top Velocity", getShooterTopVel());
    SmartDashboard.putNumber("Shooter Bottom Velocity", getShooterBottomVel());
    SmartDashboard.putNumber("Proximity", proximitySensor.getAverageValue());

    SmartDashboard.putBoolean("ShooterHasNote", hasNote());

  }
}
