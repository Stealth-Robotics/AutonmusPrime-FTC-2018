package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Autonomous Prime Auto Drive Straight", group = "Iterative Opmode")

public class AutonomousPrime_Auto_DriveStraight extends OpMode {

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
        try {
            robotCommands.TurnToAngle(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*synchronized (robotCommands.SyncObject){
            try {
                robotCommands.SyncObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        robotCommands.DriveForTicks(1000, 1000);

    }

    @Override
    public void loop() {
        telemetry.addData("Encoder_FR", robotCommands.RobotMap.frontRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_FL", robotCommands.RobotMap.frontLeftDrive.getCurrentPosition());

        //call the loop function on the robot map
        robotCommands.RobotMap.Loop();
    }

    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
