package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.TelemetryLogger;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.Systems.ClimbFeet;
import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;

@TeleOp(name="TELEOP Main", group="Iterative Opmode")

public class Teleop_Main extends OpMode {
    
    @Override
    public void init() {
        Robot.getInstance().setTelemetry(telemetry);
        Robot.getInstance().setRobotMap(new RobotMap(hardwareMap));
    }
    
    @Override
    public void init_loop() {
        
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        //region POV Drive
            //a Dead Zone for the triggers so that the robot will actually move if one isn't pressed
            double deadZone = 0.075;

            //calculate drive value from triggers instead of using the analog stick to do it.
            double drive = 0;
            if(gamepad1.left_trigger > deadZone){
                drive = gamepad1.left_trigger;
            } else if (gamepad1.right_trigger > deadZone){
                drive  = -gamepad1.right_trigger;
            }

            DriveBase.POVDrive(gamepad1.left_stick_x, drive);
        //endregion

        //region Climb Feet Drive
            deadZone = 0.1;
            int Speed = 50;
            float left, right;

            if(gamepad2.left_stick_y > deadZone || gamepad2.left_stick_y < -deadZone){
                left = gamepad2.left_stick_y;
            } else {
                left = 0.0f;
            }

            if(gamepad2.right_stick_y > deadZone || gamepad2.right_stick_y < -deadZone){
                right = gamepad2.right_stick_y;
            } else {
                right = 0.0f;
            }

            ClimbFeet.ClimbFeetDriveTicks((int)(ClimbFeet.GetLeftTarget() + (left * Speed)), (int)(ClimbFeet.GetRightTarget() + (right * Speed)));
        //endregion

        //region Climb Hook
            if(gamepad2.a){
                ClimbHook.OpenHook();
            }

            if(gamepad2.b){
                ClimbHook.CloseHook();
            }
        //endregion

        //region Telemetry
            TelemetryLogger tm = new TelemetryLogger();
            tm.Run(-1);
        //endregion

        //call the loop function on the robot map
        Robot.getInstance().getRobotMap().Loop();
    }
    
    @Override
    public void stop() {
        DriveBase.KillDriveMotorPower();
    }
}
