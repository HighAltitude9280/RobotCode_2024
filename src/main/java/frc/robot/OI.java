// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.resources.joysticks.HighAltitudeJoystick;
import frc.robot.resources.joysticks.HighAltitudeJoystick.AxisType;
import frc.robot.resources.joysticks.HighAltitudeJoystick.JoystickType;

/** Add your docs here. */
public class OI {
    public static OI instance;

    
    private HighAltitudeJoystick chassis;

    public void ConfigureButtonBindings() {
        chassis = new HighAltitudeJoystick(0, JoystickType.XBOX);
        chassis.setAxisDeadzone(AxisType.LEFT_Y, 0.1);
        chassis.setAxisDeadzone(AxisType.LEFT_X, 0.1);
        chassis.setAxisDeadzone(AxisType.RIGHT_X, 0.1);
    }
    
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public double getDefaultSwerveDriveSpeed() {
        return -chassis.getAxis(AxisType.LEFT_Y);
    }

    public double getDefaultSwerveDriveStrafe() {
        return -chassis.getAxis(AxisType.LEFT_X);
    }

    public double getDefaultSwerveDriveTurn() {
        return chassis.getAxis(AxisType.RIGHT_X);
    }

}
