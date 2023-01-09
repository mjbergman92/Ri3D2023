package frc.robot.sequences;

import frc.robot.RobotContainer.Axes;
import frc.robot.RobotContainer.Buttons;
import frc.robot.subsystems.ArmState;
import frc.statebasedcontroller.sequence.fundamental.phase.ISequencePhase;
import frc.statebasedcontroller.sequence.fundamental.phase.SequencePhase;
import frc.statebasedcontroller.sequence.fundamental.sequence.BaseSequence;
import frc.statebasedcontroller.subsystem.fundamental.state.ISubsystemState;

enum PositionArmPhase implements ISequencePhase {
    NEUTRAL,
    FLOOR(ArmState.FLOOR),
    MID_CUBE(ArmState.MID_CUBE),
    MID_CONE(ArmState.MID_CONE),
    SUBSTATTION(ArmState.SUBSTATTION),
    MOVE_MANUALLY(ArmState.MOVE_MANUALLY);

    SequencePhase phase;

    PositionArmPhase(ISubsystemState... states) {
        phase = new SequencePhase(states);
    }

    @Override
    public SequencePhase getPhase() {
        return phase;
    }
}

public class PositionArm extends BaseSequence<PositionArmPhase> {
    public PositionArm(PositionArmPhase neutralState, PositionArmPhase startState) {
        super(neutralState, startState);
    }

    @Override
    public void process() {
        if (Math.abs(Axes.Arm_Up_Down.getAxis()) > 0) {
            setNextPhase(PositionArmPhase.MOVE_MANUALLY);
        } else if (Buttons.Floor.getButton()) {
            setNextPhase(PositionArmPhase.FLOOR);
        } else if (Buttons.Mid_Cube.getButton()) {
            setNextPhase(PositionArmPhase.MID_CUBE);
        } else if (Buttons.Mid_Cone.getButton()) {
            setNextPhase(PositionArmPhase.MID_CONE);
        } else if (Buttons.Station.getButton()) {
            setNextPhase(PositionArmPhase.SUBSTATTION);
        }
        updatePhase();
    }

    @Override
    public boolean abort() {
        // TODO Auto-generated method stub
        return false;
    }
}
