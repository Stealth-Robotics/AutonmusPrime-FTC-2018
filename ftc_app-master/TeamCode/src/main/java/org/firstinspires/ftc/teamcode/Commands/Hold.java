package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class Hold implements iCommand {

    private boolean isDone = false;

    private int runSequence;
    private int timer;

    public Hold(int _runSequence, int _time){
        runSequence = _runSequence;
        timer = _time;
    }

    public void Init(){
    }

    public void Run(double dt){
        timer -= dt;

        if(timer <= 0){
            isDone = true;
        }
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
