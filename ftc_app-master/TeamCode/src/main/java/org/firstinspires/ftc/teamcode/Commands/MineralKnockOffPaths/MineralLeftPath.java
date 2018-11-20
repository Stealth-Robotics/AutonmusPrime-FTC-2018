package org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths;

import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveForTicks;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class MineralLeftPath implements iCommand {
    private int runSequence;

    private CommandManager commandManager;

    public MineralLeftPath(int _runSequence){
        runSequence = _runSequence;
        commandManager = new CommandManager();
    }

    public void Init() {
        commandManager.AddCommand(new DriveForTicks(1, 650, 650));
        commandManager.AddCommand(new DriveForTicks(2, -445, 445));
        commandManager.AddCommand(new DriveForTicks(3, 2200, 2200));
        commandManager.AddCommand(new DriveForTicks(4, 1500, -1500));
        commandManager.AddCommand(new DriveForTicks(5, 2200, 2200));

        commandManager.Start();
    }

    public void Run(double dt) {
        commandManager.Run();
    }

    public void Stop() {
        commandManager.Stop();
    }

    public boolean IsDone() {
        return (commandManager.isFinished() || (Robot.getInstance().getMineralPosition() != MineralPosition.Left));
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
