package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

@Autonomous(name="Mineral Position Detector Iterative", group="Iterative Opmode")
@Disabled
public class MineralPositionDetector_Test_Iterative extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
        Robot.getInstance().setAutoPosition(AutoPosition.Gold);
        //Robot.getInstance().setMineralPosition(MineralPosition.Unidentified);

        commandManager = new CommandManager();

        commandManager.AddCommand(new MineralPositionDetection(1, false));
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
