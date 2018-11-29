package org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths;

import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveByGyro;
import org.firstinspires.ftc.teamcode.Commands.DriveBase.TurnByGyro;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.DropMarker;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
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
        //drive to the gold cube in Left Position
        commandManager.AddCommand(new DriveByGyro(1, DriveBase.DRIVE_SPEED, 14.5));
        commandManager.AddCommand(new TurnByGyro(2, DriveBase.TURN_SPEED, 45));
        commandManager.AddCommand(new DriveByGyro(3, DriveBase.DRIVE_SPEED, 34.5));

        //drive to depot
        commandManager.AddCommand(new TurnByGyro(4, DriveBase.TURN_SPEED, -45));
        commandManager.AddCommand(new DriveByGyro(5, DriveBase.DRIVE_SPEED, 39));
        commandManager.AddCommand(new TurnByGyro(6, DriveBase.TURN_SPEED, -115));

        //drop off the marker
        commandManager.AddCommand(new DropMarker(7));

        //drive to the crater from depot
        commandManager.AddCommand(new DriveByGyro(8,DriveBase.DRIVE_SPEED, 16.25));
        commandManager.AddCommand(new TurnByGyro(9,DriveBase.TURN_SPEED,-120));
        commandManager.AddCommand(new DriveByGyro(10,DriveBase.DRIVE_SPEED, 16.25));
        commandManager.AddCommand(new TurnByGyro(11,DriveBase.TURN_SPEED,-125));
        commandManager.AddCommand(new DriveByGyro(12,DriveBase.DRIVE_SPEED, 16.25));
        commandManager.AddCommand(new TurnByGyro(13,DriveBase.TURN_SPEED,-135));
        commandManager.AddCommand(new DriveByGyro(14,DriveBase.DRIVE_SPEED, 20));


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
