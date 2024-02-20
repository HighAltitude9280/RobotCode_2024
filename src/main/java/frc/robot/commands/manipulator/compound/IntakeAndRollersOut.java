// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.compound;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.manipulator.intake.IntakeOut;
import frc.robot.commands.manipulator.shooter.RollersOut;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeAndRollersOut extends ParallelCommandGroup {
  /** Creates a new IntakeAndShooterOut. */
  public IntakeAndRollersOut() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new IntakeOut(),
      new RollersOut()
    );
  }
}
