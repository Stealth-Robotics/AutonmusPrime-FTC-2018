package org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths;

import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveForTicks;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class MineralRightPath implements iCommand {
    private int runSequence;

    private CommandManager commandManager;

    public MineralRightPath(int _runSequence){
        runSequence = _runSequence;
        commandManager = new CommandManager();
    }

    public void Init() {
        commandManager.AddCommand(new DriveForTicks(1, 650, 650));
        commandManager.AddCommand(new DriveForTicks(2, 370, -370));
        commandManager.AddCommand(new DriveForTicks(3, 2300, 2300));
        commandManager.AddCommand(new DriveForTicks(4, -900, 900));
        commandManager.AddCommand(new DriveForTicks(5, 2400, 2400));
        commandManager.AddCommand(new DriveForTicks(6, -850, 850));

        commandManager.Start();
    }

    public void Run(double dt) {
        commandManager.Run();
    }

    public void Stop() {
        commandManager.Stop();
    }

    public boolean IsDone() {
        return (commandManager.isFinished() || Robot.getInstance().getMineralPosition() != MineralPosition.Right);
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
