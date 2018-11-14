package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralCKnockOff_G;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralLKnockOff_G;
import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralRKnockOff_G;
import org.firstinspires.ftc.teamcode.RobotCommands;
import org.firstinspires.ftc.teamcode.Commands.DropHang;
import org.firstinspires.ftc.teamcode.Commands.TelemetryLog;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

@Autonomous(name = "Auto Gold", group = "Iterative Opmode")

public class Auto_Gold extends OpMode {

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

        //detect witch position the cube is in!
        MineralPosition minPos;
        MineralPositionDetection minPosDector = new MineralPositionDetection(hardwareMap, telemetry);
        minPos = minPosDector.Run();

        //run the correct code to go the the correct spot and move the cube off
        if(minPos == MineralPosition.Left){
            MineralLKnockOff_G.Run(robotCommands);
        } else if(minPos == MineralPosition.Center){
            MineralCKnockOff_G.Run(robotCommands);
        } else {
            MineralRKnockOff_G.Run(robotCommands);
        }
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
