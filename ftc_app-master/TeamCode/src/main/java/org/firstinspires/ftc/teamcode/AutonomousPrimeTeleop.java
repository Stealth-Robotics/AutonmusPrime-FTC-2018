package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
            robotCommands.POVDrive(drive ,gamepad1.left_stick_x);
        //endregion

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
