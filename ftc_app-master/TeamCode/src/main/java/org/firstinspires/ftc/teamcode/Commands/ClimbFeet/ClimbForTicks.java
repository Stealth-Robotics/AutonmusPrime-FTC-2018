package org.firstinspires.ftc.teamcode.Commands.ClimbFeet;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.ClimbFeet;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class ClimbForTicks implements iCommand {

    private int timer = 5000;
    private int runSequence;

    private int targetL;
    private int targetR;

    public ClimbForTicks(int _runSequence, int _targetL, int _targetR){
        targetL = _targetL;
        targetR = _targetR;
        runSequence = _runSequence;
    }

    public ClimbForTicks(int _runSequence, int _targetL, int _targetR, int _timer){
        targetL = _targetL;
        targetR = _targetR;
        runSequence = _runSequence;
        timer = _timer;
    }

    public void Init() {
        ClimbFeet.ClimbFeetDriveTicks(targetL, targetR);
    }

    public void Run(double dt){
        timer -= dt;
    }

    private boolean isClimbFeetForTicksDone(){
        int tolerance = 40;
        int errorR = (Robot.getInstance().getRobotMap().rightClamFoot.getTargetPosition() - Robot.getInstance().getRobotMap().rightClamFoot.getCurrentPosition());
        int errorL = (Robot.getInstance().getRobotMap().leftClamFoot.getTargetPosition() - Robot.getInstance().getRobotMap().leftClamFoot.getCurrentPosition());

        return (errorL >= -tolerance && errorL <= tolerance) && (errorR >= -tolerance && errorR <= tolerance);
    }

    public void Stop(){
        //ClimbFeet.ClimbFeetDrive(0,0);
    }

    public boolean IsDone(){
        return (isClimbFeetForTicksDone() || timer <= 0);
    }

    public int GetRunSequence(){
        return runSequence;
    }
}
