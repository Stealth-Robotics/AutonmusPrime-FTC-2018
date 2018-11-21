package org.firstinspires.ftc.teamcode.Commands.DriveBase;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class TurnByGyro implements iCommand {

    private int timer = 5000;
    private boolean isDone = false;
    private int runSequence;

    private double Speed;
    private double Angle;

    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable

    public TurnByGyro(int _runSequence, double speed, double angle){
        runSequence = _runSequence;
        Speed = speed;
        Angle = angle;
    }

    public TurnByGyro(int _runSequence, double speed, double angle, int _timer){
        runSequence = _runSequence;
        Speed = speed;
        Angle = angle;
        timer = _timer;
    }

    public void Init() {
        Robot.getInstance().getRobotMap().initIMU();
        Robot.getInstance().getRobotMap().SetDriveModeNoEncoders();
    }

    public void Run(double dt) {
        timer -= dt;

        isDone = onHeading(Speed, Angle, P_TURN_COEFF);
    }

    /**
     * Perform one cycle of closed loop heading control.
     *
     * @param speed     Desired speed of turn.
     * @param angle     Absolute Angle (in Degrees) relative to last gyro reset.
     *                  0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                  If a relative angle is required, add/subtract from current heading.
     * @param PCoeff    Proportional Gain coefficient
     * @return
     */
    boolean onHeading(double speed, double angle, double PCoeff) {
        double error;
        double steer;
        boolean onTarget = false;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(leftSpeed);
        Robot.getInstance().getRobotMap().rearLeftDrive.setPower(leftSpeed);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(rightSpeed);
        Robot.getInstance().getRobotMap().rearRightDrive.setPower(rightSpeed);

        // Display it for the driver.
        Robot.getInstance().getTelemetry().addData("Target", "%5.2f", angle);
        Robot.getInstance().getTelemetry().addData("Err/St", "%5.2f/%5.2f", error, steer);
        Robot.getInstance().getTelemetry().addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
        Robot.getInstance().getTelemetry().update();

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    private double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - Robot.getInstance().getRobotMap().IMUgetAngles()[0]; //gyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    private double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void Stop() {
        DriveBase.KillDriveMotorPower();
    }

    public boolean IsDone() {
        return isDone;
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
