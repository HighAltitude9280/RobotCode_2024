// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.manipulator.shooter.ShooterAmp;
import frc.robot.commands.manipulator.shooter.ShooterDriveRPM;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoAmp extends InstantCommand {
  public AutoAmp() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    Command command = Robot.getRobotContainer().getSwerveDriveTrain()
        .onTheFlyTrajectory(new Pose2d(new Translation2d(2.66, 0.565), new Rotation2d(Math.PI / 2)));

    SequentialCommandGroup group = command.andThen(new ShooterAmp());
    CommandScheduler.getInstance().schedule(group);

  }
}
