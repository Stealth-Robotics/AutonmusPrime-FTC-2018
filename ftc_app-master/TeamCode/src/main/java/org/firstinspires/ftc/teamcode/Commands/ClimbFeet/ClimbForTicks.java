package org.firstinspires.ftc.teamcode.Commands.ClimbFeet;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.ClimbFeet;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class ClimbForTicks implements iCommand {

    private boolean isDone = false;

    private int timer = 5000;
    private int runSequence;

    private int targetL;
    private int targetR;

    public ClimbForTicks(int _runSequence, int _targetL, int _targetR){
        targetL = _targetL;
        targetR = _targetR;
        runSequence = _runSequence;
    }

    public void Init(){
        Robot.getInstance().getRobotMap().ResetClimbEncoders();
        Robot.getInstance().getRobotMap().SetClimbModeEncoders();

        Robot.getInstance().getRobotMap().rightClamFoot.setTargetPosition(-targetL);
        Robot.getInstance().getRobotMap().rightClamFoot.setPower(0.8);

        Robot.getInstance().getRobotMap().leftClamFoot.setTargetPosition(-targetR);
        Robot.getInstance().getRobotMap().leftClamFoot.setPower(0.8);
    }

    public void Run(double dt){
        timer -= dt;

        if(isClimbFeetForTicksDone() || timer <= 0){
            isDone = true;
        }
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
        return isDone;
    }

    public int GetRunSequence(){
        return runSequence;
    }
}
