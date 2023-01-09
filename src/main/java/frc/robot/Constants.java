// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static final class DRIVE {
		/**
		 * Drive Module CAN IDs
		 */
		public static final int LEFT_MASTER = 1;
		public static final int LEFT_SLAVE = 2;
		public static final int RIGHT_MASTER = 3;
		public static final int RIGHT_SLAVE = 4;
		/**
		 * Creep
		 */
		public static final double CREEP_RATIO = 0.5;
		public static final double CREEP_ROTATE_RATIO = 0.5;
	}

	public static final class ARM {
		/**
		 * Arm CAN ID
		 */
		public static final int ARM = 5;
		/**
		 * Physical Knowns
		 */
		public static final double GEAR_RATIO = 1;// TODO gear ratio
		public static final double kG = 1.82; // TODO sysid
		public static final double kV = 0.03;
		public static final double kA = 0.0025;
		/**
		 * Tunable PID
		 */
		public static final double kP = 0.0027918; // TODO tune after using sysid
		public static final double kD = 0.0;
		/**
		 * Desired Parameters
		 */
		public static final double MAX_DEG_PER_SEC = 15.0;
		public static final double MAX_RPM = MAX_DEG_PER_SEC / 360 * 60;
		public static final double SEC_TO_MAX_VEL = 0.25;
		public static final double MAX_ACC_RPM_PER_SEC = MAX_RPM / SEC_TO_MAX_VEL;
		public static final double MAX_CHANGE_POS_PER_CYCLE = MAX_DEG_PER_SEC * 0.020;
		public static final double CLOSED_LOOP_RAME_RATE = 0.5;
		public static final double OPEN_LOOP_RAME_RATE = 0.5;
		/**
		 * Arm Position Angles
		 */
		public static final int LOWEST_ARM_POS = -70;
		public static final int HIGHEST_ARM_POS = 70; // TODO find comfortable max
		public static final int LOW_TARGET_POS = -70; // TODO low target position to grab off floor
		public static final int MIDDLE_TARGET_POS_CUBE = -40; // TODO middle target position
		public static final int MIDDLE_TARGET_POS_CONE = -40; // TODO middle target position for
		                                                      // adjusting placement of cone
		public static final int STATION_PICKUP_POS = -30; // TODO the position to be able to pickup
		                                                  // from the substation
	}

	public static final class INTAKE {
		public static final int INTAKE = 6;
		public static final double INTAKE_POWER = 0.5;
		public static final double EXTAKE_POWER = -0.5;
	}

	public static final class ROBOT {
		public static final double BATTERY_NOMINAL_VOLTAGE = 13.2; // Nicely charged battery
		public static final double MAX_VOLTAGE = 12.0;
		static public final double ROBOT_MASS_kg = Units.lbsToKilograms(100);
	}

	public static final class AUTO {
		// TODO NOT REALLY USED, UNLESS AUTONOMOUS CREATED
		public static final double kMaxSpeedMetersPerSecond = 2.0;
		public static final double kMaxAccelerationMetersPerSecondSquared = 4.0;
	}
}
