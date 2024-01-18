package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.HighAltitudeConstants;

public class SwerveModule {
    private final CANSparkMax driveMotor;
    private final CANSparkMax turningMotor;
    private final CANSparkMax driveEncoder;
    private final CANSparkMax turningEncoder;

    private final PIDController turningPidController;

    private final AnalogInput absoluteEncoder;
    private final boolean absoluteEncoderReversed;
    private final double absoluteEncoderOffsetRad;

    public SwerveModule(int driveMotorId, int turningMotorId, boolean driveMotorReversed, boolean turningMotorReversed,
    int absoluteEncoderId, double absoluteEncoderOffset, boolean absoluteEncoderReversed){

this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
this.absoluteEncoderReversed = absoluteEncoderReversed;
absoluteEncoder = new AnalogInput ( absoluteEncoderId);

driveMotor = new CANSparkMax ( driveMotorId, MotorType.kBrushless);
turningMotor = new CANSparkMax ( turningMotorId, MotorType.kBrushless);

driveMotor.setInverted (driveMotorReversed);
turningMotor.setInverted ( turningMotorReversed);

driveEncoder = driveMotor.getEncoder();
turningEncoder = turningMotor.getEncoder();

driveEncoder.setPositionConversionFactor(HighAltitudeConstants.kDriveEncoderRot2Meter);
driveEncoder.setVelocityConversionFactor(HighAltitudeConstants.kDriveEncoderRPM2MeterPerSec);
turningEncoder.setPositionConversionFactor(HighAltitudeConstants.kDriveEncoderRot2Meter);
turningEncoder.setVelocityConversionFactor(HighAltitudeConstants.kDriveEncoderRPM2MeterPerSec);

turningPidController = new PIDController(HighAltitudeConstants.kPTurning,0,0);
    }
}