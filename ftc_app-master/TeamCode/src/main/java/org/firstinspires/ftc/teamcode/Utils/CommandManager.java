package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

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
        elapsedTime = new ElapsedTime();
        Commands = new ArrayList<>();
        ActiveCommands = new ArrayList<>();
        ConstantCommands = new ArrayList<>();
    }

    public void Run() {
        Robot.getInstance().getTelemetry().addData("Active Command", CurrentRunId);
        //run linear commands
        if(ActiveCommands.size() != 0){
            for (int i = 0; i < ActiveCommands.size(); i++) {
                iCommand command = ActiveCommands.get(i);

                if(command.IsDone()) {
                    command.Stop();
                    ActiveCommands.remove(ActiveCommands.get(i));
                } else {
                    command.Run(elapsedTime.milliseconds());
                }
            }
        } else if(CurrentRunId + 1 <= MaxRunId){
            CurrentRunId++;
            for (int i = 0; i < Commands.size(); i++){
                iCommand command = Commands.get(i);
                if (command.GetRunSequence() == CurrentRunId){
                    command.Init();
                    ActiveCommands.add(command);
                }
            }
        } else {
            isFinished = true;
        }

        //run constant commands
        for (int i = 0; i < ConstantCommands.size(); i++){
            ConstantCommands.get(i).Run(elapsedTime.milliseconds());
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


}
