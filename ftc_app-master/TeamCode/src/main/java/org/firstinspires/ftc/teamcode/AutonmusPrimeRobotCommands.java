package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class AutonmusPrimeRobotCommands {
    public AutonmusPrimeRobotMap robotMap;
    
    public AutonmusPrimeRobotCommands(HardwareMap HwMap) {
        
        robotMap = new AutonmusPrimeRobotMap(HwMap);
    }
    
    public void Drive(double LeftStickX, double LeftStickY, double RightStickX) {
        robotMap.SetDriveModeNoEncoders();
        
        double auctualAngle = robotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
        
        final double x = Math.pow(-LeftStickX, 3.0);
        final double y = Math.pow(LeftStickY, 3.0);
        
        final double rotation = Math.pow(RightStickX, 3.0);
        final double direction = Math.atan2(x, y) + auctualAngle;
        final double speed = Math.min(1.0, Math.sqrt(x * x + y * y));
        
        final double lf = speed * Math.sin(direction + Math.PI / 4.0) + rotation;
        final double rf = speed * Math.cos(direction + Math.PI / 4.0) - rotation;
        final double lr = speed * Math.cos(direction + Math.PI / 4.0) + rotation;
        final double rr = speed * Math.sin(direction + Math.PI / 4.0) - rotation;
        
        robotMap.frontLeftDrive.setPower(lf);
        robotMap.frontRightDrive.setPower(rf);
        robotMap.rearLeftDrive.setPower(lr);
        robotMap.rearRightDrive.setPower(rr);
    }
    
    public void TankDrive(double LeftStickY, double RightStickY) {
        robotMap.SetDriveModeNoEncoders();
        
        robotMap.frontLeftDrive.setPower(LeftStickY);
        robotMap.frontRightDrive.setPower(RightStickY);
        robotMap.rearLeftDrive.setPower(-LeftStickY);
        robotMap.rearRightDrive.setPower(-RightStickY);
    }
    
    public void KillMotorsPower() {
        robotMap.SetDriveModeNoEncoders();
        robotMap.frontLeftDrive.setPower(0);
        robotMap.frontRightDrive.setPower(0);
        robotMap.rearLeftDrive.setPower(0);
        robotMap.rearRightDrive.setPower(0);
    }
    
    public void DriveForTicks(int targetFL, int targetFR, int targetRL, int targetRR) {
        robotMap.ResetDriveEncoders();
        robotMap.SetDriveModeEncoders();
        
        
        robotMap.frontLeftDrive.setTargetPosition(targetFL);
        robotMap.frontLeftDrive.setPower(0.6);
        
        robotMap.frontRightDrive.setTargetPosition(targetFR);
        robotMap.frontRightDrive.setPower(0.6);
        
        robotMap.rearLeftDrive.setTargetPosition(targetRL);
        robotMap.rearLeftDrive.setPower(0.6);
        
        robotMap.rearRightDrive.setTargetPosition(targetRR);
        robotMap.rearRightDrive.setPower(0.6);
    }
    
    
    
}
