// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.HighAltitudeConstants;
import frc.robot.Robot;
import frc.robot.commands.manipulator.intake.IntakeOut;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotMaintainTarget;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.ShooterPivotSetAngleTarget;

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
        .pathfindToPose(HighAltitudeConstants.AMP_POS);

    // ParallelCommandGroup shooterPos = new ParallelCommandGroup(
    // new ShooterPivotMaintainTarget(-30, 0.5));
    // SequentialCommandGroup group = command.andThen(shooterPos);

    command.schedule();

  }
}
