package org.firstinspires.ftc.teamcode.Systems;

import org.firstinspires.ftc.teamcode.Robot;

public class ClimbHook {
    //region Climb Hook
    public static void OpenHook(){
        Robot.getInstance().getRobotMap().climbHook.setPosition(0);
    }

    public static void CloseHook(){
        Robot.getInstance().getRobotMap().climbHook.setPosition(0.6);
    }
    //endregion
}
