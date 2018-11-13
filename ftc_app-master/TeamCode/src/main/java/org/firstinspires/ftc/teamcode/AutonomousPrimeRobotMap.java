package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.Set;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import android.content.Context;

public class AutonomousPrimeRobotMap {
    
    private HardwareMap hardwareMap;

    public enum DriveMode{
        Run_With_Encoders,
        Run_Without_Encoders
    }

    public DriveMode driveMode = DriveMode.Run_Without_Encoders;

    public DcMotorEx frontLeftDrive;
    public DcMotorEx frontRightDrive;
    public DcMotorEx rearLeftDrive;
    public DcMotorEx rearRightDrive;

    public DcMotorEx leftClamFoot;
    public DcMotorEx rightClamFoot;

    public Servo climbHook;
    
    // The IMU sensor object
    public BNO055IMU imu;
    
    public AutonomousPrimeRobotMap(HardwareMap HwMap) {

        hardwareMap = HwMap;

        //region Drive Base

        frontLeftDrive  = hardwareMap.get(DcMotorEx.class, "1:0");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "1:1");
        rearLeftDrive = hardwareMap.get(DcMotorEx.class, "1:2");
        rearRightDrive = hardwareMap.get(DcMotorEx.class, "1:3");
        
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        SetDriveModeNoEncoders();
        
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        rearLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        rearRightDrive.setDirection(DcMotor.Direction.REVERSE);

        //endregion

        //region Clam Feet
        leftClamFoot = hardwareMap.get(DcMotorEx.class, "2:0");
        rightClamFoot = hardwareMap.get(DcMotorEx.class, "2:1");

        leftClamFoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightClamFoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftClamFoot.setDirection(DcMotor.Direction.FORWARD);
        rightClamFoot.setDirection(DcMotor.Direction.REVERSE);
        //endregion

        climbHook = hardwareMap.get(Servo.class, "Servo2:0");
        
        initIMU();
    }

    //NOTE: IT IS IMPORTANT THAT THIS IS IMPLEMENTED IN THE LOOP FUNCTION OF THE OPMODE
    public void Loop() {
        //if the drive mode is encoders then mirror the rear motors so that we just use the encoders of the front motors
        if(driveMode == DriveMode.Run_With_Encoders){
            rearLeftDrive.setVelocity(frontLeftDrive.getVelocity());
            rearRightDrive.setVelocity(frontRightDrive.getVelocity());
        }
    }
    
    public void SetDriveModeNoEncoders() {
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveMode = DriveMode.Run_Without_Encoders;
    }
    
    public void SetDriveModeEncoders() {
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //set the back two motors to run without encoders so that we can just mirror the power being sent to the front wheels with the encoders
        rearLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveMode = DriveMode.Run_With_Encoders;
    }
    
    public void ResetDriveEncoders() {
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveMode = DriveMode.Run_With_Encoders;
    }
    
    private void initIMU() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void ResetClimbEncoders() {
        leftClamFoot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightClamFoot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void SetClimbModeEncoders() {
       leftClamFoot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       rightClamFoot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void SetClimbModeNoEncoders() {
        leftClamFoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightClamFoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
