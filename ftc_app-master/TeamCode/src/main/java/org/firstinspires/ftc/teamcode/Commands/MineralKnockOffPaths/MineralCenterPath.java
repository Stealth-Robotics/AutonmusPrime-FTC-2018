package org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths;

import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveForTicks;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class MineralCenterPath implements iCommand {
    private boolean isDone = false;

    private int runSequence;

    private CommandManager commandManager;

    public MineralCenterPath(int _runSequence){
        runSequence = _runSequence;
        commandManager = new CommandManager();
    }

    public void Init() {
        //TODO: Check this logic when i am not super tired! \/
        if(Robot.getInstance().getMineralPosition() != MineralPosition.Center || Robot.getInstance().getMineralPosition() != MineralPosition.Unidentified){
            isDone = true;
            return;
        }

        commandManager.AddCommand(new DriveForTicks(1, 1000, 1000));
    }

    public void Run(double dt) {
        commandManager.Run();

        if(commandManager.isFinished()){
            isDone = true;
        }

    }

    public void Stop() {
    }

    public boolean IsDone() {
        return isDone;
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
