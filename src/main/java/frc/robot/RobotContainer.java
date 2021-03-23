// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.classes.Kinematics;
import frc.robot.classes.Position2D;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import frc.robot.classes.TargetPosition2D;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer 
{
  private static RobotContainer m_robotContainer = new RobotContainer();

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
  // The robot's subsystems
  public final Kinematics m_kinematics = new Kinematics(new Position2D(0.0,0.0,0.0));
  public final Drivetrain m_drivetrain = new Drivetrain(m_kinematics);

  // Joysticks/Controllers
  private final XboxController xboxController = new XboxController(4);
  private final Joystick operatorRightJoy = new Joystick(2);
  private final Joystick operatorLeftJoy = new Joystick(3);
  private final Joystick rightJoy = new Joystick(1);
  private final Joystick leftJoy = new Joystick(0);
  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() 
  {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Configure the button bindings
    configureButtonBindings();
    // Configure default commands
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND

    // Configure autonomous sendable chooser
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    List<TargetPosition2D>threeByThreeBoxPath = new ArrayList<TargetPosition2D>();
    List<TargetPosition2D>bouncePath = new ArrayList<TargetPosition2D>();
    List<TargetPosition2D>slalomPath = new ArrayList<TargetPosition2D>();
    List<TargetPosition2D>barrelPath = new ArrayList<TargetPosition2D>();
    
    //Slalom 
    slalomPath.add(new TargetPosition2D(3.25,2.5, Math.toRadians(0),3));
    slalomPath.add(new TargetPosition2D(5,2.5,Math.toRadians(40),3));
    slalomPath.add(new TargetPosition2D(7.5,5,Math.toRadians(40),3));
    slalomPath.add(new TargetPosition2D(10,7.5,Math.toRadians(0),3));
    slalomPath.add(new TargetPosition2D(20,7.5,Math.toRadians(0),3));
    slalomPath.add(new TargetPosition2D(22.5,5,Math.toRadians(-60),3));
    slalomPath.add(new TargetPosition2D(25,2.5,Math.toRadians(0),3));
    slalomPath.add(new TargetPosition2D(27.5,5,Math.toRadians(90),3));
    slalomPath.add(new TargetPosition2D(25,7.5,Math.toRadians(180),3));
    slalomPath.add(new TargetPosition2D(22.5,5,Math.toRadians(-120),3));
    slalomPath.add(new TargetPosition2D(20,2.5,Math.toRadians(180),3));
    slalomPath.add(new TargetPosition2D(10,2.5,Math.toRadians(180),3));
    slalomPath.add(new TargetPosition2D(7.5,5,Math.toRadians(120),3));
    slalomPath.add(new TargetPosition2D(2.5,7.5,Math.toRadians(180),3));
    
    //3X3 Square 
    threeByThreeBoxPath.add(new TargetPosition2D(0, 0, Math.toRadians(0.0), 0.5d));
    threeByThreeBoxPath.add(new TargetPosition2D(3.0, 0.0, Math.toRadians(0.0), 0.5d));
    threeByThreeBoxPath.add(new TargetPosition2D(3.0, 3.0, Math.toRadians(180.0), 0.5d));
    threeByThreeBoxPath.add(new TargetPosition2D(0.0, 3.0, Math.toRadians(270.0), 0.5d));
    threeByThreeBoxPath.add(new TargetPosition2D(0.0, 0.0, Math.toRadians(0), 0.5d));

    //Bounce path
    bouncePath.add(new TargetPosition2D(2.5,7.5, Math.toRadians(0),0.5));
    bouncePath.add(new TargetPosition2D(7.5,11, Math.toRadians(90),-0.5));
    bouncePath.add(new TargetPosition2D(10,5, Math.toRadians(-60),-0.5));
    bouncePath.add(new TargetPosition2D(12.5, 2.5,Math.toRadians(0),-0.5));
    bouncePath.add(new TargetPosition2D(15,5, Math.toRadians(90),-0.5));
    bouncePath.add(new TargetPosition2D(15,11, Math.toRadians(90),0.5));
    bouncePath.add(new TargetPosition2D(15,5, Math.toRadians(-90),0.5));
    bouncePath.add(new TargetPosition2D(17.5,2.5, Math.toRadians(0),0.5));
    bouncePath.add(new TargetPosition2D(20,2.5, Math.toRadians(0),0.5));
    bouncePath.add(new TargetPosition2D(22.5,5, Math.toRadians(90),0.5));
    bouncePath.add(new TargetPosition2D(22.5,11, Math.toRadians(90),-0.5));
    bouncePath.add(new TargetPosition2D(25,7.5, Math.toRadians(0),-0.5));
    bouncePath.add(new TargetPosition2D(27.5,7.5, Math.toRadians(0),-0.5));

    //Barrel Path
    barrelPath.add(new TargetPosition2D(0.0, 0.0, Math.toRadians(0),0.0));

    m_chooser.setDefaultOption("3 x 3 Autonav", new AutonomousCommand(m_drivetrain, m_kinematics, threeByThreeBoxPath));
    m_chooser.addOption("Slalom Autonav", new AutonomousCommand(m_drivetrain, m_kinematics, slalomPath));
    m_chooser.addOption("Bounce Autonav", new AutonomousCommand(m_drivetrain, m_kinematics, bouncePath));
    m_chooser.addOption("Barrel Autonav", new AutonomousCommand(m_drivetrain, m_kinematics, barrelPath));
    
    //Setting default command for drivetrain as VelocityDrive
    m_drivetrain.setDefaultCommand(new ArcadeDrive( m_drivetrain, xboxController));

    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() 
  {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() 
  {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
  }

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
  public Joystick getleftJoy() 
  {
    return leftJoy;
  }

  public Joystick getrightJoy() 
  {
    return rightJoy;
  }

  public Joystick getoperatorLeftJoy() 
  {
    return operatorLeftJoy;
  }

  public Joystick getoperatorRightJoy()
  {
    return operatorRightJoy;
  }

  public XboxController getxboxController() 
  {
    return xboxController;
  }
  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() 
  {
    // The selected command will be run in autonomous
    return m_chooser.getSelected();
  }
  public Command getTeleopCommand()
  {
    return new ArcadeDrive(m_drivetrain, xboxController);
  }
}
