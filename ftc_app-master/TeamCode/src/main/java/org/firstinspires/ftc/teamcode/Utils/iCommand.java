package org.firstinspires.ftc.teamcode.Utils;

public interface iCommand {

    int GetRunSequence();

    void Init();

    void Run(double dt);

    void Stop();

    boolean IsDone();

}
