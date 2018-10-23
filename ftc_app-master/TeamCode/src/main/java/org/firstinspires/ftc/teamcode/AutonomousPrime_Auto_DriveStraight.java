package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "Autonomous Prime Auto Drive Straight", group = "Iterative Opmode")

public class AutonomousPrime_Auto_DriveStraight extends OpMode {

    private AutonomousPrimeRobotCommands robotCommands;
    
    @Override
    public void init() {
        robotCommands = new AutonomousPrimeRobotCommands(hardwareMap);
    }
    
    @Override
    public void init_loop() {
        
    }

    
    @Override
    public void start() {
        robotCommands.DriveForTicks(1000, 1000);
    }

    @Override
    public void loop() {
        telemetry.addData("Encoder_RR", robotCommands.robotMap.rearRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_RL", robotCommands.robotMap.rearLeftDrive.getCurrentPosition());
        telemetry.addData("Encoder_FR", robotCommands.robotMap.frontRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_FL", robotCommands.robotMap.frontLeftDrive.getCurrentPosition());

        //call the loop function on the robot map
        robotCommands.robotMap.Loop();
        
    }

    
    
    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
