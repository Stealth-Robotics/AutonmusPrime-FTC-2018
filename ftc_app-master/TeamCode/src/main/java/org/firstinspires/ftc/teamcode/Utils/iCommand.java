package org.firstinspires.ftc.teamcode.Utils;

import org.firstinspires.ftc.teamcode.RobotCommands;

public interface iCommand {
    int runSequence = -1;
    boolean isDone = false;

    void Init();

    void Run(double dt, RobotCommands robotCommands);

    void Stop();

    boolean IsDone();

}
