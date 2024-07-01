// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.vision;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  PhotonCamera photonCamera;
  PhotonPipelineResult result;

  PhotonPoseEstimator poseEstimator;
  EstimatedRobotPose estimatedPose;

  /** Creates a new vision. */
  public Vision() {
    photonCamera = new PhotonCamera("TengoHambre");
    AprilTagFieldLayout fieldLayout;

    try {
      Transform3d cam = new Transform3d(new Translation3d(-0.33, 0, 0.47), new Rotation3d(0f, 0f, Math.PI));
      fieldLayout = new AprilTagFieldLayout(
          "/home/lvuser/deploy/vision/CustomAprilTagFieldLayout.json");
      poseEstimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, photonCamera,
          cam);
    } catch (IOException e) {
      e.printStackTrace();
    }

    result = photonCamera.getLatestResult();
  }

  public boolean hasTargets() {
    return result.hasTargets();
  }

  public double getYaw() {
    return hasTargets() ? result.getBestTarget().getYaw() : 0;
  }

  public double getPitch() {
    return hasTargets() ? result.getBestTarget().getPitch() : 0;
  }

  public double getArea() {
    return hasTargets() ? result.getBestTarget().getArea() : 0;
  }

  public EstimatedRobotPose getEstimatedPosition() {

    return estimatedPose;

  }

  @Override
  public void periodic() {
    result = photonCamera.getLatestResult();

    Optional<EstimatedRobotPose> pose = poseEstimator.update();
    if (pose.isPresent()) {
      estimatedPose = pose.get();
      SmartDashboard.putNumber("Vision Pose X", estimatedPose.estimatedPose.getX());
      SmartDashboard.putNumber("Vision Pose Y", estimatedPose.estimatedPose.getY());
    }
    // System.out.println("getYaw: " + getYaw());
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Pitch", getPitch());
    SmartDashboard.putNumber("Yaw", getYaw());
    SmartDashboard.putBoolean("Targets", result.hasTargets());

  }
}
