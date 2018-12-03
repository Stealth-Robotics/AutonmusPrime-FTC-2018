package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Commands.ClimbFeet.ClimbForTicks;
import org.firstinspires.ftc.teamcode.Commands.ClimbHook.OpenClimbHook;
import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveByGyro;
import org.firstinspires.ftc.teamcode.Commands.DriveBase.TurnByGyro;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.DropMarker;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class ClimbDown implements iCommand {
    private int runSequence;

    private CommandManager commandManager;

    public ClimbDown(int _runSequence){
        runSequence = _runSequence;
        commandManager = new CommandManager();
    }

    public void Init() {
        commandManager.AddCommand(new ClimbForTicks(1, 3450, 3450));
        commandManager.AddCommand(new OpenClimbHook(2));
        commandManager.AddCommand(new Hold(3, 500));
        commandManager.AddCommand(new ClimbForTicks(4, -500, -500));
        commandManager.AddCommand(new Hold(5, 100));

        commandManager.Start();
    }

    public void Run(double dt) {
        commandManager.Run();
    }

    public void Stop() {
        commandManager.Stop();
    }

    public boolean IsDone() {
        return (commandManager.isFinished());
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
