// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.manipulator.shooter.ShooterAmp;
import frc.robot.commands.manipulator.shooter.ShooterIntake;
import frc.robot.commands.manipulator.shooter.SuperShoot;
import frc.robot.commands.climber.MoveClimer;
import frc.robot.commands.manipulator.compound.IntakeAndRollersOut;
import frc.robot.commands.manipulator.intake.IntakeIn;
import frc.robot.commands.manipulator.intake.IntakeOut;
import frc.robot.commands.manipulator.pivots.pivotsParameters.toggleOverride;
import frc.robot.commands.manipulator.pivots.positions.ShooterPivotMoveTo;
import frc.robot.commands.manipulator.pivots.primitives.IntakePivotDown;
import frc.robot.commands.manipulator.pivots.primitives.IntakePivotUp;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotDown;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotUp;
import frc.robot.commands.manipulator.pivots.primitives.ToggleIntakePivot;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.IntakePivotResetEncoder;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.ShooterPivotResetCanCoder;
import frc.robot.commands.swerve.TestSwerve;
import frc.robot.commands.swerve.swerveParameters.ResetOdometryZeros;
import frc.robot.commands.swerve.swerveParameters.SetIsFieldOriented;
import frc.robot.resources.joysticks.HighAltitudeJoystick;
import frc.robot.resources.joysticks.HighAltitudeJoystick.AxisType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.ButtonType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.JoystickType;

/** Add your docs here. */
public class OI {
    public static OI instance;

    private HighAltitudeJoystick pilot;
    private HighAltitudeJoystick copilot;

    // private HighAltitudeGuitarHeroJoystick pilotG;
    // private HighAltitudeGuitarHeroJoystick copilotG;

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

                pilot.whileTrue(ButtonType.RB, new IntakeAndRollersOut());
                pilot.whileTrue(ButtonType.LB, new ShooterIntake());

                pilot.whileTrue(ButtonType.RT, new ShooterAmp());
                pilot.whileTrue(ButtonType.LT, new IntakeIn());

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

                pilot.whileTrue(ButtonType.POV_E, new ShooterPivotMoveTo(105));

                break;

            case MACGwithGuitar:

                // pilotG = new HighAltitudeGuitarHeroJoystick(0);
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

                // copilotG = new HighAltitudeGuitarHeroJoystick(1);

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

                // copilotG = new HighAltitudeGuitarHeroJoystick(1);

                break;

            case LuisNN:

                copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

                copilot.onTrue(ButtonType.START, new IntakePivotResetEncoder());
                copilot.onTrue(ButtonType.Y, new ShooterPivotResetCanCoder());
                copilot.onTrue(ButtonType.BACK, new toggleOverride());

                copilot.whileTrue(ButtonType.X, new ToggleIntakePivot());

                copilot.whileTrue(ButtonType.POV_N, new IntakePivotUp());
                copilot.whileTrue(ButtonType.POV_S, new IntakePivotDown());

                copilot.whileTrue(ButtonType.POV_E, new ShooterPivotUp());
                copilot.whileTrue(ButtonType.POV_W, new ShooterPivotDown());

                copilot.whileTrue(ButtonType.RB, new IntakeAndRollersOut());
                copilot.whileTrue(ButtonType.LB, new ShooterIntake());

                copilot.whileTrue(ButtonType.RT, new SuperShoot());
                copilot.whileTrue(ButtonType.LT, new IntakeIn());

                copilot.whileTrue(ButtonType.A, new MoveClimer(0.5));
                copilot.whileTrue(ButtonType.B, new MoveClimer(-0.5));

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
}
