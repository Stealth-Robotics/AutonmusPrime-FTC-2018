package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Robot;

public class DriveBase {

    private static final double TICKS_PER_MOTOR_REV    = 1120;
    private static final double DRIVE_GEAR_REDUCTION    = 1.143; // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 8.0; // For figuring circumference
    public static final double TICKS_PER_INCH = (TICKS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI); //16,086.49056

    // These constants define the desired driving/control characteristics
    // The can/should be tweaked to suite the specific robot drive train.
    public static final double DRIVE_SPEED = 0.45;     // Nominal speed for better accuracy.
    public static final double TURN_SPEED = 0.35;     // Nominal half speed for better accuracy.

    public static void MechanumDrive(double LeftStickX, double LeftStickY, double RightStickX) {
        Robot.getInstance().getRobotMap().SetDriveModeNoEncoders();

        double actualAngle = Robot.getInstance().getRobotMap().imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;

        final double x = Math.pow(-LeftStickX, 3.0);
        final double y = Math.pow(LeftStickY, 3.0);

        final double rotation = Math.pow(RightStickX, 3.0);
        final double direction = Math.atan2(x, y) + actualAngle;
        final double speed = Math.min(1.0, Math.sqrt(x * x + y * y));

        final double lf = speed * Math.sin(direction + Math.PI / 4.0) + rotation;
        final double rf = speed * Math.cos(direction + Math.PI / 4.0) - rotation;
        final double lr = speed * Math.cos(direction + Math.PI / 4.0) + rotation;
        final double rr = speed * Math.sin(direction + Math.PI / 4.0) - rotation;

        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(lf);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(rf);
        Robot.getInstance().getRobotMap().rearLeftDrive.setPower(lr);
        Robot.getInstance().getRobotMap().rearRightDrive.setPower(rr);
    }

    public static void TankDrive(double LeftStickY, double RightStickY) {
        Robot.getInstance().getRobotMap().SetDriveModeNoEncoders();

        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(LeftStickY);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(RightStickY);
        Robot.getInstance().getRobotMap().rearLeftDrive.setPower(-LeftStickY);
        Robot.getInstance().getRobotMap().rearRightDrive.setPower(-RightStickY);
    }

    public static void POVDrive(double Drive, double Turn){
        //make sure that the motors are in the correct setup
        Robot.getInstance().getRobotMap().SetDriveModeNoEncoders();

        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;
        double frontWheelPowerPercent = 0.7;
        double rearWheelPowerPercent = 1;

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        leftPower = Range.clip(Drive + Turn, -1.0, 1.0) ;
        rightPower = Range.clip(Drive - Turn, -1.0, 1.0) ;

        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(leftPower * frontWheelPowerPercent);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(rightPower * frontWheelPowerPercent);
        Robot.getInstance().getRobotMap().rearLeftDrive.setPower(leftPower * rearWheelPowerPercent);
        Robot.getInstance().getRobotMap().rearRightDrive.setPower(rightPower * rearWheelPowerPercent);
    }

    public static void KillDriveMotorPower() {
        Robot.getInstance().getRobotMap().SetDriveModeNoEncoders();
        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(0);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(0);
        Robot.getInstance().getRobotMap().rearLeftDrive.setPower(0);
        Robot.getInstance().getRobotMap().rearRightDrive.setPower(0);
    }
}
