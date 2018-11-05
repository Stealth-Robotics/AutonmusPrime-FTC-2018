package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.Set;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.hardware.bosch.BNO055IMU;
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

    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor rearLeftDrive;
    public DcMotor rearRightDrive;

    public DcMotor leftClamFoot;
    public DcMotor rightClamFoot;

    public Servo climbHook;
    
    // The IMU sensor object
    public BNO055IMU imu;
    
    public AutonomousPrimeRobotMap(HardwareMap HwMap) {

        hardwareMap = HwMap;

        //region Drive Base

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "1:0");
        frontRightDrive = hardwareMap.get(DcMotor.class, "1:1");
        rearLeftDrive = hardwareMap.get(DcMotor.class, "1:2");
        rearRightDrive = hardwareMap.get(DcMotor.class, "1:3");
        
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        SetDriveModeNoEncoders();
        
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        rearLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        rearRightDrive.setDirection(DcMotor.Direction.REVERSE);

        //endregion

        //region Clam Feet
        leftClamFoot = hardwareMap.get(DcMotor.class, "2:1");
        rightClamFoot = hardwareMap.get(DcMotor.class, "2:2");

        leftClamFoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightClamFoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftClamFoot.setDirection(DcMotor.Direction.FORWARD);
        rightClamFoot.setDirection(DcMotor.Direction.REVERSE);
        //endregion

        climbHook = hardwareMap.get(Servo.class, "Servo1:1");
        
        initIMU();
    }

    //NOTE: IT IS IMPORTANT THAT THIS IS IMPLEMENTED IN THE LOOP FUNCTION OF THE OPMODE
    public void Loop() {
        //if the drive mode is encoders then mirror the rear motors so that we just use the encoders of the front motors
        if(driveMode == DriveMode.Run_With_Encoders){
            rearLeftDrive.setPower(frontLeftDrive.getPower());
            rearRightDrive.setPower(frontRightDrive.getPower());
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
}
