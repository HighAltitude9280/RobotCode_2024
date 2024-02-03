// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

        switch(Robot.getRobotContainer().CURRENT_PILOT){

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

            pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

            break;

            case MACG:

            pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

            pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
            pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

            pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

            break;
            
            case MACGwithGuitar:

            pilotG = new HighAltitudeGuitarHeroJoystick(0);

            break;

            default:

            pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);

            pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
            pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

            pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());

            break;

        }

        ///////////////////////// COPILOT /////////////////////////

        switch(Robot.getRobotContainer().CURRENT_COPILOT){

            case DefaultUser:

            copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

            break;

            case Joakin:

            copilotG = new HighAltitudeGuitarHeroJoystick(1);

            break;

            case MACG:

            copilot = new HighAltitudeJoystick(1, JoystickType.XBOX);

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

        switch(Robot.getRobotContainer().CURRENT_PILOT){

            case DefaultUser:
            return -pilot.getAxis(AxisType.LEFT_Y);

            case Joakin:
            return -pilot.getAxis(AxisType.LEFT_Y);

            default:
            return -pilot.getAxis(AxisType.LEFT_Y);

        }
    }

    public double getDefaultSwerveDriveStrafe() {

        switch(Robot.getRobotContainer().CURRENT_PILOT){

            case DefaultUser:
            return -pilot.getAxis(AxisType.LEFT_X);

            case Joakin:
            return -pilot.getAxis(AxisType.LEFT_X);

            default:
            return -pilot.getAxis(AxisType.LEFT_X);
        }
    }

    public double getDefaultSwerveDriveTurn() {

        switch(Robot.getRobotContainer().CURRENT_PILOT){

            case DefaultUser:
            return -pilot.getAxis(AxisType.RIGHT_X);

            case Joakin:
            return -pilot.getAxis(AxisType.RIGHT_X);

            default:
            return -pilot.getAxis(AxisType.RIGHT_X);
        }
    }
}
