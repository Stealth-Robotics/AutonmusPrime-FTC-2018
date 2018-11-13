package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotCommands;
import org.firstinspires.ftc.teamcode.Commands.DropHang;
import org.firstinspires.ftc.teamcode.Commands.TelemetryLog;

@Autonomous(name = "Auto Silver", group = "Iterative Opmode")

public class Auto_Silver extends OpMode {

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

        DropHang.Run(robotCommands);

        robotCommands.DriveForTicks(2000, 2000);
    }

    @Override
    public void loop() {
        TelemetryLog.Run(robotCommands, telemetry);

        //call the loop function on the robot map
        robotCommands.RobotMap.Loop();
    }

    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
