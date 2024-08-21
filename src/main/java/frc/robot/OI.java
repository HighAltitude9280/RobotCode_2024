// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.manipulator.shooter.ControlShooter;
import frc.robot.commands.manipulator.shooter.RollersIfNotNoteOnShooter;
import frc.robot.commands.manipulator.shooter.RollersInUntilNoNote;
import frc.robot.commands.manipulator.shooter.RollersOut;
import frc.robot.commands.manipulator.shooter.ShooterDriveRPM;
import frc.robot.commands.manipulator.shooter.ShooterIntake;
import frc.robot.commands.manipulator.shooter.SuperShoot;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.primitiveAutos.MaintainPointToTarget;
import frc.robot.commands.autonomous.teleop.AutoAmp;
import frc.robot.commands.manipulator.compound.IntakeAndRollersOut;
import frc.robot.commands.manipulator.compound.IntakeAutoTransition;
import frc.robot.commands.manipulator.intake.DriveIntake;
import frc.robot.commands.manipulator.intake.IntakeIn;
import frc.robot.commands.manipulator.intake.IntakeOut;
import frc.robot.commands.manipulator.pivots.pivotsParameters.toggleOverride;
import frc.robot.commands.manipulator.pivots.positions.IntakePivotExtruir;
import frc.robot.commands.manipulator.pivots.positions.IntakePivotMoveTo;
import frc.robot.commands.manipulator.pivots.positions.IntakePivotRetractar;
import frc.robot.commands.manipulator.pivots.primitives.IntakePivotDown;
import frc.robot.commands.manipulator.pivots.primitives.IntakePivotUp;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotDown;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotMaintainTarget;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotMaintainTargetAndRollers;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotUp;
import frc.robot.commands.manipulator.pivots.primitives.ToggleIntakePivot;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.IntakePivotResetEncoder;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.ShooterPivotResetCanCoder;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.ShooterPivotSetAngleTarget;
import frc.robot.commands.swerve.TestSwerve;
import frc.robot.commands.swerve.swerveParameters.ResetOdometryZeros;
import frc.robot.commands.swerve.swerveParameters.SetIsFieldOriented;
import frc.robot.commands.swerve.swerveParameters.ToggleIsOnCompetitiveField;
import frc.robot.resources.joysticks.HighAltitudeGuitarJoystick;
import frc.robot.resources.joysticks.HighAltitudeJoystick;
import frc.robot.resources.joysticks.HighAltitudeGuitarJoystick.GuitarJoystickType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.AxisType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.ButtonType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.JoystickType;

/** Add your docs here. */
public class OI {
    public static OI instance;

    private HighAltitudeJoystick pilot;
    private HighAltitudeJoystick copilot;

    private HighAltitudeGuitarJoystick pilotG;
    private HighAltitudeGuitarJoystick copilotG;

