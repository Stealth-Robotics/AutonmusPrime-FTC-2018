package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private int CurrentRunId = 0;
    private int MaxRunId = 0;

    private boolean isFinished = false;

    private List<iCommand> Commands;
    private List<iCommand> ActiveCommands;

    private List<iConstantCommand> ConstantCommands;

    private ElapsedTime elapsedTime;

    public CommandManager(){
        elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        Commands = new ArrayList<>();
        ActiveCommands = new ArrayList<>();
        ConstantCommands = new ArrayList<>();
    }

    public void Start() {
        elapsedTime.reset();
    }

    public void Run() {
        //Robot.getInstance().getTelemetry().addData("Active Command", CurrentRunId);

        //run constant commands
        for (int i = 0; i < ConstantCommands.size(); i++){
            ConstantCommands.get(i).Run(elapsedTime.milliseconds());
        }

        //run/init linear commands
        if(!ActiveCommands.isEmpty()){

            List<iCommand> CommandsToRemove = new ArrayList<>();
            //check for commands that are done, tell them to stop and add them to Commands to remove list
            for (iCommand command : ActiveCommands) {
                if (command.IsDone()) {
                    command.Stop();
                    //ActiveCommands.remove(command);
                    CommandsToRemove.add(command);
                }
            }
            //remove all the commands that are now done as they are in commands to remove
            ActiveCommands.removeAll(CommandsToRemove);

            //run commands that are still active
            for (iCommand command : ActiveCommands) {
                command.Run(elapsedTime.milliseconds());
            }
        } else if(CurrentRunId + 1 <= MaxRunId){
            CurrentRunId++;
            //loop through all the commands and if the command run id matches current run id then init it and add it to active commands
            for (iCommand command : Commands){
                if (command.GetRunSequence() == CurrentRunId){
                    command.Init();
                    ActiveCommands.add(command);
                }
            }
        } else {
            //there are no more commands to run so we are done here!
            isFinished = true;
        }

        elapsedTime.reset();
    }

    public void AddCommand(iCommand command) {
        if(command.GetRunSequence() > MaxRunId){
            MaxRunId = command.GetRunSequence();
        }
        Commands.add(command);
    }

    public void AddConstantCommand(iConstantCommand constantCommand){
        ConstantCommands.add(constantCommand);
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void Stop(){
        //stop all of the active commands
        for (iCommand command : ActiveCommands) {
            command.Stop();
        }
    }
}
