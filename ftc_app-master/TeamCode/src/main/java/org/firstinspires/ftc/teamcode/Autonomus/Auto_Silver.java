package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.ClimbDown;
import org.firstinspires.ftc.teamcode.Commands.ClimbFeet.ClimbForTicks;
import org.firstinspires.ftc.teamcode.Commands.ClimbHook.OpenClimbHook;
import org.firstinspires.ftc.teamcode.Commands.Hold;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.CenterPathSilver;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.LeftPathSilver;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.RightPathSilver;
import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

@Autonomous(name="Auto Silver", group="Iterative Opmode")

public class Auto_Silver extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
        Robot.getInstance().setAutoPosition(AutoPosition.Silver);
        Robot.getInstance().setMineralPosition(MineralPosition.Right);

        commandManager = new CommandManager();

        //commandManager.AddConstantCommand(new TelemetryLogger());

        commandManager.AddCommand(new MineralPositionDetection(1, true));
        //climb down
        commandManager.AddCommand(new ClimbDown(2));
        //knock off path
        commandManager.AddCommand(new LeftPathSilver(3));
        commandManager.AddCommand(new CenterPathSilver(3));
        commandManager.AddCommand(new RightPathSilver(3));

        //put the climb feet back to 0 for Teleop
        //commandManager.AddCommand(new ClimbForTicks(10,0,0));
    }
    
    @Override
    public void init_loop() {
        
    }

    @Override
    public void start() {
        commandManager.Start();
    }

    @Override
    public void loop() {
        commandManager.Run();

        Robot.getInstance().getRobotMap().Loop();
    }
    
    @Override
    public void stop() {
        DriveBase.KillDriveMotorPower();
        commandManager.Stop();
    }
}
