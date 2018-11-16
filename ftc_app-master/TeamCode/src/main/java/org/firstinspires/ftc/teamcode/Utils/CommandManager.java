package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotCommands;

import java.util.List;

public class CommandManager {
    private int CurrentRunId = 0;
    private List<iCommand> Commands;
    private List<iCommand> ActiveCommands;

    private RobotCommands robotCommands;
    private ElapsedTime elapsedTime;

    public CommandManager(RobotCommands _robotCommands){
        robotCommands = _robotCommands;
        elapsedTime = new ElapsedTime();
    }

    public void Run() {
        if(ActiveCommands.size() != 0){
            for (int i = 0; i < ActiveCommands.size(); i++) {
                iCommand command = ActiveCommands.get(i);
                command.Run(elapsedTime.milliseconds(), robotCommands);

                if(command.IsDone()) {
                    command.Stop();
                    ActiveCommands.remove(ActiveCommands.get(i));
                }
            }
        } else {
            CurrentRunId++;
            for (int i = 0; i < Commands.size(); i++){
                iCommand command = Commands.get(i);
                if (command.runSequence == CurrentRunId){
                    ActiveCommands.add(command);
                }
            }
        }

        elapsedTime.reset();
    }

    public void AddCommand(iCommand command) {
        command.Init();
        Commands.add(command);
    }


}
