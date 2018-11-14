package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Commands.Sounds;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

@Autonomous(name = "Mineral Pos Detection Tester", group = "Iterative Opmode")
//@Disabled
public class MineralPositionDetection_Tester extends OpMode {
    
    @Override
    public void init() {
    }
    
    @Override
    public void init_loop() {
        
    }

    @Override
    public  void start() {
        //play autobots roll out sound
        Sounds.PlayAutobotsRollOut(hardwareMap);

        //detect witch position the cube is in!
        MineralPosition minPos;
        MineralPositionDetection minPosDector = new MineralPositionDetection(hardwareMap, telemetry);
        minPos = minPosDector.Run();

        telemetry.addData("CubePos", minPos.toString());

        telemetry.update();
    }

    @Override
    public void loop() {
    }

    @Override
    public void stop() {
    }
}
