// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.ShooterPivotResetEncoder;
import frc.robot.commands.manipulator.intake.IntakeIn;
import frc.robot.commands.manipulator.intake.IntakeOut;
import frc.robot.commands.manipulator.pivots.positions.ShooterPivotMoveTo;
import frc.robot.commands.manipulator.pivots.primitives.IntakePivotDown;
import frc.robot.commands.manipulator.pivots.primitives.IntakePivotUp;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotDown;
import frc.robot.commands.manipulator.pivots.primitives.ShooterPivotUp;
import frc.robot.commands.manipulator.pivots.primitives.pivotParameters.IntakePivotResetEncoder;
import frc.robot.commands.swerve.TestSwerve;
import frc.robot.commands.swerve.swerveParameters.ResetOdometryZeros;
import frc.robot.commands.swerve.swerveParameters.SetIsFieldOriented;
import frc.robot.resources.joysticks.HighAltitudeGuitarHeroJoystick;
import frc.robot.resources.joysticks.HighAltitudeJoystick;
import frc.robot.resources.joysticks.HighAltitudeJoystick.AxisType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.ButtonType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.JoystickType;

/** Add your docs here. */
public class OI {
    public static OI instance;

    private HighAltitudeJoystick pilot;
    private HighAltitudeJoystick copilot;

    private HighAltitudeGuitarHeroJoystick pilotG;
    private HighAltitudeGuitarHeroJoystick copilotG;

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

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.setAxisDeadzone(AxisType.LEFT_X, 0.1);
                pilot.setAxisDeadzone(AxisType.LEFT_Y, 0.1);

                pilot.onTrueCombo(new ResetOdometryZeros(), ButtonType.START, ButtonType.BACK);

                pilot.whileTrue(ButtonType.RB, new IntakeOut());
                pilot.whileTrue(ButtonType.LB, new IntakeIn());

                break;

            case MACG:

                pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

                pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
                pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

                pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

                pilot.onTrue(ButtonType.A, new ShooterPivotResetEncoder());
                pilot.onTrue(ButtonType.B, new IntakePivotResetEncoder());

                pilot.whileTrue(ButtonType.RB, new IntakeOut());
                pilot.whileTrue(ButtonType.LB, new IntakeIn());

                pilot.setAxisDeadzone(AxisType.LEFT_X, 0.1);
                pilot.setAxisDeadzone(AxisType.LEFT_Y, 0.1);

                pilot.whileTrue(ButtonType.POV_E, new ShooterPivotMoveTo(105));

                break;

            case MACGwithGuitar:

                pilotG = new HighAltitudeGuitarHeroJoystick(0);

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

                copilotG = new HighAltitudeGuitarHeroJoystick(1);

                break;

            case MACG:

                copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

                copilot.whileTrueCombo(new ShooterPivotUp(), ButtonType.A, ButtonType.POV_N);
                copilot.whileTrueCombo(new ShooterPivotDown(), ButtonType.A, ButtonType.POV_S);

                copilot.whileTrueCombo(new IntakePivotUp(), ButtonType.B, ButtonType.POV_N);
                copilot.whileTrueCombo(new IntakePivotDown(), ButtonType.B, ButtonType.POV_S);

                copilot.onTrue(ButtonType.START, new IntakePivotResetEncoder());

                break;

            case MACGwithGuitar:

                copilotG = new HighAltitudeGuitarHeroJoystick(1);

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

            default:
                return copilot;
        }
    }
}
