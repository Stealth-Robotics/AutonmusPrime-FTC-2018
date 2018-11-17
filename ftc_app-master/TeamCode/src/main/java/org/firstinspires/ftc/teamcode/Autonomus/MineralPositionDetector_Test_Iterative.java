package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;

@Autonomous(name="Mineral Position Detector Iterative", group="Iterative Opmode")

public class MineralPositionDetector_Test_Iterative extends OpMode {

    private CommandManager commandManager;

    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));

        commandManager = new CommandManager();

        commandManager.AddCommand(new MineralPositionDetection(1, false));
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
