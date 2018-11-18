package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.iConstantCommand;

public class TelemetryLogger implements iConstantCommand {

    public TelemetryLogger(){
    }

    public void Run(double dt){
        Robot.getInstance().getTelemetry().addData("Elapsed Time", dt);

        if(Robot.getInstance().getMineralPosition() != null){
            Robot.getInstance().getTelemetry().addData("Cube Position", Robot.getInstance().getMineralPosition().toString());
        }

        Robot.getInstance().getTelemetry().addData("Encoder_FR", Robot.getInstance().getRobotMap().frontRightDrive.getCurrentPosition());
        Robot.getInstance().getTelemetry().addData("Encoder_FL", Robot.getInstance().getRobotMap().frontLeftDrive.getCurrentPosition());

        Robot.getInstance().getTelemetry().addData("Encoder_ClimbL", Robot.getInstance().getRobotMap().leftClamFoot.getCurrentPosition());
        Robot.getInstance().getTelemetry().addData("Encoder_ClimbR", Robot.getInstance().getRobotMap().rightClamFoot.getCurrentPosition());

        Robot.getInstance().getTelemetry().addData("CurrentAngle", Robot.getInstance().getRobotMap().imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
        Robot.getInstance().getTelemetry().addData("HookPosition", Robot.getInstance().getRobotMap().climbHook.getPosition());



        Robot.getInstance().getTelemetry().update();
    }
}
