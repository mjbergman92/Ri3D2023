// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import frc.robot.extras.XboxPlusPOV;

import java.util.concurrent.Callable;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	// private final ExampleSubsystem m_exampleSubsystem = new
	// ExampleSubsystem();
	final static XboxPlusPOV xbox1 = new XboxPlusPOV(0);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
	}

	public static XboxPlusPOV getController1() {
		return xbox1;
	}

	private static double deadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0;
		}
	}

	private static double modifyAxis(double value) {
		// Deadband
		value = deadband(value, 0.1);
		// Square the axis
		value = Math.copySign(value * value, value);
		return value;
	}

	public enum Buttons {
		Creep(() -> getController1().getRightBumper()),
		Floor(() -> getController1().getAButton()),
		Mid_Cube(() -> getController1().getBButton()),
		Mid_Cone(() -> getController1().getXButton()),
		Station(() -> getController1().getYButton()),
		Intake(() -> getController1().getRightTriggerAxis() > 0.15),
		Extake(() -> getController1().getLeftTriggerAxis() > 0.15);

		Callable<Boolean> callable;

		Buttons(Callable<Boolean> callable) {
			this.callable = callable;
		}

		public boolean getButton() {
			try {
				return callable.call().booleanValue();
			} catch (Exception ex) {
				return false;
			}
		}
	}

	public enum Axes {
		Drive_ForwardBackward(() -> -modifyAxis(getController1().getLeftY())),
		Drive_LeftRight(() -> modifyAxis(getController1().getLeftX())),
		Arm_Up_Down(() -> -modifyAxis(getController1().getRightY()));

		Callable<Double> callable;

		Axes(Callable<Double> callable) {
			this.callable = callable;
		}

		public double getAxis() {
			try {
				return callable.call().doubleValue();
			} catch (Exception ex) {
				return 0.0;
			}
		}
	}
}
