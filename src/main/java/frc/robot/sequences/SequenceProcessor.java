package frc.robot.sequences;

import frc.robot.Robot;

public class SequenceProcessor {
	/**
	 * Create a new variable of each of the functions
	 */
	public Drive drive;
	public PositionArm positionArm;
	public Intake intake;

	public SequenceProcessor() {
		/**
		 * Instantiate each of the sequences
		 */
		drive = new Drive(DrivePhase.NEUTRAL, DrivePhase.DRIVE);
		positionArm = new PositionArm(PositionArmPhase.NEUTRAL, PositionArmPhase.NEUTRAL);
		intake = new Intake(IntakePhase.NEUTRAL, IntakePhase.NEUTRAL);
	}

	public void process() {
		drive.start(Robot.drivetrain);
		// positionArm.start();
		intake.start();
		drive.process();
		// positionArm.process();
		intake.process();
	}
}
