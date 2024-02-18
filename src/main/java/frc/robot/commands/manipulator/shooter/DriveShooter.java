// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.manipulator.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.resources.joysticks.HighAltitudeJoystick.ButtonType;
import frc.robot.subsystems.manipulator.shooter.Shooter;

public class DriveShooter extends Command {
  Shooter shooter;
  boolean SuperShoot;

  /** Creates a new DriveShooter. */
  public DriveShooter() {
    shooter = Robot.getRobotContainer().getShooter();
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SuperShoot = OI.getInstance().getCopilot().getButtonObj(ButtonType.X).getAsBoolean();
    if (SuperShoot == true) {
      shooter.driveTop(1);
      shooter.driveBottom(1);

      shooter.driveRollers(OI.getInstance().getDeafultShooterDriveSpeed() * 0.5);
    } else {
      shooter.driveShooter(OI.getInstance().getDeafultShooterDriveSpeed());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.driveShooter(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
