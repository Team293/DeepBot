// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DrivetrainConstants.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.kauailabs.navx.frc.AHRS;
import frc.robot.classes.Kinematics;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.classes.Position2D;
import frc.robot.classes.SPIKE293Utils;

/**
 *
 */
public class Drivetrain extends SubsystemBase
{
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private CANSparkMax m_leftMotorLead;
    private CANSparkMax m_leftMotorFollow;
    private CANPIDController leftPID;
    private CANEncoder leftEncoder;
    private CANSparkMax m_rightMotorLead;
    private CANSparkMax m_rightMotorFollow;
    private CANPIDController rightPID;
    private CANEncoder rightEncoder;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    AHRS m_navX;
    private Kinematics m_kinematics;

    /**
    *
    */
    public Drivetrain(Kinematics kinematics)
    {
        m_leftMotorLead = new CANSparkMax(2, MotorType.kBrushless);
        m_leftMotorFollow = new CANSparkMax(3, MotorType.kBrushless);
        m_leftMotorFollow.follow(m_leftMotorLead, false);
        
        m_leftMotorLead.setIdleMode(IdleMode.kCoast);
        m_leftMotorFollow.setIdleMode(IdleMode.kCoast);
        leftEncoder = m_leftMotorLead.getEncoder();
        leftEncoder.setPositionConversionFactor(ENCODER_CONVERSION_FACTOR);
        leftEncoder.setVelocityConversionFactor(ENCODER_CONVERSION_FACTOR);
        leftPID = m_leftMotorLead.getPIDController();
        leftPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
        leftPID.setSmartMotionMaxVelocity(MAX_VELOCITY, SMART_MOTION_SLOT);
        leftPID.setSmartMotionMinOutputVelocity(MIN_VELOCITY, SMART_MOTION_SLOT);
        leftPID.setSmartMotionMaxAccel(MAX_ACCEL, SMART_MOTION_SLOT);
        
        m_rightMotorLead = new CANSparkMax(4, MotorType.kBrushless);
        m_rightMotorFollow = new CANSparkMax(5, MotorType.kBrushless);
        m_rightMotorFollow.follow(m_rightMotorLead, false);
        
        m_rightMotorLead.setIdleMode(IdleMode.kCoast);
        m_rightMotorFollow.setIdleMode(IdleMode.kCoast);
        rightEncoder = m_rightMotorLead.getEncoder();
        rightEncoder.setPositionConversionFactor(ENCODER_CONVERSION_FACTOR);
        rightEncoder.setVelocityConversionFactor(ENCODER_CONVERSION_FACTOR);
        rightPID = m_rightMotorLead.getPIDController();
        rightPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
        rightPID.setSmartMotionMaxVelocity(MAX_VELOCITY, SMART_MOTION_SLOT);
        rightPID.setSmartMotionMinOutputVelocity(MIN_VELOCITY, SMART_MOTION_SLOT);
        rightPID.setSmartMotionMaxAccel(MAX_ACCEL, SMART_MOTION_SLOT);

        leftPID.setP(KP);
        leftPID.setI(KI);
        leftPID.setD(KD);
        leftPID.setFF(KF);
        
        rightPID.setP(KP);
        rightPID.setI(KI);
        rightPID.setD(KD);
        rightPID.setFF(KF);

        m_navX = new AHRS(Port.kMXP);
        //Setup Gyro
        
        setupGyro(m_navX, 0.0d);
        m_kinematics = kinematics;
    }

    @Override
    public void periodic() 
    {
        // Put code here to be run every loop
        //Run Kinematics
        if(USE_NAVX_HEADING)
        {
            //use the NAVX for heading
            double headingInRadians = Math.toRadians(getGyroHeadingDegrees());
            m_kinematics.calculatePosition(getLeftEncoderPosition(), getRightEncoderPosition(), headingInRadians);
        }
        else
        {
            //Use the encoder information for heading
            m_kinematics.calculatePosition(getLeftEncoderPosition(), getRightEncoderPosition());
        }
        
        //Get current pose from Kinematics
        Position2D currentPose = m_kinematics.getPose();

        //Push robot info to Dashboard
        SmartDashboard.putNumber("Kinematics X (Feet)", currentPose.getX());
        SmartDashboard.putNumber("Kinematics Y (Feet)", currentPose.getY());
        SmartDashboard.putNumber("Kinematics Heading (degrees)", currentPose.getHeadingDegrees());

        SmartDashboard.putNumber("Left Encoder Velocity (Ft/S)", getLeftEncoderVelocity());
        SmartDashboard.putNumber("Left Encoder Position (Ft)", getLeftEncoderPosition());
        SmartDashboard.putNumber("Right Encoder Velocity (Ft/S)", getRightEncoderVelocity());
        SmartDashboard.putNumber("Right Encoder Position (Ft)", getRightEncoderPosition());

        SmartDashboard.putNumber("Robot Heading (degrees)", getGyroHeadingDegrees());
        SmartDashboard.putNumber("NavX X Accel", m_navX.getWorldLinearAccelX());
        SmartDashboard.putNumber("NavX Y Accel", m_navX.getWorldLinearAccelY());
        SmartDashboard.putNumber("NavX Z Accel", m_navX.getWorldLinearAccelZ());
        SmartDashboard.putNumber("NavX Yaw", m_navX.getYaw());
        SmartDashboard.putNumber("NavX Angle", m_navX.getAngle());
        SmartDashboard.putNumber("NavX Fused Heading", m_navX.getFusedHeading());
    }
    
