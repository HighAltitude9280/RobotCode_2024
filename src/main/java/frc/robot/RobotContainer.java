// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Human_Drivers.HumanDrivers;
import frc.robot.commands.autonomous.primitiveAutos.MoveUntilLimit;
import frc.robot.commands.autonomous.primitiveAutos.ShootPreloaded;
import frc.robot.commands.climber.MaintainClimberPosition;
import frc.robot.commands.manipulator.intake.IntakeIn;
/*import frc.robot.commands.manipulator.pivots.positions.ShooterPivotKeepCurrentPosition;*/
import frc.robot.commands.manipulator.shooter.DriveShooter;
import frc.robot.commands.swerve.DefaultSwerveDrive;
import frc.robot.resources.components.Navx;
import frc.robot.resources.components.PWMLEDStrip.LEDs;
import frc.robot.subsystems.climber.Climber;
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
    private Climber climber;

    SendableChooser<Command> m_chooser = new SendableChooser<>();

    public RobotContainer() {

        navx = new Navx();
        intake = new Intake();
        shooter = new Shooter();
        intakePivot = new IntakePivot();
        shooterPivot = new ShooterPivot();
        swerveDriveTrain = new SwerveDriveTrain();
        climber = new Climber();
        leds = new LEDs();

    }

    public void ConfigureButtonBindings() {
        OI.getInstance().ConfigureButtonBindings();
        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case Joakin:
                swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
                /* shooterPivot.setDefaultCommand(new ShooterPivotKeepCurrentPosition()); */
                break;

            default:
                shooter.setDefaultCommand(new DriveShooter());
                swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
                /* shooterPivot.setDefaultCommand(new ShooterPivotKeepCurrentPosition()); */
        }

        // TODO: matar el climber si no hay climber
        climber.setDefaultCommand(new MaintainClimberPosition());
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

    public Command getAutonomousCommand() {
        return m_chooser.getSelected();
    }

    public void putAutoChooser() {
        SmartDashboard.putData("Autonomous", m_chooser);
    }

    public LEDs getLEDs() {
        return leds;
    }

    public Climber getClimber() {
        return climber;
    }

    public void generateAutos() {
        NamedCommands.registerCommand("ShootPreloaded", new ShootPreloaded());
        NamedCommands.registerCommand("LowerIntake", new MoveUntilLimit(-0.25));
        NamedCommands.registerCommand("RaiseIntake", new MoveUntilLimit(0.25));
        NamedCommands.registerCommand("IntakeIn", new IntakeIn().withTimeout(5.0));

        m_chooser.setDefaultOption("Nothing", new WaitCommand(0));
        m_chooser.addOption("Shoot Preloaded", new ShootPreloaded());
        m_chooser.addOption("Go Straight", new PathPlannerAuto("GoStraight"));
        m_chooser.addOption("Shoot Go Straight", new PathPlannerAuto("ShootGoStraight"));
        m_chooser.addOption("Shoot Then IntakeIn", new PathPlannerAuto("ShootThenIntakeIn"));
        m_chooser.addOption("Two Piece Careful", new PathPlannerAuto("TwoPieceCareful"));
        m_chooser.addOption("Two Piece Semi-Fluid", new PathPlannerAuto("TwoPieceSemiFluid"));
        m_chooser.addOption("Two Piece Fluid", new PathPlannerAuto("TwoPieceFluid"));
        m_chooser.addOption("Three Piece Careful", new PathPlannerAuto("ThreePieceCareful"));
    }
}
