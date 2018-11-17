package org.firstinspires.ftc.teamcode.Commands.ClimbHook;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class OpenClimbHook implements iCommand {

    private boolean isDone = false;

    private int runSequence;

    public OpenClimbHook(int _runSequence){
        runSequence = _runSequence;
    }

    public void Init(){
        ClimbHook.OpenHook();
    }

    public void Run(double dt){
        isDone = true;
    }

    public void Stop(){
    }

    public boolean IsDone(){
        return isDone;
    }

    public int GetRunSequence(){
        return runSequence;
    }
}
