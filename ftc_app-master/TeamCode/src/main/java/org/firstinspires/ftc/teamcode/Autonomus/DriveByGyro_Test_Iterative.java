package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.ClimbFeet.ClimbForTicks;
import org.firstinspires.ftc.teamcode.Commands.ClimbHook.OpenClimbHook;
import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveByGyro;
import org.firstinspires.ftc.teamcode.Commands.DriveBase.TurnByGyro;
import org.firstinspires.ftc.teamcode.Commands.Hold;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.FlipMarkerDroper;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.UnFlipMarkerDroper;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralCenterPath;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralLeftPath;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralRightPath;
import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;

@Autonomous(name="Drive By Gyro Test", group="Iterative Opmode")
@Disabled
public class DriveByGyro_Test_Iterative extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
        Robot.getInstance().setAutoPosition(AutoPosition.Gold);
        //Robot.getInstance().setMineralPosition(MineralPosition.Center);

        commandManager = new CommandManager();

        //commandManager.AddConstantCommand(new TelemetryLogger());

        commandManager.AddCommand(new DriveByGyro(1, DriveBase.DRIVE_SPEED, 12));
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
