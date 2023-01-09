package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.statebasedcontroller.subsystem.fundamental.state.ISubsystemState;
import frc.statebasedcontroller.subsystem.fundamental.state.SubsystemState;

import java.util.function.Consumer;

public enum DrivetrainState implements ISubsystemState<Drivetrain> {
	NEUTRAL((s) -> s.neutral()),
	DRIVE((s) -> {
		if ((RobotContainer.Buttons.Creep.getButton())) {
			s.creep();
		} else {
			s.drive();
		}
	});

	SubsystemState<Drivetrain> state;

	DrivetrainState(Consumer<Drivetrain> processFunction) {
		this.state = new SubsystemState<>(this, processFunction);
	}

	@Override
	public SubsystemState<Drivetrain> getState() {
		return state;
	}

	@Override
	public Drivetrain getSubsystem() {
		return Robot.drivetrain;
	}
}
