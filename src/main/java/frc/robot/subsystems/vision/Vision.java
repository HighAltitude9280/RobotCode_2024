// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.vision;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  PhotonCamera photonCamera;
  PhotonPipelineResult result;

  /** Creates a new vision. */
  public Vision() {
    photonCamera = new PhotonCamera("uwu");
    result = photonCamera.getLatestResult();
  }

  public boolean hasTargets() {
    return result.hasTargets();
  }

  public double getYaw() {
    return hasTargets() ? result.getBestTarget().getYaw() : 0;
  }

  @Override
  public void periodic() {
    result = photonCamera.getLatestResult();
    // System.out.println("getYaw: " + getYaw());
    // This method will be called once per scheduler run
  }
}
