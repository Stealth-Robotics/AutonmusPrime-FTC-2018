package org.firstinspires.ftc.teamcode.Commands.ClimbHook;

import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class CloseClimbHook implements iCommand {

    private boolean isDone = false;

    private int runSequence;

    public CloseClimbHook(int _runSequence){
        runSequence = _runSequence;
    }

    public void Init(){
        ClimbHook.CloseHook();
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
