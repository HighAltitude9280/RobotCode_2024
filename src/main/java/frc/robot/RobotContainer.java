// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.swerve.DefaultSwerveDrive;
import frc.robot.resources.components.Navx;
import frc.robot.subsystems.swerve.SwerveDriveTrain;

/** Add your docs here. */
public class RobotContainer {
    private Navx navx;
    private SwerveDriveTrain swerveDriveTrain;

    public RobotContainer(){
        navx = new Navx();
        swerveDriveTrain = new SwerveDriveTrain();
        
    }

    public void config(){
        OI.getInstance().ConfigureButtonBindings();
        swerveDriveTrain.setDefaultCommand(new DefaultSwerveDrive());
    }

    public Navx getNavx(){
        return navx;
    }

    public SwerveDriveTrain getSwerveDriveTrain(){
        return swerveDriveTrain;
    }
}
