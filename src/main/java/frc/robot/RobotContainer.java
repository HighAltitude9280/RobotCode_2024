// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Human_Drivers.HumanDrivers;
import frc.robot.commands.manipulator.shooter.DriveShooter;
import frc.robot.commands.swerve.DefaultSwerveDrive;
import frc.robot.resources.components.Navx;
import frc.robot.subsystems.manipulator.intake.Intake;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;
import frc.robot.subsystems.manipulator.shooter.Shooter;
import frc.robot.subsystems.swerve.SwerveDriveTrain;

/** Add your docs here. */
public class RobotContainer {

    private Navx navx;
    private SwerveDriveTrain swerveDriveTrain;
    private Intake intake;
    private Shooter shooter;
    private IntakePivot intakePivot;
    private ShooterPivot shooterPivot;

    public RobotContainer() {

        navx = new Navx();
        swerveDriveTrain = new SwerveDriveTrain();
        intake = new Intake();
        shooter = new Shooter();
        intakePivot = new IntakePivot();
        shooterPivot = new ShooterPivot();
    }

    public void ConfigureButtonBindings() {
        OI.getInstance().ConfigureButtonBindings();
        swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
        shooter.setDefaultCommand(new DriveShooter());
    }

    public Navx getNavx() {
        return navx;
    }

    public HumanDrivers getCurrentPilot() {
        return HighAltitudeConstants.CURRENT_PILOT;
    }

    public HumanDrivers getCurrentCopilot() {
        return HighAltitudeConstants.CURRENT_COPILOT;
    }

    public SwerveDriveTrain getSwerveDriveTrain() {
        return swerveDriveTrain;
    }

    public Intake getIntake() {
        return intake;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public IntakePivot getIntakePivot() {
        return intakePivot;
    }

    public ShooterPivot getShooterPivot() {
        return shooterPivot;
    }
}
