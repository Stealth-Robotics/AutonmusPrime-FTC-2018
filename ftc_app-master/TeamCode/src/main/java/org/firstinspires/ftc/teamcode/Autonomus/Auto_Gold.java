package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.Arm.RotateArmToTick;
import org.firstinspires.ftc.teamcode.Commands.ClimbDown;
import org.firstinspires.ftc.teamcode.Commands.ClimbFeet.ClimbForTicks;
import org.firstinspires.ftc.teamcode.Commands.ClimbHook.OpenClimbHook;
import org.firstinspires.ftc.teamcode.Commands.Hold;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.CenterPathGold;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.LeftPathGold;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.RightPathGold;
import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

@Autonomous(name="Auto Gold", group="Iterative Opmode")

public class Auto_Gold extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
        Robot.getInstance().setAutoPosition(AutoPosition.Gold);
        Robot.getInstance().setMineralPosition(MineralPosition.Left);

        commandManager = new CommandManager();

        //commandManager.AddConstantCommand(new TelemetryLogger());
        //commandManager.AddCommand(new RotateArmToTick(1, 2250));

        commandManager.AddCommand(new MineralPositionDetection(2, true));
        //climb down
        commandManager.AddCommand(new ClimbDown(3));
        //knock off path
        commandManager.AddCommand(new LeftPathGold(4));
        commandManager.AddCommand(new CenterPathGold(4));
        commandManager.AddCommand(new RightPathGold(4));

        //put the climb feet back to 0 for Teleop
        //commandManager.AddCommand(new ClimbForTicks(10,0,0));

        Robot.getInstance().getRobotMap().climbHook.setPosition(0.6);
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
        Robot.getInstance().StopVuforia();
        commandManager.Stop();
    }
}
