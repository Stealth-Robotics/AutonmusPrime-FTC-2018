package org.firstinspires.ftc.teamcode.Commands.MarkerDroper;

import org.firstinspires.ftc.teamcode.Systems.MarkerDroper;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class FlipMarkerDroper implements iCommand {

    private int runSequence;

    private int timer = 250;

    public FlipMarkerDroper(int _runSequence){
        runSequence = _runSequence;
    }

    public void Init(){
        MarkerDroper.Flip();
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
