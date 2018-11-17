package org.firstinspires.ftc.teamcode.Systems;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.StopWatch;

public class ClimbFeet {

    public static void ClimbFeetDrive(double leftPower, double rightPower){
        Robot.getInstance().getRobotMap().SetClimbModeNoEncoders();

        Robot.getInstance().getRobotMap().leftClamFoot.setPower(leftPower);
        Robot.getInstance().getRobotMap().rightClamFoot.setPower(rightPower);
    }
}
