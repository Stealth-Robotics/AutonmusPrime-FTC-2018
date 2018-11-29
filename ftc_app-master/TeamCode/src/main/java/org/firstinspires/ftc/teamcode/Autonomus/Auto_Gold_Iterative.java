package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.ClimbFeet.ClimbForTicks;
import org.firstinspires.ftc.teamcode.Commands.ClimbHook.OpenClimbHook;
import org.firstinspires.ftc.teamcode.Commands.Hold;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.FlipMarkerDroper;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.UnFlipMarkerDroper;
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
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

@Autonomous(name="Auto Gold Iterative", group="Iterative Opmode")

public class Auto_Gold_Iterative extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
        Robot.getInstance().setAutoPosition(AutoPosition.Gold);
        //Robot.getInstance().setMineralPosition(MineralPosition.Center);

        commandManager = new CommandManager();

        //commandManager.AddConstantCommand(new TelemetryLogger());

        commandManager.AddCommand(new MineralPositionDetection(1, true));
        //climb down
        commandManager.AddCommand(new ClimbForTicks(2, 3400, 3400));
        commandManager.AddCommand(new OpenClimbHook(3));
        commandManager.AddCommand(new ClimbForTicks(4, -500, -500));
        commandManager.AddCommand(new Hold(5, 100));
        //knock off path
        commandManager.AddCommand(new MineralLeftPath(6));
        commandManager.AddCommand(new MineralCenterPath(6));
        commandManager.AddCommand(new MineralRightPath(6));

        //put the climb feet back to 0 for Teleop
        commandManager.AddCommand(new ClimbForTicks(10,0,0));
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
