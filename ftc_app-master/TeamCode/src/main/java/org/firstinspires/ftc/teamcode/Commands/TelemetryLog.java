package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.AutonomousPrimeRobotCommands;

public class TelemetryLog {
    public static void Run(AutonomousPrimeRobotCommands robotCommands, Telemetry telemetry) {
        telemetry.addData("Encoder_FR", robotCommands.RobotMap.frontRightDrive.getCurrentPosition());
        telemetry.addData("Encoder_FL", robotCommands.RobotMap.frontLeftDrive.getCurrentPosition());

        telemetry.addData("Encoder_ClimbL", robotCommands.RobotMap.leftClamFoot.getCurrentPosition());
        telemetry.addData("Encoder_ClimbR", robotCommands.RobotMap.rightClamFoot.getCurrentPosition());

        telemetry.addData("CurrentAngle", robotCommands.RobotMap.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
        telemetry.addData("HookPosition", robotCommands.RobotMap.climbHook.getPosition());

        telemetry.update();
    }

}
