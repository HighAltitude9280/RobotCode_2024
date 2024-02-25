// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Human_Drivers.HumanDrivers;
/*import frc.robot.commands.manipulator.pivots.positions.ShooterPivotKeepCurrentPosition;*/
import frc.robot.commands.manipulator.shooter.DriveShooter;
import frc.robot.commands.swerve.DefaultSwerveDrive;
import frc.robot.resources.components.Navx;
import frc.robot.resources.components.PWMLEDStrip.LEDs;
import frc.robot.subsystems.manipulator.intake.Intake;
import frc.robot.subsystems.manipulator.pivots.IntakePivot;
import frc.robot.subsystems.manipulator.pivots.ShooterPivot;
import frc.robot.subsystems.manipulator.shooter.Shooter;
import frc.robot.subsystems.swerve.SwerveDriveTrain;

/** Add your docs here. */
public class RobotContainer {

    private Navx navx;
    private Intake intake;
    private Shooter shooter;
    private IntakePivot intakePivot;
    private ShooterPivot shooterPivot;
    private SwerveDriveTrain swerveDriveTrain;
    private LEDs leds;

    public RobotContainer() {

        navx = new Navx();
        intake = new Intake();
        shooter = new Shooter();
        intakePivot = new IntakePivot();
        shooterPivot = new ShooterPivot();
        swerveDriveTrain = new SwerveDriveTrain();
        leds = new LEDs();

    }

    public void ConfigureButtonBindings() {
        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case Joakin:
                OI.getInstance().ConfigureButtonBindings();
                swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
                /*shooterPivot.setDefaultCommand(new ShooterPivotKeepCurrentPosition());*/
                break;

            default:
                OI.getInstance().ConfigureButtonBindings();
                shooter.setDefaultCommand(new DriveShooter());
                swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
                /*shooterPivot.setDefaultCommand(new ShooterPivotKeepCurrentPosition()); */

        }
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

    public SwerveDriveTrain getSwerveDriveTrain() {
        return swerveDriveTrain;
    }
}
