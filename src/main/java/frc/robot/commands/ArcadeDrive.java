// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import static frc.robot.Constants.DrivetrainConstants.*;
import frc.robot.classes.SPIKE293Utils;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.subsystems.Drivetrain;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class ArcadeDrive extends CommandBase 
{
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private final Drivetrain m_drivetrain;
    private final XboxController m_xboxcontroller;

    private double m_arcadeDeadband;
    private boolean m_forzaEnabled;
    private double m_forzaDeadband;
    private double m_velocityLimitPercentage;
    private double m_turningLimitPercentage;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    public ArcadeDrive(Drivetrain subsystem, XboxController xboxcontroller)
    {
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
 
        m_drivetrain = subsystem;
        addRequirements(m_drivetrain);
        m_xboxcontroller = xboxcontroller;
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        
        m_velocityLimitPercentage = DEFAULT_MAX_VELOCITY_PERCENTAGE;
        m_turningLimitPercentage = DEFAULT_MAX_TURNING_SPEED;
        m_arcadeDeadband = DEFAULT_ARCADE_JOY_DEADBAND;
        m_forzaDeadband = DEFAULT_FORZA_DEADBAND;
        m_forzaEnabled = DEFAULT_FORZA_MODE;
        SmartDashboard.putNumber("Arcade Joy Deadband", m_arcadeDeadband);
        SmartDashboard.putNumber("Forza Deadband", m_forzaDeadband);
        SmartDashboard.putBoolean("Forza Mode", m_forzaEnabled);       
        SmartDashboard.putNumber("Max Velocity Percentage", m_velocityLimitPercentage);
        SmartDashboard.putNumber("Max Turning Percentage", m_turningLimitPercentage);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        double turning;
        double speed;
        double triggerRight;
        double triggerLeft;
        
        //Get deadband value set in SmartDashboard
        m_arcadeDeadband = SmartDashboard.getNumber("Arcade Joy Deadband", DEFAULT_ARCADE_JOY_DEADBAND);
        m_arcadeDeadband = MathUtil.clamp(m_arcadeDeadband, 0.0d, 1.0d);
        m_forzaDeadband = SmartDashboard.getNumber("Forza Deadband", DEFAULT_FORZA_DEADBAND);
        m_forzaDeadband = MathUtil.clamp(m_forzaDeadband, 0.0d, 1.0d);
        m_forzaEnabled = SmartDashboard.getBoolean("Forza Mode", DEFAULT_FORZA_MODE);

        m_velocityLimitPercentage = SmartDashboard.getNumber("Max Velocity Percentage", DEFAULT_MAX_VELOCITY_PERCENTAGE);
        m_velocityLimitPercentage = MathUtil.clamp(m_velocityLimitPercentage, 0.0d, 1.0d);
        SmartDashboard.putNumber("Max Velocity Percentage", m_velocityLimitPercentage);

        m_turningLimitPercentage = SmartDashboard.getNumber("Max Turning Percentage", DEFAULT_MAX_TURNING_SPEED);
        m_turningLimitPercentage = MathUtil.clamp(m_turningLimitPercentage, 0.0d , 1.0d);
        SmartDashboard.putNumber("Max Turning Percentage", m_turningLimitPercentage);

        //Get turning. Note that the controls are inverted!        
        turning = m_xboxcontroller.getX(Hand.kLeft) * -1.0d;

        //Checks if joystick value is higher or lower than deadband value
        turning = SPIKE293Utils.applyDeadband(turning, m_arcadeDeadband);
        SmartDashboard.putNumber("AD: Turning", turning);

        //Check if we should use the triggers for speed
        if(m_forzaEnabled)
        {
            //Get trigger values
            triggerRight = m_xboxcontroller.getTriggerAxis(Hand.kRight);
            triggerRight = SPIKE293Utils.applyDeadband(triggerRight, m_forzaDeadband);
            triggerLeft = m_xboxcontroller.getTriggerAxis(Hand.kLeft);
            triggerLeft = SPIKE293Utils.applyDeadband(triggerLeft, m_forzaDeadband);
            SmartDashboard.putNumber("AD: Right Trigger", triggerRight);
            SmartDashboard.putNumber("AD: Left Trigger", triggerLeft);

            if(triggerRight >= triggerLeft)
            {
                //Use right trigger for forward speed!
                speed = triggerRight;
            }
            else
            {
                //Going in reverse! Right trigger was zero, set speed to left trigger
                speed = -triggerLeft;
            }
        }
        else
        {
            //Use the stick, note that the joystick is inverted, -1 is up, 1 is down
            speed = m_xboxcontroller.getY(Hand.kLeft) * -1.0d;
            speed = SPIKE293Utils.applyDeadband(speed, m_arcadeDeadband);
            SmartDashboard.putNumber("AD: Speed", speed);
        }

        //Clamp input to verify they are valid and greater than the deadband
        turning = MathUtil.clamp(turning, -1.0d, 1.0d);
        speed = MathUtil.clamp(speed, -1.0d, 1.0d);

        //Apply limiting percentage
        turning *= m_turningLimitPercentage;
        speed *= m_velocityLimitPercentage;
        arcadeDrive(speed, turning);
    }

    private void arcadeDrive(double velocity, double turning)
    {
        //Convert turning and speed to left right encoder velocity
        double leftMotorOutput;
        double rightMotorOutput;
    
        double maxInput = Math.copySign(Math.max(Math.abs(velocity), Math.abs(turning)), velocity);
        if (velocity >= 0.0) 
        {
            // First quadrant, else second quadrant
            if (turning >= 0.0) 
            {
              leftMotorOutput = maxInput;
              rightMotorOutput = velocity - turning;
            } 
            else 
            {
              leftMotorOutput = velocity + turning;
              rightMotorOutput = maxInput;
            }
        } 
        else 
        {
            // Third quadrant, else fourth quadrant
            if (turning >= 0.0) 
            {
                leftMotorOutput = velocity + turning;
                rightMotorOutput = maxInput;
            } 
            else 
            {
                leftMotorOutput = maxInput;
                rightMotorOutput = velocity - turning;
            }
        }

        //Convert to encoder velocity
        //double leftMotorSpeed = SPIKE293Utils.percentageToControllerVelocity(leftMotorOutput);
        // Right needs to be inverted
        //double rightMotorSpeed = SPIKE293Utils.percentageToControllerVelocity(rightMotorOutput *-1.0d);

        //Send to motors
        //m_drivetrain.velocityDrive(leftMotorSpeed, rightMotorSpeed);
        m_drivetrain.percentDrive(leftMotorOutput, rightMotorOutput * -1.0d);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        m_drivetrain.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() 
    {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
