package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@TeleOp(name="Autonomous Prime TELEOP", group="Iterative Opmode")

public class AutonomousPrimeTeleop extends OpMode {

    private AutonomousPrimeRobotCommands robotCommands;
    
    @Override
    public void init() {
        robotCommands = new AutonomousPrimeRobotCommands(hardwareMap);
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
            double deadZone = 0.1;
            //calculate drive value from triggers instead of using the analog stick to do it.
            double drive = 0;
            if(gamepad1.left_trigger > deadZone){
                drive = gamepad1.left_trigger;
            } else if (gamepad1.right_trigger > deadZone){
                drive  = -gamepad1.right_trigger;
            }
            robotCommands.POVDrive(gamepad1.left_stick_x, drive);
        //endregion

        //region Clam Feet Drive
        deadZone = 0.1;
        double left, right;

        if(gamepad2.left_stick_y > deadZone || gamepad2.left_stick_y < -deadZone){
            left = gamepad2.left_stick_y;
        } else {
            left = 0.0;
        }

        if(gamepad2.right_stick_y > deadZone || gamepad2.right_stick_y < -deadZone){
            right = gamepad2.right_stick_y;
        } else {
            right = 0.0;
        }

        robotCommands.ClamFeetManualDrive(left, right);
        //endregion

        //region Climb Hook
        if(gamepad2.a){
            robotCommands.OpenHook();
        }

        if(gamepad2.b){
            robotCommands.CloseHook();
        }
        //endregion

        telemetry.addData("Encoder_FR", robotCommands.RobotMap.frontRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_FL", robotCommands.RobotMap.frontLeftDrive.getCurrentPosition());
        telemetry.addData("CurrentAngle", robotCommands.RobotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle);
        telemetry.addData("HookPosition", robotCommands.RobotMap.climbHook.getPosition());


        //call the loop function on the robot map
        robotCommands.RobotMap.Loop();
        
    }

    
    
    @Override
    public void stop() {
        robotCommands.KillDriveMotorPower();
    }
}
