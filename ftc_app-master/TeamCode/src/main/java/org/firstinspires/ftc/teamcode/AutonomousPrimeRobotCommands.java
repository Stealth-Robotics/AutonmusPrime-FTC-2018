package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Utils.StopWatch;

public class AutonomousPrimeRobotCommands {
    public AutonomousPrimeRobotMap RobotMap;
    //public Object SyncObject;
    private ElapsedTime ElapsedTimer;
    
    public AutonomousPrimeRobotCommands(HardwareMap HwMap) {
        
        RobotMap = new AutonomousPrimeRobotMap(HwMap);

        ElapsedTime.Resolution res = com.qualcomm.robotcore.util.ElapsedTime.Resolution.MILLISECONDS;
        ElapsedTimer = new ElapsedTime(res);
    }

    //region Base Drive

    public void MechanumDrive(double LeftStickX, double LeftStickY, double RightStickX) {
        RobotMap.SetDriveModeNoEncoders();
        
        double actualAngle = RobotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
        
        final double x = Math.pow(-LeftStickX, 3.0);
        final double y = Math.pow(LeftStickY, 3.0);
        
        final double rotation = Math.pow(RightStickX, 3.0);
        final double direction = Math.atan2(x, y) + actualAngle;
        final double speed = Math.min(1.0, Math.sqrt(x * x + y * y));
        
        final double lf = speed * Math.sin(direction + Math.PI / 4.0) + rotation;
        final double rf = speed * Math.cos(direction + Math.PI / 4.0) - rotation;
        final double lr = speed * Math.cos(direction + Math.PI / 4.0) + rotation;
        final double rr = speed * Math.sin(direction + Math.PI / 4.0) - rotation;
        
        RobotMap.frontLeftDrive.setPower(lf);
        RobotMap.frontRightDrive.setPower(rf);
        RobotMap.rearLeftDrive.setPower(lr);
        RobotMap.rearRightDrive.setPower(rr);
    }
    
    public void TankDrive(double LeftStickY, double RightStickY) {
        RobotMap.SetDriveModeNoEncoders();
        
        RobotMap.frontLeftDrive.setPower(LeftStickY);
        RobotMap.frontRightDrive.setPower(RightStickY);
        RobotMap.rearLeftDrive.setPower(-LeftStickY);
        RobotMap.rearRightDrive.setPower(-RightStickY);
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

        RobotMap.frontLeftDrive.setPower(leftPower * frontWheelPowerPercent);
        RobotMap.frontRightDrive.setPower(rightPower * frontWheelPowerPercent);
        RobotMap.rearLeftDrive.setPower(leftPower * rearWheelPowerPercent);
        RobotMap.rearRightDrive.setPower(rightPower * rearWheelPowerPercent);
    }
    
    public void KillDriveMotorPower() {
        RobotMap.SetDriveModeNoEncoders();
        RobotMap.frontLeftDrive.setPower(0);
        RobotMap.frontRightDrive.setPower(0);
        RobotMap.rearLeftDrive.setPower(0);
        RobotMap.rearRightDrive.setPower(0);
    }
    
    public void DriveForTicks(int targetL, int targetR) {
        RobotMap.ResetDriveEncoders();
        RobotMap.SetDriveModeEncoders();
        
        RobotMap.frontLeftDrive.setTargetPosition(targetL);
        RobotMap.frontLeftDrive.setPower(0.8);
        
        RobotMap.frontRightDrive.setTargetPosition(targetR);
        RobotMap.frontRightDrive.setPower(0.8);
    }

    //region TurnToAnglePID
    private double ANGLEPID_Kp = 0.333, ANGLEPID_Ki = 0, ANGLEPID_Kd = 0;
    private double ANGLEPID_targetAngle = 0, ANGLEPID_previousError, ANGLEPID_integral = 0, ANGLEPID_MinAcceptableRange = -10, ANGLEPID_MaxAcceptableRange = 10;
    private boolean ANGLEPID_Active = false;

    public void TurnToAngle(double targetAngle) /*throws InterruptedException*/ {
        ANGLEPID_targetAngle = targetAngle;
        RobotMap.SetDriveModeNoEncoders();
        ANGLEPID_Active = true;

        //reset the integral
        ANGLEPID_integral = 0;

        do {
            double dt = ElapsedTimer.milliseconds();
            ANGLEPID_Active = AnglePID(dt);
            //wait(20);
            ElapsedTimer.reset();
        } while (ANGLEPID_Active);

        /*synchronized (SyncObject){
            SyncObject.notify();
        }*/
    }

    private boolean AnglePID(double dt) {
        double currentAngle = RobotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle;
        double error = ANGLEPID_targetAngle - currentAngle;
        ANGLEPID_integral = ANGLEPID_integral + error * dt;
        double derivative = (error - ANGLEPID_previousError) / dt;
        double output = ANGLEPID_Kp * error + ANGLEPID_Ki * ANGLEPID_integral + ANGLEPID_Kd * derivative;
        ANGLEPID_previousError = error;

        output = Range.clip(output,-1,1);

        RobotMap.frontLeftDrive.setPower(output);
        RobotMap.rearLeftDrive.setPower(output);
        RobotMap.frontRightDrive.setPower(-output);
        RobotMap.rearRightDrive.setPower(-output);

        if(error < ANGLEPID_MinAcceptableRange && error > ANGLEPID_MaxAcceptableRange){
            return true;
        }
        return false;
    }
    //endregion

    //endregion

    //region Climb Feet

    //region Auto Commands
    public void ClamFeetAutoDrive(double powerL, double powerR, int driveTime) // power - power for the left clam foot motor and right clam foot motor, driveTime - amount of time to drive the motors for
    {

        StopWatch clamFootStopWatch = new StopWatch(driveTime);

        while(!clamFootStopWatch.isExpired()) // while stop watch not expired
        {
            RobotMap.leftClamFoot.setPower(powerL);
            RobotMap.rightClamFoot.setPower(powerR);
        }

        RobotMap.leftClamFoot.setPower(0);
        RobotMap.rightClamFoot.setPower(0);
        clamFootStopWatch.reset();
    }
    //endregion

    //region Manual Commands
    public void ClimbFeetDrive(double leftPower, double rightPower){

        RobotMap.leftClamFoot.setPower(leftPower);
        RobotMap.rightClamFoot.setPower(rightPower);
    }
    //endregion

    //region PID Commands

    //endregion

    //endregion

    //region Climb Hook
    public void OpenHook(){
        RobotMap.climbHook.setPosition(180);
    }

    public void CloseHook(){
        RobotMap.climbHook.setPosition(0);
    }
    //endregion
}
