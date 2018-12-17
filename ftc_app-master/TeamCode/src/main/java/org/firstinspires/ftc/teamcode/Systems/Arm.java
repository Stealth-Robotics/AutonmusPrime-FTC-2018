package org.firstinspires.ftc.teamcode.Systems;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.Mathf;

public class Arm {

    //TODO: Tune Max and Min Ticks
    public static int MaxTicks = 3500;
    public static int MinTicks = -3500;

    //region ArmRotation
    public static void RotateArmToTarget(int target){
        target = Mathf.clamp(target, Arm.MinTicks, Arm.MaxTicks);

        Robot.getInstance().getRobotMap().SetArmModeEncoder();

        Robot.getInstance().getRobotMap().armRotationMotor.setTargetPosition(target);
        Robot.getInstance().getRobotMap().armRotationMotor.setPower(0.45);
    }
    //endregion ArmRotation

    //region GrabberRotation
    public static void RotateGrabber(double target){
        target = Mathf.clamp(target, 0, 1);

        Robot.getInstance().getRobotMap().grabberRotation.setPosition(target);
    }
    //endregion GrabberRotation

    //region IntakeSpinner
    public static void IntakeIn(){
        Robot.getInstance().getRobotMap().intakeSpinner.setPosition(1);
    }

    public static void IntakeOut(){
        Robot.getInstance().getRobotMap().intakeSpinner.setPosition(0);
    }

    public static void IntakeStop(){
        Robot.getInstance().getRobotMap().intakeSpinner.setPosition(0.5);
    }
    //endregion IntakeSpinner

    //region ExtendRelease
    public static void ReleaseArm(){
        Robot.getInstance().getRobotMap().armExtendRelease.setPosition(0);
    }

    public static void DeReleaseArm(){
        Robot.getInstance().getRobotMap().armExtendRelease.setPosition(1);
    }
    //endregion ExtendRelease

}
