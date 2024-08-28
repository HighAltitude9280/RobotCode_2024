// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.compound;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.manipulator.intake.IntakeUntilNote;
import frc.robot.commands.manipulator.intake.StopIntake;
import frc.robot.commands.manipulator.pivots.positions.IntakePivotRetractar;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotMaintainTarget;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.ShooterPivotSetAngleTarget;
import frc.robot.commands.manipulator.shooter.StopRollers;
import frc.robot.commands.manipulator.shooter.TransitionUntilNote;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeAutoTransition extends SequentialCommandGroup {
  /** Creates a new IntakeAutoTransition. */
  public IntakeAutoTransition() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ParallelRaceGroup(new IntakeUntilNote(),
        new ShooterPivotMaintainTarget(35, 0.5)),
        new ParallelRaceGroup(new IntakePivotRetractar(0.5), new ShooterPivotMaintainTarget(0.5)),
        new TransitionUntilNote());
  }
}
