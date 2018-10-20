package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Autonmus Prime TELEOP", group="Iterative Opmode")

public class AutonmusPrimeTeleop extends OpMode {

    private AutonmusPrimeRobotCommands robotCommands;
    
    @Override
    public void init() {
        robotCommands = new AutonmusPrimeRobotCommands(hardwareMap);
        
    }
    
    @Override
    public void init_loop() {
        
    }

    
    @Override
    public void start() {
        
    }

    @Override
    public void loop() {
        robotCommands.TankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y);
        
        //robotCommands.Drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        
        telemetry.addData("Encoder_RR", robotCommands.robotMap.rearRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_RL", robotCommands.robotMap.rearLeftDrive.getCurrentPosition());
        telemetry.addData("Encoder_FR", robotCommands.robotMap.frontRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_FL", robotCommands.robotMap.frontLeftDrive.getCurrentPosition());
        
    }

    
    
    @Override
    public void stop() {
        robotCommands.KillMotorsPower();
    }
}
