package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.AutonomousPrimeRobotCommands;
import org.firstinspires.ftc.teamcode.Utils.Halt;

public class DropHang {

    public static void Run(AutonomousPrimeRobotCommands robotCommands){
        robotCommands.CloseHook();

        Halt.Hold(50);

        robotCommands.ClamFeetForTicks(4000,4000);

        //Halt.Hold(3500);

        robotCommands.OpenHook();

        Halt.Hold(1000);

        robotCommands.ClamFeetForTicks(-4100, -4100);

        Halt.Hold(50);
    }
}
