// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public enum HumanDrivers {

        ///// DRIVERS /////
        DefaultUser, // DefaultUser use a default configuration for his joystick
        Joakin, // Joakin use a specific configuration for his joystick

        ///// PROGRAMMING /////
        MACG, // MACG use a specific configuration for testing features
        MACGwithGuitar, // MACG use a specific configuration for testing features
    }

    SendableChooser<HumanDrivers> Pilot = new SendableChooser<>();
    SendableChooser<HumanDrivers> Copilot = new SendableChooser<>();

    private Navx navx;
    private SwerveDriveTrain swerveDriveTrain;
    private Intake intake;
    private Shooter shooter;
    private IntakePivot intakePivot;
    private ShooterPivot shooterPivot;

    private HumanDrivers CURRENT_PILOT = HumanDrivers.DefaultUser;
    private HumanDrivers CURRENT_COPILOT = HumanDrivers.DefaultUser;

    public RobotContainer() {

        navx = new Navx();
        swerveDriveTrain = new SwerveDriveTrain();
    }

    public void ConfigureButtonBindings() {
        OI.getInstance().ConfigureButtonBindings();
        swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
        shooter.setDefaultCommand(new DriveShooter());
    }

    public void ConfigureDrivers() {

        Pilot.setDefaultOption("Default", HumanDrivers.DefaultUser);
        Pilot.addOption("Joakin", HumanDrivers.Joakin);
        Pilot.addOption("MACG", HumanDrivers.MACG);
        Pilot.addOption("MACG with Guitar", HumanDrivers.MACGwithGuitar);

        Copilot.setDefaultOption("Default", HumanDrivers.DefaultUser);
        Copilot.addOption("Joakin", HumanDrivers.Joakin);
        Copilot.addOption("MACG", HumanDrivers.MACG);
        Copilot.addOption("MACG with Guitar", HumanDrivers.MACGwithGuitar);
    }

    public void putDriverChoosers() {
        SmartDashboard.putData("Curent Pilot", Pilot);
        SmartDashboard.putData("Current Copilot", Copilot);
    }

    public void setDrivers() {
        setCurrentPilot(Pilot.getSelected());
        setCurrentCopilot(Copilot.getSelected());
    }

    public Navx getNavx() {
        return navx;
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

    public HumanDrivers getCurrentPilot() {
        return CURRENT_PILOT;
    }

    public void setCurrentPilot(HumanDrivers Pilot) {
        CURRENT_PILOT = Pilot;
    }

    public HumanDrivers getCurrentCopilot() {
        return CURRENT_COPILOT;
    }

    public void setCurrentCopilot(HumanDrivers Copilot) {
        CURRENT_COPILOT = Copilot;
    }

}
