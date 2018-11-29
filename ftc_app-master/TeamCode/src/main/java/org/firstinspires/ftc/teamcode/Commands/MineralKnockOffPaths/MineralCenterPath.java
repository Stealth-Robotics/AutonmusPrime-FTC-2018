package org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths;

import org.firstinspires.ftc.teamcode.Commands.DriveBase.DriveByGyro;
import org.firstinspires.ftc.teamcode.Commands.DriveBase.TurnByGyro;
import org.firstinspires.ftc.teamcode.Commands.MarkerDroper.DropMarker;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.CommandManager;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

public class MineralCenterPath implements iCommand {
    private int runSequence;

    private CommandManager commandManager;

    public MineralCenterPath(int _runSequence){
        runSequence = _runSequence;
        commandManager = new CommandManager();
    }

    public void Init() {
        //drive to the gold cube in Center Position
        commandManager.AddCommand(new DriveByGyro(1, DriveBase.DRIVE_SPEED, 36));
        commandManager.AddCommand(new TurnByGyro(2, DriveBase.TURN_SPEED, -45));
        commandManager.AddCommand(new TurnByGyro(2, DriveBase.TURN_SPEED, 45));

        //drive to depot and turn
        commandManager.AddCommand(new TurnByGyro(4, DriveBase.TURN_SPEED, 0));
        commandManager.AddCommand(new DriveByGyro(5, DriveBase.DRIVE_SPEED, 20));
        commandManager.AddCommand(new TurnByGyro(6, DriveBase.TURN_SPEED, -120));

        //drop off the marker
        commandManager.AddCommand(new DropMarker(7));

        //drive to the crater from depot
        commandManager.AddCommand(new DriveByGyro(8,DriveBase.DRIVE_SPEED, 16.25));
        commandManager.AddCommand(new TurnByGyro(9,DriveBase.TURN_SPEED,-135));
        commandManager.AddCommand(new DriveByGyro(10,DriveBase.DRIVE_SPEED, 16.25));
        commandManager.AddCommand(new TurnByGyro(11,DriveBase.TURN_SPEED,-135));
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
        return (commandManager.isFinished() || Robot.getInstance().getMineralPosition() != MineralPosition.Center);
    }

    public int GetRunSequence() {
        return runSequence;
    }
}