    public void ConfigureButtonBindings() {

        ////////////////////////// PILOT //////////////////////////

        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case DefaultUser:

                pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

                break;

            case Joakin:

                pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

                pilot.setAxisDeadzone(AxisType.LEFT_X, 0.1);
                pilot.setAxisDeadzone(AxisType.LEFT_Y, 0.1);
                pilot.setAxisDeadzone(AxisType.RIGHT_X, 0.1);

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.onTrueCombo(new ResetOdometryZeros(), ButtonType.START, ButtonType.BACK);
                pilot.whileTrue(ButtonType.X, new DriveIntake(-0.1));

                // pilot.whileTrue(ButtonType.POV_N, new IntakePivotMoveTo(0.75, 0.0));
                pilot.whileTrue(ButtonType.POV_S, new IntakePivotMoveTo(0.5, 0));
                pilot.onTrue(ButtonType.POV_N, new ShooterPivotSetAngleTarget(20));
                pilot.whileTrue(ButtonType.POV_N, new ShooterPivotMaintainTarget(0.5));
                pilot.onTrue(ButtonType.POV_N, new IntakePivotRetractar(0.5));

                pilot.onTrue(ButtonType.POV_S, new ShooterPivotSetAngleTarget(40));
                pilot.whileTrue(ButtonType.POV_S, new ShooterPivotMaintainTarget(0.5));
                pilot.onTrue(ButtonType.POV_S, new IntakePivotExtruir(0.5));
                // ARREGLAR ESTO

                // pilot.whileTrue(ButtonType.POV_E, new ShooterPivotUp());
                // pilot.whileTrue(ButtonType.POV_W, new ShooterPivotDown());

                pilot.whileTrue(ButtonType.RB, new IntakeAndRollersOut());
                // pilot.whileTrue(ButtonType.LB, new ShooterIntake());
                pilot.whileTrue(ButtonType.LB, new IntakeOut());
                pilot.whileTrue(ButtonType.LB, new RollersInUntilNoNote());

                // pilot.whileTrue(ButtonType.RT, new SuperShoot());
                // pilot.whileTrue(ButtonType.RT, new ShooterDriveRPM(5000));
                pilot.whileTrue(ButtonType.RT, new ControlShooter(4000));
                pilot.whileTrue(ButtonType.LT, new IntakeIn());

                // pilot.whileTrue(ButtonType.A, new followTarget());

                pilot.whileTrue(ButtonType.POV_SE, new ToggleIsOnCompetitiveField());

                // pilot.toggleOnTrue(ButtonType.A, new SwerveDriveAndCenter());
                // pilot.onTrue(ButtonType.A, new ShooterPivotSetAngleTarget(22));
                // pilot.whileTrue(ButtonType.A, new ShooterPivotMaintainTarget(0.5));
                pilot.whileTrue(ButtonType.POV_E, new ShooterPivotMaintainTargetAndRollers(0.5, 12));

                pilot.whileTrue(ButtonType.POV_W, new ShooterPivotMaintainTargetAndRollers(0.5, 35));

                // pilot.whileTrue(ButtonType.A,new MaintainPointToTarget(0.5, 0.8));
                pilot.onTrue(ButtonType.A, new IntakeAutoTransition());

                // pilot.onTrue(ButtonType.B, new AutoAmp());
                pilot.whileTrue(ButtonType.B, new ShooterPivotSetAngleTarget(-30));
                pilot.whileTrue(ButtonType.B, new ShooterPivotMaintainTarget(0.5));
                pilot.whileTrue(ButtonType.B, new IntakeOut());

                break;

            case MACG:

                pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

                pilot.onTrue(ButtonType.B, new IntakePivotResetEncoder());

                pilot.whileTrue(ButtonType.RB, new IntakeOut());
                pilot.whileTrue(ButtonType.LB, new IntakeIn());

                pilot.setAxisDeadzone(AxisType.LEFT_X, 0.1);
                pilot.setAxisDeadzone(AxisType.LEFT_Y, 0.1);

                break;

            case MACGwithGuitar:
                pilotG = new HighAltitudeGuitarJoystick(0, GuitarJoystickType.GUITAR_HERO);

                pilotG.onTrue(HighAltitudeGuitarJoystick.ButtonType.BACK, new WaitCommand(0.5));

                break;

            case Mafer:
                pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

                pilot.onTrue(ButtonType.A, new TestSwerve());

                break;

            default:

                pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

                break;

        }

        ///////////////////////// COPILOT /////////////////////////

