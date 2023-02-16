package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import frc.robot.Constants.ARM;
import frc.robot.RobotContainer.Axes;
import frc.statebasedcontroller.subsystem.fundamental.subsystem.BaseSubsystem;

public class Arm extends BaseSubsystem<ArmState> {
    CANSparkMax armMotor = new CANSparkMax(ARM.ARM, MotorType.kBrushless);
    SparkMaxPIDController pid;
    ArmFeedforward feedforward = new ArmFeedforward(ARM.kS, ARM.kG, ARM.kV, ARM.kA);
    double targetPos = ARM.LOWEST_ARM_POS;
    double prevDegreesPerSecond;
    // ProfiledPIDController armPositionController

    public Arm() {
        super(ArmState.NEUTRAL);
        /* Arm Motor */
        armMotor.restoreFactoryDefaults();
        armMotor.setInverted(true);
        RelativeEncoder encoder = armMotor.getEncoder();
        encoder.setPositionConversionFactor(360 * ARM.GEAR_RATIO);
        encoder.setVelocityConversionFactor(360 / 60 * ARM.GEAR_RATIO);
        encoder.setPosition(ARM.LOWEST_ARM_POS);
        armMotor.setClosedLoopRampRate(ARM.CLOSED_LOOP_RAME_RATE);
        armMotor.setOpenLoopRampRate(ARM.OPEN_LOOP_RAME_RATE);
        pid = armMotor.getPIDController();
        pid.setFeedbackDevice(encoder);
        pid.setP(ARM.kP, 0);
        pid.setD(ARM.kD, 0);
        pid.setOutputRange(-0.2, 0.4, 0);
        pid.setSmartMotionMaxAccel(ARM.MAX_ACC_RPM_PER_SEC, 0);
        pid.setSmartMotionMaxVelocity(ARM.MAX_RPM, 0);
        armMotor.burnFlash();
    }

    protected void setTargetArmPosition(double targetPos) {
        if (targetPos > ARM.HIGHEST_ARM_POS || targetPos < ARM.LOWEST_ARM_POS) return;
        this.targetPos = targetPos;
    }

    protected void changeTargetArmPosition(double proportion) {
        targetPos += (ARM.MAX_CHANGE_POS_PER_CYCLE * proportion);
        if (targetPos > ARM.HIGHEST_ARM_POS) targetPos = ARM.HIGHEST_ARM_POS;
        if (targetPos < ARM.LOWEST_ARM_POS) targetPos = ARM.LOWEST_ARM_POS;
    }

    protected void armCorrect() {
        double degrees = armMotor.getEncoder().getPosition();
        double degreesPerSecond = armMotor.getEncoder().getVelocity();
        double degreesPerSecondSquared = (degreesPerSecond - prevDegreesPerSecond) / 0.020;
        // pid.setReference(targetPos, ControlType.kSmartMotion, 0,
        // feedforward.calculate(Math.toRadians(degrees), degreesPerSecond,
        // degreesPerSecondSquared), SparkMaxPIDController.ArbFFUnits.kVoltage);
        // armMotor.set(Axes.Arm_Up_Down.getAxis());
        double output = Axes.Arm_Up.getAxis();
        if (Math.abs(output) < 0.01) output = Axes.Arm_Down.getAxis();
        armMotor.set(output);
        prevDegreesPerSecond = degreesPerSecond;
    }

    public double getArmPosition() {
        return armMotor.getEncoder().getPosition();
    }

    @Override
    public void neutral() {
        armMotor.set(0.0);
    }

    @Override
    public boolean abort() {
        armMotor.set(0.0);
        return true;
    }
}
