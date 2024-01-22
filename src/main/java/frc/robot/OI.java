// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Human_Drivers.HumanDrivers;
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
    private HighAltitudeGuitarHeroJoystick pilotG;

    public void ConfigureButtonBindings() {

        if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.DefaultUser){
        pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);
    
        pilot.setAxisDeadzone(AxisType.LEFT_Y, 0.08);
        pilot.setAxisDeadzone(AxisType.LEFT_X, 0.08);
        pilot.setAxisDeadzone(AxisType.RIGHT_X, 0.08);

        pilot.onTrue(ButtonType.BACK, new SetIsFieldOriented(true));
        pilot.onTrue(ButtonType.START, new SetIsFieldOriented(false));

        pilot.onTrue(ButtonType.POV_N, new ResetOdometryZeros());
    }
        else if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.MACGwithGuitar){
        pilotG = new HighAltitudeGuitarHeroJoystick(0);
        }
        else{ pilot = new HighAltitudeJoystick(0, JoystickType.XBOX);}
    }

    
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public double getDefaultSwerveDriveSpeed() {
        if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.DefaultUser){
            return -pilot.getAxis(AxisType.LEFT_Y);
        }
        else if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.MACGwithGuitar){
            return -pilotG.getDriveY();
        }
        else{
            return -pilot.getAxis(AxisType.LEFT_Y);
        }
    }

    public double getDefaultSwerveDriveStrafe() {
        if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.DefaultUser){
            return -pilot.getAxis(AxisType.LEFT_X);
        }
        else if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.MACGwithGuitar){
            return -pilotG.getDriveX();
        }
        else{
            return -pilot.getAxis(AxisType.LEFT_X);
        }
    }

    public double getDefaultSwerveDriveTurn() {
        if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.DefaultUser){
            return -pilot.getAxis(AxisType.RIGHT_X);
        }
        else if(HighAltitudeConstants.CURRENT_PILOT == HumanDrivers.MACGwithGuitar){
            return -pilotG.getDriveZ();
        }
        else{
            return -pilot.getAxis(AxisType.RIGHT_X);
        }
    }

}