        switch (HighAltitudeConstants.CURRENT_COPILOT) {

            case DefaultUser:

                copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

                break;

            case Joakin:

                copilotG = new HighAltitudeGuitarJoystick(1, GuitarJoystickType.GUITAR_HERO);

                break;

            case MACG:

                copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

                copilot.onTrue(ButtonType.START, new IntakePivotResetEncoder());
                copilot.onTrue(ButtonType.BACK, new toggleOverride());

                copilot.whileTrueCombo(new IntakePivotUp(), ButtonType.POV_N, ButtonType.A);
                copilot.whileTrueCombo(new IntakePivotDown(), ButtonType.POV_S, ButtonType.A);

                copilot.whileTrueCombo(new ShooterPivotUp(), ButtonType.POV_N, ButtonType.B);
                copilot.whileTrueCombo(new ShooterPivotDown(), ButtonType.POV_S, ButtonType.B);

                copilot.whileTrue(ButtonType.RB, new IntakeAndRollersOut());
                copilot.whileTrue(ButtonType.LB, new ToggleIntakePivot());

                break;

            case MACGwithGuitar:

                copilotG = new HighAltitudeGuitarJoystick(1, GuitarJoystickType.GUITAR_HERO);

                break;

            case LuisNN:

                copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

                copilot.onTrue(ButtonType.START, new IntakePivotResetEncoder());
                copilot.onTrue(ButtonType.START, new ShooterPivotResetCanCoder());
                copilot.onTrue(ButtonType.BACK, new toggleOverride());

                copilot.whileTrue(ButtonType.X, new DriveIntake(-0.1));

                copilot.whileTrue(ButtonType.POV_N, new IntakePivotMoveTo(0.75, 0.0));
                copilot.whileTrue(ButtonType.POV_S, new IntakePivotMoveTo(0.75, 160.0));

                copilot.whileTrue(ButtonType.POV_E, new ShooterPivotUp());
                copilot.whileTrue(ButtonType.POV_W, new ShooterPivotDown());

                copilot.whileTrue(ButtonType.RB, new IntakeAndRollersOut());
                copilot.whileTrue(ButtonType.LB, new ShooterIntake());

                copilot.whileTrue(ButtonType.RT, new SuperShoot());
                copilot.whileTrue(ButtonType.LT, new IntakeIn());

                copilot.whileTrue(ButtonType.A, new IntakePivotDown());
                copilot.whileTrue(ButtonType.B, new IntakePivotUp());

                break;

            case Abby:

                copilotG = new HighAltitudeGuitarJoystick(1, GuitarJoystickType.GUITAR_HERO);

                copilotG.whileTrue(HighAltitudeGuitarJoystick.ButtonType.POV_N, new ShooterPivotUp()/*
                                                                                                     * ShooterPivotMoveTo
                                                                                                     * (0.4, 0.0)
                                                                                                     */);
                copilotG.whileTrue(HighAltitudeGuitarJoystick.ButtonType.POV_S, new ShooterPivotDown()/*
                                                                                                       * ShooterPivotMoveTo
                                                                                                       * (0.4, 0.0)
                                                                                                       */);

                copilotG.onTrue(HighAltitudeGuitarJoystick.ButtonType.START, new IntakePivotResetEncoder());
                copilotG.onTrue(HighAltitudeGuitarJoystick.ButtonType.START, new ShooterPivotResetCanCoder());
                copilotG.onTrue(HighAltitudeGuitarJoystick.ButtonType.BACK, new toggleOverride());

                copilotG.whileTrue(HighAltitudeGuitarJoystick.ButtonType.GREEN, new IntakeIn());
                copilotG.whileTrue(HighAltitudeGuitarJoystick.ButtonType.RED, new IntakeAndRollersOut());
                copilotG.whileTrue(HighAltitudeGuitarJoystick.ButtonType.YELLOW, new SuperShoot());

                copilotG.whileTrue(HighAltitudeGuitarJoystick.ButtonType.BLUE, new DriveIntake(-0.1));

                break;

            default:

                copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

                break;

        }
    }

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public double getDefaultSwerveDriveSpeed() {

        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case DefaultUser:
                return -pilot.getAxis(AxisType.LEFT_Y);

            case Joakin:
                return -pilot.getAxis(AxisType.LEFT_Y);

            default:
                return -pilot.getAxis(AxisType.LEFT_Y);

        }
    }

    public double getDefaultSwerveDriveStrafe() {

        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case DefaultUser:
                return -pilot.getAxis(AxisType.LEFT_X);

            case Joakin:
                return -pilot.getAxis(AxisType.LEFT_X);

            default:
                return -pilot.getAxis(AxisType.LEFT_X);
        }
    }

    public double getDefaultSwerveDriveTurn() {

        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case DefaultUser:
                return -pilot.getAxis(AxisType.RIGHT_X);

            case Joakin:
                return -pilot.getAxis(AxisType.RIGHT_X);

            case MACG:
                return -pilot.getAxis(AxisType.RIGHT_X);

            default:
                return -pilot.getAxis(AxisType.RIGHT_X);
        }
    }

    public double getDeafultShooterDriveSpeed() {
        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case DefaultUser:
                return pilot.getTriggers();

            case Joakin:
                return pilot.getTriggers();

            case MACG:
                return pilot.getTriggers();

            default:
                return pilot.getTriggers();
        }
    }

    public HighAltitudeJoystick getPilot() {
        switch (HighAltitudeConstants.CURRENT_PILOT) {

            case DefaultUser:
                return pilot;

            case Joakin:
                return pilot;

            case MACG:
                return pilot;

            default:
                return pilot;
        }
    }

    public HighAltitudeJoystick getCopilot() {
        switch (HighAltitudeConstants.CURRENT_COPILOT) {

            case DefaultUser:
                return copilot;

            case Joakin:
                return copilot;

            case MACG:
                return copilot;

            case LuisNN:
                return copilot;

            default:
                return copilot;
        }
    }

    public HighAltitudeGuitarJoystick getCopilotG() {
        switch (HighAltitudeConstants.CURRENT_COPILOT) {
            case Abby:
                return copilotG;

            default:
                return copilotG;
        }
    }
}
