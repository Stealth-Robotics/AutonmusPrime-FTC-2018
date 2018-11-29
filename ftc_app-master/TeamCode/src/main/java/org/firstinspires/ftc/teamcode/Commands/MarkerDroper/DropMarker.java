package org.firstinspires.ftc.teamcode.Commands.MarkerDroper;

import org.firstinspires.ftc.teamcode.Commands.Hold;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class DropMarker implements iCommand {
    private int runSequence;

    private CommandManager commandManager;

    public DropMarker(int _runSequence){
        runSequence = _runSequence;
        commandManager = new CommandManager();
    }

    public void Init() {
        //drop the marker
        commandManager.AddCommand(new FlipMarkerDroper(1));
        commandManager.AddCommand(new Hold(2, 250));
        commandManager.AddCommand(new UnFlipMarkerDroper(3));

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
