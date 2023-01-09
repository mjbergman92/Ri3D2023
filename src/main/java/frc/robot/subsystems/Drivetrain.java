package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.DRIVE;
import frc.robot.RobotContainer.Axes;
import frc.statebasedcontroller.subsystem.fundamental.subsystem.BaseSubsystem;

public class Drivetrain extends BaseSubsystem<DrivetrainState> {
	static ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");
	CANSparkMax left = new CANSparkMax(DRIVE.LEFT_MASTER, MotorType.kBrushless);
	CANSparkMax left_slave = new CANSparkMax(DRIVE.LEFT_SLAVE, MotorType.kBrushless);
	CANSparkMax right = new CANSparkMax(DRIVE.RIGHT_MASTER, MotorType.kBrushless);
	CANSparkMax right_slave = new CANSparkMax(DRIVE.RIGHT_SLAVE, MotorType.kBrushless);
	DifferentialDrive drive = new DifferentialDrive(left, right);

	public Drivetrain() {
		super(DrivetrainState.NEUTRAL);
		left_slave.follow(left);
		right_slave.follow(right);
	}

	protected void drive() {
		drive.arcadeDrive(Axes.Drive_ForwardBackward.getAxis(), Axes.Drive_LeftRight.getAxis());
	}

	protected void creep() {
		drive.arcadeDrive(Axes.Drive_ForwardBackward.getAxis() * DRIVE.CREEP_RATIO, Axes.Drive_LeftRight.getAxis() * DRIVE.CREEP_ROTATE_RATIO);
	}

	@Override
	public void neutral() {
		drive.arcadeDrive(0, 0);
	}

	@Override
	public boolean abort() {
		drive.arcadeDrive(0, 0);
		return true;
	}
}
