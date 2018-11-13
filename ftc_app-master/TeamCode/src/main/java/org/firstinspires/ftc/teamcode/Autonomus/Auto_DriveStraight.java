package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.RobotCommands;

@Autonomous(name = "Literally Drive Straight", group = "Iterative Opmode")
@Disabled
public class Auto_DriveStraight extends OpMode {

    private RobotCommands robotCommands;
    
    @Override
    public void init() {
        robotCommands = new RobotCommands(hardwareMap);
    }
    
    @Override
    public void init_loop() {
        
    }

    @Override
    public  void start() {
        robotCommands.DriveForTicks(5000, 5000);
    }

    @Override
    public void loop() {
        telemetry.addData("Encoder_FR", robotCommands.RobotMap.frontRightDrive.getCurrentPosition());
        telemetry.addData("Motor_FR", robotCommands.RobotMap.frontRightDrive.getPower());
        telemetry.addData("Encoder_FL", robotCommands.RobotMap.frontLeftDrive.getCurrentPosition());
        telemetry.addData("Motor_FL", robotCommands.RobotMap.frontLeftDrive.getPower());
        telemetry.addData("Motor_RR", robotCommands.RobotMap.rearRightDrive.getPower());
        telemetry.addData("Motor_RL", robotCommands.RobotMap.rearLeftDrive.getPower());
        telemetry.addData("CurrentAngle", robotCommands.RobotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);

        //call the loop function on the robot map
        robotCommands.RobotMap.Loop();
    }

    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
