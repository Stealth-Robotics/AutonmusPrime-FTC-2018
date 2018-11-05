package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.AutonomousPrimeRobotCommands;

@Autonomous(name = "Autonomous Prime Auto Drive Straight", group = "Iterative Opmode")
@Disabled
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
        robotCommands.TurnToAngle(90);
        /*try {
            robotCommands.TurnToAngle(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
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
        telemetry.addData("CurrentAngle", robotCommands.RobotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle);

        //call the loop function on the robot map
        robotCommands.RobotMap.Loop();
    }

    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
