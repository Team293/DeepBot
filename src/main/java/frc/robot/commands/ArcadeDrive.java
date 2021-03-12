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
import static frc.robot.Constants.InputConstants.*;

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

    private double m_forzaDeadband;
    private double m_arcadeJoyDeadband;
    private boolean m_forzaEnabled;
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
        
        m_forzaDeadband = FORZA_DEADBAND;
        m_arcadeJoyDeadband = ARCADE_JOY_DEADBAND;
        m_forzaEnabled = DEFAULT_FORZA_MODE;
        SmartDashboard.putNumber("Arcade Joystick Deadband", m_arcadeJoyDeadband);
        SmartDashboard.putNumber("Forza Deadband", m_forzaDeadband);
        SmartDashboard.putBoolean("Forza Mode", m_forzaEnabled);
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
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
        m_forzaDeadband = SmartDashboard.getNumber("Forza Deadband", FORZA_DEADBAND);
        m_arcadeJoyDeadband = SmartDashboard.getNumber("Arcade Joystick Deadband", ARCADE_JOY_DEADBAND);
        m_forzaEnabled = SmartDashboard.getBoolean("Forza Mode", DEFAULT_FORZA_MODE);

        //Checks if joystick value is higher or lower than deadband value
        turning = m_drivetrain.clampInput((m_xboxcontroller.getX(Hand.kLeft)),m_arcadeJoyDeadband);
        //Check if we should use the triggers for speed
        if(m_forzaEnabled)
        {
            //Get trigger values
            triggerRight = m_xboxcontroller.getTriggerAxis(Hand.kRight);
            triggerLeft = m_xboxcontroller.getTriggerAxis(Hand.kLeft);

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
            
            speed = m_drivetrain.clampInput(speed,m_forzaDeadband);
        }
        else
        {
            //Use the stick, note that the joystick is inverted, -1 is up, 1 is down
            speed = m_drivetrain.clampInput((m_xboxcontroller.getY(Hand.kLeft) * -1),m_arcadeJoyDeadband);
        }

        SmartDashboard.putNumber("AD: Speed", speed);
        SmartDashboard.putNumber("AD: Turning", turning);

        //Pass input to arcadeDrive
        m_drivetrain.velArcadeDrive(speed, turning);  
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
