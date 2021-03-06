package org.firstinspires.ftc.teamcode.Commands.DriveBase;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class DriveByGyro implements iCommand {

    private int runSequence;
    private boolean isDone = false;

    private double speed;
    private double angle;
    private double distance;

    private int newLeftTarget;
    private int newRightTarget;

    private static final double P_DRIVE_COEFF = -0.01;     // Larger is more responsive, but also less stable

    public DriveByGyro(int _runSequence, double _speed, double _distance){
        runSequence = _runSequence;
        speed = _speed;
        distance = _distance;
    }

    public void Init() {
        //Robot.getInstance().getRobotMap().initIMU();

        /*// calculate target angle in -179 to +180 range based on current angle
        angle = angle - Robot.getInstance().getRobotMap().IMUgetAngles()[0];
        while (angle > 180)  angle -= 360;
        while (angle <= -180) angle += 360;*/
        angle = Robot.getInstance().getRobotMap().IMUgetAngles()[0];

        //reset the encoders
        Robot.getInstance().getRobotMap().ResetDriveEncoders();

        // Determine new target position, and pass to motor controller
        int moveTicks = (int)(distance * DriveBase.TICKS_PER_INCH);

        // Turn On RUN_TO_POSITION(SetDriveModeEncoders) and Set Target
        Robot.getInstance().getRobotMap().SetDriveModeEncoders();

        Robot.getInstance().getRobotMap().frontLeftDrive.setTargetPosition(moveTicks);
        Robot.getInstance().getRobotMap().frontRightDrive.setTargetPosition(-moveTicks);

        newLeftTarget = moveTicks;
        newRightTarget = -moveTicks;


        // start moving
        speed = Range.clip(Math.abs(speed), 0.0, 1.0);
        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(speed);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(speed);
    }

    public void Run(double dt) {
        // keep going while BOTH motors are running other wise we are done
        if (Robot.getInstance().getRobotMap().frontLeftDrive.isBusy() && Robot.getInstance().getRobotMap().frontRightDrive.isBusy()) {

            // adjust relative speed based on heading error.
            double error = getError(angle);
            double steer = getSteer(error, P_DRIVE_COEFF);

            // if driving in reverse, the motor correction also needs to be reversed
            if (distance < 0)
                steer *= -1.0;

            //calculate the left and right speed combined with steer
            double leftSpeed = speed - steer;
            double rightSpeed = speed + steer;

            /*// Normalize speeds if either one exceeds +/- 1.0;
            double max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
            if (max > 1.0)
            {
                leftSpeed /= max;
                rightSpeed /= max;
            }*/

            leftSpeed = Range.clip(Math.abs(leftSpeed), -1.0, 1.0);
            Robot.getInstance().getRobotMap().frontLeftDrive.setPower(leftSpeed);

            rightSpeed = Range.clip(Math.abs(rightSpeed), -1.0, 1.0);
            Robot.getInstance().getRobotMap().frontRightDrive.setPower(rightSpeed);

            // Display drive status for the driver.
            Robot.getInstance().getTelemetry().addData("Err/St",  "%5.1f/%5.1f",  error, steer);
            Robot.getInstance().getTelemetry().addData("Target",  "%7d:%7d",      newLeftTarget,  newRightTarget);
            Robot.getInstance().getTelemetry().addData("Actual",  "%7d:%7d",      Robot.getInstance().getRobotMap().frontLeftDrive.getCurrentPosition(), Robot.getInstance().getRobotMap().frontRightDrive.getCurrentPosition());
            Robot.getInstance().getTelemetry().addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
            Robot.getInstance().getTelemetry().update();
        } else {
            isDone = true;
        }
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    private double getError(double targetAngle) {
        // calculate error in -179 to +180 range
        double robotError = targetAngle - Robot.getInstance().getRobotMap().IMUgetAngles()[0]; //gyro.getIntegratedZValue();
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
