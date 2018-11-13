package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.AutonomousPrimeRobotCommands;
import org.firstinspires.ftc.teamcode.Commands.DropHang;

@Autonomous(name = "Auto Gold", group = "Iterative Opmode")

public class Auto_Gold extends OpMode {

    private AutonomousPrimeRobotCommands robotCommands;
    
    @Override
    public void init() {
        robotCommands = new AutonomousPrimeRobotCommands(hardwareMap);
    }
    
    @Override
    public void init_loop() {
        
    }

    @Override
    public  void start() {

        DropHang.Run(robotCommands);

        robotCommands.DriveForTicks(2000, 2000);
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
