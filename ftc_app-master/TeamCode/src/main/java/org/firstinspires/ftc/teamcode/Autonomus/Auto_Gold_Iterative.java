package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.ClimbFeet.ClimbForTicks;
import org.firstinspires.ftc.teamcode.Commands.ClimbHook.OpenClimbHook;
import org.firstinspires.ftc.teamcode.Commands.Hold;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralCenterPath;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralLeftPath;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralRightPath;
import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Commands.TelemetryLogger;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;

@Autonomous(name="Auto Gold Iterative", group="Iterative Opmode")

public class Auto_Gold_Iterative extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
        Robot.getInstance().setAutoPosition(AutoPosition.Gold);

        commandManager = new CommandManager();

        commandManager.AddConstantCommand(new TelemetryLogger());

        commandManager.AddCommand(new MineralPositionDetection(1, true));
        commandManager.AddCommand(new ClimbForTicks(2, 4000, 4000));
        commandManager.AddCommand(new Hold(3, 100));
        commandManager.AddCommand(new OpenClimbHook(4));
        commandManager.AddCommand(new Hold(5, 500));
        commandManager.AddCommand(new ClimbForTicks(6, -4100, -4100));
        commandManager.AddCommand(new Hold(7, 100));
        commandManager.AddCommand(new MineralLeftPath(8));
        commandManager.AddCommand(new MineralCenterPath(8));
        commandManager.AddCommand(new MineralRightPath(8));
    }
    
    @Override
    public void init_loop() {
        
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        commandManager.Run();

        Robot.getInstance().getRobotMap().Loop();

        if(Robot.getInstance().getMineralPosition() != null){
            Robot.getInstance().getTelemetry().addData("Cube Position", Robot.getInstance().getMineralPosition().toString());
        }

        Robot.getInstance().getTelemetry().update();
    }

    
    
    @Override
    public void stop() {
        DriveBase.KillDriveMotorPower();
    }
}
