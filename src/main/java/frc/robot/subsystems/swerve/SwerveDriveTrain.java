// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.swerve;

import java.util.ArrayList;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.PPLibTelemetry;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.HighAltitudeConstants;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SwerveDriveTrain extends SubsystemBase {
  private HighAltitudeSwerveModule frontLeft, frontRight, backLeft, backRight;
  ArrayList<HighAltitudeSwerveModule> modules;

  private SwerveDrivePoseEstimator swerveDrivePoseEstimator;

  private boolean isSlower = false;
  private boolean isFieldOriented = true;
  private boolean isOnCompetitiveField = false;

  private Field2d field = new Field2d();

  /** Creates a new SwerveDrive. */
  public SwerveDriveTrain() {
    frontLeft = new HighAltitudeSwerveModule(
        RobotMap.SWERVE_FRONT_LEFT_DRIVE_MOTOR_PORT,
        RobotMap.SWERVE_FRONT_LEFT_DRIVE_MOTOR_TYPE,
        RobotMap.SWERVE_FRONT_LEFT_DRIVE_MOTOR_INVERTED,
        RobotMap.SWERVE_FRONT_LEFT_DRIVE_ENCODER_INVERTED,
        RobotMap.SWERVE_FRONT_LEFT_DIRECTION_MOTOR_PORT,
        RobotMap.SWERVE_FRONT_LEFT_DIRECTION_MOTOR_TYPE,
        RobotMap.SWERVE_FRONT_LEFT_DIRECTION_MOTOR_INVERTED,
        RobotMap.SWERVE_FRONT_LEFT_DIRECTION_ENCODER_INVERTED,
        RobotMap.SWERVE_FRONT_LEFT_ENCODED_TALON_PORT,
        RobotMap.SWERVE_FRONT_LEFT_DIRECTION_ENCODER_OFFSET_PULSES,
        RobotMap.SWERVE_FRONT_LEFT_ENCODED_TALON_INVERTED);
    frontRight = new HighAltitudeSwerveModule(
        RobotMap.SWERVE_FRONT_RIGHT_DRIVE_MOTOR_PORT,
        RobotMap.SWERVE_FRONT_RIGHT_DRIVE_MOTOR_TYPE,
        RobotMap.SWERVE_FRONT_RIGHT_DRIVE_MOTOR_INVERTED,
        RobotMap.SWERVE_FRONT_RIGHT_DRIVE_ENCODER_INVERTED,
        RobotMap.SWERVE_FRONT_RIGHT_DIRECTION_MOTOR_PORT,
        RobotMap.SWERVE_FRONT_RIGHT_DIRECTION_MOTOR_TYPE,
        RobotMap.SWERVE_FRONT_RIGHT_DIRECTION_MOTOR_INVERTED,
        RobotMap.SWERVE_FRONT_RIGHT_DIRECTION_ENCODER_INVERTED,
        RobotMap.SWERVE_FRONT_RIGHT_ENCODED_TALON_PORT,
        RobotMap.SWERVE_FRONT_RIGHT_DIRECTION_ENCODER_OFFSET_PULSES,
        RobotMap.SWERVE_FRONT_RIGHT_ENCODED_TALON_INVERTED);
    backLeft = new HighAltitudeSwerveModule(
        RobotMap.SWERVE_BACK_LEFT_DRIVE_MOTOR_PORT,
        RobotMap.SWERVE_BACK_LEFT_DRIVE_MOTOR_TYPE,
        RobotMap.SWERVE_BACK_LEFT_DRIVE_MOTOR_INVERTED,
        RobotMap.SWERVE_BACK_LEFT_DRIVE_ENCODER_INVERTED,
        RobotMap.SWERVE_BACK_LEFT_DIRECTION_MOTOR_PORT,
        RobotMap.SWERVE_BACK_LEFT_DIRECTION_MOTOR_TYPE,
        RobotMap.SWERVE_BACK_LEFT_DIRECTION_MOTOR_INVERTED,
        RobotMap.SWERVE_BACK_LEFT_DIRECTION_ENCODER_INVERTED,
        RobotMap.SWERVE_BACK_LEFT_ENCODED_TALON_PORT,
        RobotMap.SWERVE_BACK_LEFT_DIRECTION_ENCODER_OFFSET_PULSES,
        RobotMap.SWERVE_BACK_LEFT_ENCODED_TALON_INVERTED);
    backRight = new HighAltitudeSwerveModule(
        RobotMap.SWERVE_BACK_RIGHT_DRIVE_MOTOR_PORT,
        RobotMap.SWERVE_BACK_RIGHT_DRIVE_MOTOR_TYPE,
        RobotMap.SWERVE_BACK_RIGHT_DRIVE_MOTOR_INVERTED,
        RobotMap.SWERVE_BACK_RIGHT_DRIVE_ENCODER_INVERTED,
        RobotMap.SWERVE_BACK_RIGHT_DIRECTION_MOTOR_PORT,
        RobotMap.SWERVE_BACK_RIGHT_DIRECTION_MOTOR_TYPE,
        RobotMap.SWERVE_BACK_RIGHT_DIRECTION_MOTOR_INVERTED,
        RobotMap.SWERVE_BACK_RIGHT_DIRECTION_ENCODER_INVERTED,
        RobotMap.SWERVE_BACK_RIGHT_ENCODED_TALON_PORT,
        RobotMap.SWERVE_BACK_RIGHT_DIRECTION_ENCODER_OFFSET_PULSES,
        RobotMap.SWERVE_BACK_RIGHT_ENCODED_TALON_INVERTED);

    modules = new ArrayList<HighAltitudeSwerveModule>();
    modules.add(frontLeft);
    modules.add(frontRight);
    modules.add(backLeft);
    modules.add(backRight);

    swerveDrivePoseEstimator = new SwerveDrivePoseEstimator(HighAltitudeConstants.SWERVE_KINEMATICS, new Rotation2d(0),
        new SwerveModulePosition[] {
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        }, new Pose2d(0.0, 0.0, new Rotation2d(0)));

    // Configure AutoBuilder
    AutoBuilder.configureHolonomic(
        this::getPose,
        this::resetPose,
        this::getChassisSpeeds,
        this::driveRobotRelative,
        HighAltitudeConstants.pathFollowerConfig,
        () -> {
          // Boolean supplier that controls when the path will be mirrored for the red
          // alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        },
        this);

    // Set up custom logging to add the current path to a field 2d widget
    PathPlannerLogging.setLogActivePathCallback((poses) -> field.getObject("path").setPoses(poses));
    SmartDashboard.putData("Field", field);
  }

  // By default, the Navx reports its angle as increasing when turning to its
  // right, but many wpilib functions consider the angle as increasing when moving
  // to the left (Counter Clock-Wise, or CCW).
  // The example I saw uses the raw yaw reported by the navx and switches the
  // position of the left and right wheels in the kinematics.
  // Upon testing with simulation, it turns out both wheels and navx need to work
  // with CW-increasing angles.

  public double getHeading() {
    return Robot.getRobotContainer().getNavx().getYaw();
  }

  public double getHeadingCCWPositive() {
    return -Robot.getRobotContainer().getNavx().getYaw();
  }

  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(getHeading());
  }

  public Rotation2d getRotation2dCCWPositive() {
    return Rotation2d.fromDegrees(getHeadingCCWPositive());
  }

  public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
    ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);

    SwerveModuleState[] targetStates = HighAltitudeConstants.SWERVE_KINEMATICS.toSwerveModuleStates(targetSpeeds);
    setModuleStates(targetStates);
  }

  // Controlling modules

  public void setModuleStates(SwerveModuleState[] states) {
    SwerveDriveKinematics.desaturateWheelSpeeds(states, HighAltitudeConstants.SWERVE_DRIVE_MAX_SPEED_METERS_PER_SECOND);
    frontLeft.setState(states[0]);
    frontRight.setState(states[1]);
    backLeft.setState(states[2]);
    backRight.setState(states[3]);
  }

  public void stopModules() {
    frontLeft.stop();
    frontRight.stop();
    backLeft.stop();
    backRight.stop();
  }

  public void setModulesInXPosition() {
    frontLeft.setStateRegardlessOfSpeed(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    frontRight.setStateRegardlessOfSpeed(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    backLeft.setStateRegardlessOfSpeed(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    backRight.setStateRegardlessOfSpeed(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
  }

  public void recalculateModuleDirections() {
    frontLeft.recalculateWheelDirection();
    frontRight.recalculateWheelDirection();
    backLeft.recalculateWheelDirection();
    backRight.recalculateWheelDirection();
  }

  // Odometry

  public void updateOdometry() {
    swerveDrivePoseEstimator.update(
        getRotation2dCCWPositive(),
        new SwerveModulePosition[] {
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition() });
  }

  public void addVisionMeasurement(Pose2d visionMeasurement, double timeStampSeconds) {
    swerveDrivePoseEstimator.addVisionMeasurement(visionMeasurement, timeStampSeconds);
  }

  public Pose2d getPose() {
    return swerveDrivePoseEstimator.getEstimatedPosition();
  }

  public void resetPose(Pose2d pose) {
    swerveDrivePoseEstimator.resetPosition(getRotation2dCCWPositive(), new SwerveModulePosition[] {
        frontLeft.getPosition(),
        frontRight.getPosition(),
        backLeft.getPosition(),
        backRight.getPosition() }, pose);
  }

  // Getters for the modules

  public HighAltitudeSwerveModule getFrontLeft() {
    return frontLeft;
  }

  public HighAltitudeSwerveModule getFrontRight() {
    return frontRight;
  }

  public HighAltitudeSwerveModule getBackLeft() {
    return backLeft;
  }

  public HighAltitudeSwerveModule getBackRight() {
    return backRight;
  }

  public ArrayList<HighAltitudeSwerveModule> getModules() {
    return modules;
  }

  // Parameters getters and setters

  public boolean getIsSlower() {
    return isSlower;
  }

  public void toggleIsSlower() {
    isSlower = !isSlower;
  }

  public void setIsSlower(boolean shouldBeSlower) {
    isSlower = shouldBeSlower;
  }

  public boolean getIsFieldOriented() {
    return isFieldOriented;
  }

  public void toggleFieldOriented() {
    isFieldOriented = !isFieldOriented;
  }

  public void setIsFieldOriented(boolean shouldBeFieldOriented) {
    isFieldOriented = shouldBeFieldOriented;
  }

  public void setModulesBrakeMode(boolean doBrake) {
    for (HighAltitudeSwerveModule module : modules) {
      module.getDriveMotor().setBrakeMode(doBrake);
      module.getDirectionMotor().setBrakeMode(doBrake);
      System.out.println("BrakeMode: " + doBrake);
    }
  }

  public ChassisSpeeds getChassisSpeeds() {
    return HighAltitudeConstants.SWERVE_KINEMATICS.toChassisSpeeds(
        // supplier for chassisSpeed, order of motors need to be the same as the
        // consumer of ChassisSpeed
        frontLeft.getState(),
        backLeft.getState(),
        frontRight.getState(),
        backRight.getState());
  }

  // see drive constants for details
  public void setChassisSpeeds(ChassisSpeeds chassisSpeeds) {
    setModuleStates(
        HighAltitudeConstants.SWERVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds));
  }

  public boolean getIsOnCompetitiveField() {
    return isOnCompetitiveField;
  }

  public void toggleIsOnCompetitiveField() {
    isOnCompetitiveField = !isOnCompetitiveField;
  }

  @Override
  public void periodic() {
    updateOdometry();
    putAllInfoInSmartDashboard();
  }

  public void putAllInfoInSmartDashboard() {
    /*
     * frontLeft.putProcessedValues("FL");
     * frontRight.putProcessedValues("FR");
     * backLeft.putProcessedValues("BL");
     * backRight.putProcessedValues("BR");
     * frontLeft.putEncoderValuesInvertedApplied("FL");
     * frontRight.putEncoderValuesInvertedApplied("FR");
     * backLeft.putEncoderValuesInvertedApplied("BL");
     * backRight.putEncoderValuesInvertedApplied("BR");
     * 
     * SmartDashboard.putNumber("FL Steer Power",
     * frontLeft.getDirectionMotor().getOutput());
     * SmartDashboard.putNumber("FR Steer Power",
     * frontRight.getDirectionMotor().getOutput());
     * SmartDashboard.putNumber("BL Steer Power",
     * backLeft.getDirectionMotor().getOutput());
     * SmartDashboard.putNumber("BR Steer Power",
     * backRight.getDirectionMotor().getOutput());
     * 
     * SmartDashboard.putNumber("FL Target",
     * frontLeft.getPIDController().getSetpoint());
     * SmartDashboard.putNumber("FL Current", frontLeft.getAbsoluteEncoderRad());
     * SmartDashboard.putNumber("FR Target",
     * frontRight.getPIDController().getSetpoint());
     * SmartDashboard.putNumber("FR Current", frontRight.getAbsoluteEncoderRad());
     * SmartDashboard.putNumber("BL Target",
     * backLeft.getPIDController().getSetpoint());
     * SmartDashboard.putNumber("BL Current", backLeft.getAbsoluteEncoderRad());
     * SmartDashboard.putNumber("BR Target",
     * backRight.getPIDController().getSetpoint());
     * SmartDashboard.putNumber("BR Current", backRight.getAbsoluteEncoderRad());
     */
    SmartDashboard.putNumber("Odometry X", swerveDrivePoseEstimator.getEstimatedPosition().getX());
    SmartDashboard.putNumber("Odometry Y", swerveDrivePoseEstimator.getEstimatedPosition().getY());
    SmartDashboard.putNumber("Odometry Degree",
        swerveDrivePoseEstimator.getEstimatedPosition().getRotation().getDegrees());

  }
}
