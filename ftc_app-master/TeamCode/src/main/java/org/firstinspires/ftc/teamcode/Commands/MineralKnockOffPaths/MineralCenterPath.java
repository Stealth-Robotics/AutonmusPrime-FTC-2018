package org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths;

import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveForTicks;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class MineralCenterPath implements iCommand {
    private int runSequence;

    private CommandManager CommandManager;

    public MineralCenterPath(int _runSequence){
        runSequence = _runSequence;
        CommandManager = new CommandManager();
    }

    public void Init() {
        CommandManager.AddCommand(new DriveForTicks(1, 1000, 1000));

        CommandManager.Start();
    }

    public void Run(double dt) {
        CommandManager.Run();
    }

    public void Stop() {
        CommandManager.Stop();
    }

    public boolean IsDone() {
        return (CommandManager.isFinished() || Robot.getInstance().getMineralPosition() != MineralPosition.Center);
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
