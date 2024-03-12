// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.Robot;
import frc.robot.subsystems.climber.Climber;

public class MaintainClimberPosition extends Command {
  Climber climber;

  /** Creates a new MaintainPosition. */
  public MaintainClimberPosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    //climber = Robot.getRobotContainer().getClimber();
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Recuerda que esto solo se ejecuta si el motor es un NEO.
    climber.maintainPosition();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.driveClimber(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  // siga ejecutandose incluso cuando esté en disable :0
  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
