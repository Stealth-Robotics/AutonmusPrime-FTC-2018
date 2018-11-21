package org.firstinspires.ftc.teamcode.Commands.DriveBase;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class DriveForTicks implements iCommand {

    private int timer = 15000;
    private int runSequence;

    private int targetL;
    private int targetR;

    private double power = 0.5;

    public DriveForTicks(int _runSequence, int _targetL, int _targetR){
        runSequence = _runSequence;
        targetL = _targetL;
        targetR = _targetR;
    }

    public DriveForTicks(int _runSequence, double distanceL, double distanceR, double _power){
        runSequence = _runSequence;
        targetL = (int)(distanceL * DriveBase.TICKS_PER_INCH);
        targetR = (int)(distanceR * DriveBase.TICKS_PER_INCH);
        power = _power;
    }

    public DriveForTicks(int _runSequence, int _targetL, int _targetR, double _power, int _timer){
        runSequence = _runSequence;
        targetL = _targetL;
        targetR = _targetR;
        power = _power;
        timer = _timer;
    }

    public void Init() {
        Robot.getInstance().getRobotMap().ResetDriveEncoders();
        Robot.getInstance().getRobotMap().SetDriveModeEncoders();

        Robot.getInstance().getRobotMap().frontLeftDrive.setTargetPosition(targetL);
        Robot.getInstance().getRobotMap().frontLeftDrive.setPower(power);

        Robot.getInstance().getRobotMap().frontRightDrive.setTargetPosition(-targetR);
        Robot.getInstance().getRobotMap().frontRightDrive.setPower(power);
    }

    public void Run(double dt) {
        timer -= dt;
    }

    private boolean isDriveForTicksDone(){
        int tolerance = 15;
        int errorL = (Robot.getInstance().getRobotMap().frontLeftDrive.getTargetPosition() - Robot.getInstance().getRobotMap().frontLeftDrive.getCurrentPosition());
        int errorR = (Robot.getInstance().getRobotMap().frontRightDrive.getTargetPosition() - Robot.getInstance().getRobotMap().frontRightDrive.getCurrentPosition());

        return (errorL >= -tolerance && errorL <= tolerance) && (errorR >= -tolerance && errorR <= tolerance);
    }

    public void Stop() {
        DriveBase.KillDriveMotorPower();
    }

    public boolean IsDone() {
        return (isDriveForTicksDone() || timer <= 0);
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
