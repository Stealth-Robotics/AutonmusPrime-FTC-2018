package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.AutonomousPrimeRobotCommands;
import org.firstinspires.ftc.teamcode.Commands.DropHang;
import org.firstinspires.ftc.teamcode.Commands.TelemetryLog;

@Autonomous(name = "Auto Silver", group = "Iterative Opmode")

public class Auto_Silver extends OpMode {

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
        TelemetryLog.Run(robotCommands, telemetry);

        //call the loop function on the robot map
        robotCommands.RobotMap.Loop();
    }

    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
