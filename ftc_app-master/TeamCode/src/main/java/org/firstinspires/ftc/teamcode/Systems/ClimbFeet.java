package org.firstinspires.ftc.teamcode.Systems;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.Mathf;

public class ClimbFeet {

    public static int MaxTicks = 4000;
    public static int MinTicks = -500;

    public static void ClimbFeetDrive(double leftPower, double rightPower){
        Robot.getInstance().getRobotMap().SetClimbModeNoEncoders();

        Robot.getInstance().getRobotMap().leftClamFoot.setPower(leftPower);
        Robot.getInstance().getRobotMap().rightClamFoot.setPower(rightPower);
    }

    public static void ClimbFeetDriveTicks(int targetL, int targetR){
        targetL = Mathf.clamp(targetL, ClimbFeet.MinTicks, ClimbFeet.MaxTicks);
        targetR = Mathf.clamp(targetR, ClimbFeet.MinTicks, ClimbFeet.MaxTicks);

        Robot.getInstance().getRobotMap().SetClimbModeEncoders();

        Robot.getInstance().getRobotMap().rightClamFoot.setTargetPosition(-targetL);
        Robot.getInstance().getRobotMap().rightClamFoot.setPower(0.8);

        Robot.getInstance().getRobotMap().leftClamFoot.setTargetPosition(-targetR);
        Robot.getInstance().getRobotMap().leftClamFoot.setPower(0.8);
    }

    public static int GetLeftTarget() {
        return Robot.getInstance().getRobotMap().leftClamFoot.getTargetPosition();
    }

    public static int GetRightTarget() {
        return Robot.getInstance().getRobotMap().rightClamFoot.getTargetPosition();
    }

    public static void ResetEncoders() {
        Robot.getInstance().getRobotMap().ResetClimbEncoders();
    }
}
