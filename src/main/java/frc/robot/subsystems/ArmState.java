package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.Constants.ARM;
import frc.robot.RobotContainer.Axes;
import frc.statebasedcontroller.subsystem.fundamental.state.ISubsystemState;
import frc.statebasedcontroller.subsystem.fundamental.state.SubsystemState;

import java.util.function.Consumer;

public enum ArmState implements ISubsystemState<Arm> {
    NEUTRAL((s) -> s.neutral()),
    FLOOR((s) -> {
        s.setTargetArmPosition(ARM.LOW_TARGET_POS);
    }),
    MID_CUBE((s) -> {
        s.setTargetArmPosition(ARM.MIDDLE_TARGET_POS_CUBE);
    }),
    MID_CONE((s) -> {
        s.setTargetArmPosition(ARM.MIDDLE_TARGET_POS_CONE);
    }),
    SUBSTATTION((s) -> {
        s.setTargetArmPosition(ARM.STATION_PICKUP_POS);
    }),
    MOVE_MANUALLY((s) -> {
        s.changeTargetArmPosition(Axes.Arm_Up_Down.getAxis());
    });

    SubsystemState<Arm> state;

    ArmState(Consumer<Arm> processFunction) {
        this.state = new SubsystemState<>(this, (s) -> {
            processFunction.accept(s);
            s.armCorrect();
        });
    }

    @Override
    public SubsystemState<Arm> getState() {
        return state;
    }

    @Override
    public Arm getSubsystem() {
        return Robot.arm;
    }
}
