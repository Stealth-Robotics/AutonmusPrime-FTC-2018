package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class Hold implements iCommand {

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
    }

    public void Stop(){
    }

    public boolean IsDone(){
        return timer <= 0;
    }

    public int GetRunSequence(){
        return runSequence;
    }
}
