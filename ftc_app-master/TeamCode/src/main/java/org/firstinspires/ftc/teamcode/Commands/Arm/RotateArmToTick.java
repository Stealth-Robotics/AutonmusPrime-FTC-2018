package org.firstinspires.ftc.teamcode.Commands.Arm;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.Arm;
import org.firstinspires.ftc.teamcode.Systems.ClimbFeet;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class RotateArmToTick implements iCommand {

    private int timer = 5500;
    private int runSequence;

    private int target;

    public RotateArmToTick(int _runSequence, int _target){
        target = _target;
        runSequence = _runSequence;
    }

    public RotateArmToTick(int _runSequence, int _target, int _timer){
        target = _target;
        runSequence = _runSequence;
        timer = _timer;
    }

    public void Init() {
        Arm.RotateArmToTarget(target);
    }

    public void Run(double dt){
        timer -= dt;
    }

    private boolean isArmRotationDone(){
        int tolerance = 30;
        int error = (Robot.getInstance().getRobotMap().armRotationMotor.getTargetPosition() - Robot.getInstance().getRobotMap().armRotationMotor.getCurrentPosition());

        return (error >= -tolerance && error <= tolerance);
    }

    public void Stop(){
    }

    public boolean IsDone(){
        return (isArmRotationDone() || timer <= 0);
    }

    public int GetRunSequence(){
        return runSequence;
    }
}