    //Sets the motors to run at a given percentage output, does NOT use onboard PID
    public void percentDrive(double leftPercentage, double rightPercentage) 
    {
        SmartDashboard.putNumber("Percentage Left", leftPercentage);
        SmartDashboard.putNumber("Percentage Right", rightPercentage);
        m_leftMotorLead.set(leftPercentage);
        m_rightMotorLead.set(rightPercentage);
    }


    //Sets the motors to encoder rpm, uses the onboard motor PID
    public void velocityDrive(double vL, double vR)
    {
        SmartDashboard.putNumber("Desired Velocity Left (Encoder RPM)", vL);
        SmartDashboard.putNumber("Desired Velocity Right (Encoder RPM)", vR);
        leftPID.setReference(vL, ControlType.kVelocity);
        rightPID.setReference(vR, ControlType.kVelocity); 
    }

    // Stops motor usually used after the drive command ends to prevent shenanigans
    public void stop() 
    {
        m_leftMotorLead.set(0.0d);
        m_rightMotorLead.set(0.0d);
    }

    public void initAutonomous(Position2D startingPose)
    {
        //reset encoders
        zeroDriveTrainEncoders();

        m_kinematics.setPose(startingPose);
        //Reset Gyro
        setupGyro(m_navX, startingPose.getHeadingDegrees());
    }

    /**   
     * returns left encoder position
     * 
     * @return left encoder position
     */
    public double getLeftEncoderPosition() 
    {
        return SPIKE293Utils.controllerUnitsToFeet(m_leftMotorLead.getEncoder().getPosition()); 
    }
    
    /**
     * returns right encoder position
     * 
     * @return right encoder position
     */
    public double getRightEncoderPosition() 
    {
        return SPIKE293Utils.controllerUnitsToFeet(m_rightMotorLead.getEncoder().getPosition());    
    }

    /**
     * returns left encoder Velocity in ft/s
     * 
     * @return left encoder Velocity in ft/s
     */
    public double getLeftEncoderVelocity()
    {
        return SPIKE293Utils.controllerVelocityToFeetPerSec(m_leftMotorLead.getEncoder().getVelocity());
    }

    /**
     * returns right encoder Velocity in ft/s
     * 
     * @return right encoder Velocity in ft/s
     */
    public double getRightEncoderVelocity()
    {
        return SPIKE293Utils.controllerVelocityToFeetPerSec(m_rightMotorLead.getEncoder().getVelocity());
    }

    /**
     * returns robot Velocity in ft/s
     * 
     * @return robot Velocity in ft/s
     */
    public double getRobotVelocity()
    {
        // Returns the velocity of the robot by taking the averga of the velcity on both sides of the robor
        return (getLeftEncoderVelocity() + getRightEncoderVelocity()) / 2.0d;
    }
    
    private void zeroDriveTrainEncoders() 
    {
        m_leftMotorLead.getEncoder().setPosition(0);
        m_rightMotorLead.getEncoder().setPosition(0);
    }
      
    public double getGyroHeadingDegrees() 
    {
        return (m_navX.getAngle() * -1.0d);
    }
    
    public void setupGyro(AHRS gyro, double startingAngleDegrees)
    {
        System.out.println("Calibrating gyroscope.");
        gyro.enableBoardlevelYawReset(true);
        gyro.reset();
        gyro.calibrate();

        //Wait for gyro to calibrate ~1 - 10 seconds
        while (gyro.isCalibrating())
        {
        }
        
        //Set's the starting angle to the given angle
        gyro.setAngleAdjustment(startingAngleDegrees);
        
        System.out.println("Calibrating gyroscope done.");
    }
}
