package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutonomousPrimeRobotCommands {
    public AutonomousPrimeRobotMap robotMap;
    
    public AutonomousPrimeRobotCommands(HardwareMap HwMap) {
        
        robotMap = new AutonomousPrimeRobotMap(HwMap);
    }
    
    public void MechanumDrive(double LeftStickX, double LeftStickY, double RightStickX) {
        robotMap.SetDriveModeNoEncoders();
        
        double actualAngle = robotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
        
        final double x = Math.pow(-LeftStickX, 3.0);
        final double y = Math.pow(LeftStickY, 3.0);
        
        final double rotation = Math.pow(RightStickX, 3.0);
        final double direction = Math.atan2(x, y) + actualAngle;
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

    public void POVDrive(double Drive, double Turn){
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;
        double frontWheelPowerPercent = 0.8;
        double rearWheelPowerPercent = 1;

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        leftPower = Range.clip(Drive + Turn, -1.0, 1.0) ;
        rightPower = Range.clip(Drive - Turn, -1.0, 1.0) ;

        robotMap.frontLeftDrive.setPower(leftPower * frontWheelPowerPercent);
        robotMap.frontRightDrive.setPower(rightPower * frontWheelPowerPercent);
        robotMap.rearLeftDrive.setPower(leftPower * rearWheelPowerPercent);
        robotMap.rearRightDrive.setPower(rightPower * rearWheelPowerPercent);
    }
    
    public void KillDriveMotorPower() {
        robotMap.SetDriveModeNoEncoders();
        robotMap.frontLeftDrive.setPower(0);
        robotMap.frontRightDrive.setPower(0);
        robotMap.rearLeftDrive.setPower(0);
        robotMap.rearRightDrive.setPower(0);
    }
    
    public void DriveForTicks(int targetL, int targetR) {
        robotMap.ResetDriveEncoders();
        robotMap.SetDriveModeEncoders();
        
        robotMap.frontLeftDrive.setTargetPosition(targetL);
        robotMap.frontLeftDrive.setPower(0.8);
        
        robotMap.frontRightDrive.setTargetPosition(targetR);
        robotMap.frontRightDrive.setPower(0.8);
    }
    
    
    
}
