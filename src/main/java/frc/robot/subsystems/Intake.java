package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants.INTAKE;
import frc.statebasedcontroller.subsystem.fundamental.subsystem.BaseSubsystem;

public class Intake extends BaseSubsystem<IntakeState> {
    WPI_TalonSRX intake = new WPI_TalonSRX(INTAKE.INTAKE);

    public Intake() {
        super(IntakeState.NEUTRAL);
        /* Intake Motors */
        intake.set(ControlMode.PercentOutput, 0.0);
    }

    protected void rollIn() {
        intake.set(INTAKE.INTAKE_POWER);
    }

    protected void rollOut() {
        intake.set(INTAKE.EXTAKE_POWER);
    }

    protected void intakeOff() {
        intake.set(0);
    }

    @Override
    public void neutral() {
        intakeOff();
    }

    @Override
    public boolean abort() {
        intakeOff();
        return true;
    }
}
