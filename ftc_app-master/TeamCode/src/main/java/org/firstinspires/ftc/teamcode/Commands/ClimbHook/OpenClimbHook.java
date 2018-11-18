package org.firstinspires.ftc.teamcode.Commands.ClimbHook;

import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class OpenClimbHook implements iCommand {

    private int runSequence;

    private int timer = 500;

    public OpenClimbHook(int _runSequence){
        runSequence = _runSequence;
    }

    public void Init(){
        ClimbHook.OpenHook();
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
