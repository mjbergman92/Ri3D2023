// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.pathplanner.PathPlannerFollower;
import frc.robot.autons.Auton;
import frc.robot.autons.Path;
import frc.robot.sequences.SequenceProcessor;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.statebasedcontroller.sequence.fundamental.phase.ISequencePhase;
import frc.statebasedcontroller.sequence.fundamental.sequence.BaseAutonSequence;

import static frc.robot.Constants.*;

import java.util.Arrays;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Drivetrain drivetrain;
	public static Arm arm;
	public static Intake intake;
	public static SequenceProcessor sequenceProcessor;
	public static RobotContainer robotContainer;
	public static PneumaticsControlModule mainPCM;
	public static boolean isAutonomous = false;
	public static double designatedLoopPeriod = 20;
	public static BaseAutonSequence<? extends ISequencePhase> chosenAuton;
	private static long autonStartTime;
	private final SendableChooser<Auton> sendableChooser = new SendableChooser<>();
	private final Field2d m_field = new Field2d();
	private int loopCnt = 0;
	private int loopPeriod = 0;
	private int logCounter = 0;
	private long prevLoopTime = 0;

	@Override
	public void robotInit() {
		robotContainer = new RobotContainer();
		drivetrain = new Drivetrain();
		arm = new Arm();
		intake = new Intake();
		sequenceProcessor = new SequenceProcessor();
		// Arrays.asList(Path.values()).stream().forEach(path -> path.loadPath());
		SmartDashboard.putNumber("Auton Time Delay(ms)", 0.0);
		sendableChooser.setDefaultOption("NO AUTON", Auton.NO_OP);
		SmartDashboard.putData(sendableChooser);
		SmartDashboard.putData("Field", m_field);
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void disabledInit() {
		drivetrain.forceRelease();
		prevLoopTime = 0;
		arm.forceRelease();
		intake.forceRelease();
	}

	@Override
	public void disabledPeriodic() {
		log();
		long currentTime = System.currentTimeMillis();
		if (currentTime - prevLoopTime >= designatedLoopPeriod) {
			loopPeriod = (int) (currentTime - prevLoopTime);
			prevLoopTime = currentTime;
			loopCnt++;
			drivetrain.neutral();
			drivetrain.process();
		}
		Timer.delay(0.001);
	}

	@Override
	public void autonomousInit() {
		chosenAuton = sendableChooser.getSelected().getAuton();
		chosenAuton.start();
		autonStartTime = System.currentTimeMillis();
		prevLoopTime = 0;
	}

	@Override
	public void autonomousPeriodic() {
		isAutonomous = this.isAutonomous();
		log();
		long currentTime = System.currentTimeMillis();
		if (currentTime - prevLoopTime >= designatedLoopPeriod) {
			loopPeriod = (int) (currentTime - prevLoopTime);
			prevLoopTime = currentTime;
			loopCnt++;
			if (currentTime - autonStartTime > SmartDashboard.getNumber("Auton Time Delay(ms)", 0.0)) {
				chosenAuton.process();
			}
			// run processes
			/** Run subsystem process methods here */
			drivetrain.process();
			arm.process();
			intake.process();
		}
		Timer.delay(0.001);
	}

	@Override
	public void teleopInit() {
		drivetrain.forceRelease();
		arm.forceRelease();
		intake.forceRelease();
		prevLoopTime = 0;
	}

	@Override
	public void teleopPeriodic() {
		isAutonomous = this.isAutonomous();
		log();
		long currentTime = System.currentTimeMillis();
		if (currentTime - prevLoopTime >= designatedLoopPeriod) {
			loopPeriod = (int) (currentTime - prevLoopTime);
			prevLoopTime = currentTime;
			loopCnt++;
			sequenceProcessor.process();
			// run processes
			/** Run subsystem process methods here */
			drivetrain.process();
			arm.process();
			intake.process();
		}
		Timer.delay(0.001);
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

	public void log() {
		logCounter++;
		if (logCounter > 5) {
			SmartDashboard.putNumber("Arm Position", arm.getArmPosition());
			logCounter = 0;
		}
	}
}
