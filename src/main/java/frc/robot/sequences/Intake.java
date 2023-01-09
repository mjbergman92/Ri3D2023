package frc.robot.sequences;

import frc.robot.RobotContainer.Buttons;
import frc.robot.subsystems.IntakeState;
import frc.statebasedcontroller.sequence.fundamental.phase.ISequencePhase;
import frc.statebasedcontroller.sequence.fundamental.phase.SequencePhase;
import frc.statebasedcontroller.sequence.fundamental.sequence.BaseSequence;
import frc.statebasedcontroller.subsystem.fundamental.state.ISubsystemState;

enum IntakePhase implements ISequencePhase {
    NEUTRAL,
    INTAKE(IntakeState.INTAKE),
    EXTAKE(IntakeState.EXTAKE);

    SequencePhase phase;

    IntakePhase(ISubsystemState... states) {
        phase = new SequencePhase(states);
    }

    @Override
    public SequencePhase getPhase() {
        return phase;
    }
}

public class Intake extends BaseSequence<IntakePhase> {
    public Intake(IntakePhase neutralState, IntakePhase startState) {
        super(neutralState, startState);
    }

    @Override
    public void process() {
        if (Buttons.Intake.getButton()) {
            setNextPhase(IntakePhase.INTAKE);
        } else if (Buttons.Extake.getButton()) {
            setNextPhase(IntakePhase.EXTAKE);
        } else {
            setNextPhase(IntakePhase.NEUTRAL);
        }
        updatePhase();
    }

    @Override
    public boolean abort() {
        // TODO Auto-generated method stub
        return false;
    }
}
