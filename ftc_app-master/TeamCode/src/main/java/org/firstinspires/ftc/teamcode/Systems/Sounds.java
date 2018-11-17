package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.ftccommon.SoundPlayer;
import org.firstinspires.ftc.teamcode.Robot;

public class Sounds {

    public static void PlayAutobotsRollOut(){

        // Determine Resource IDs for sounds built into the RC application.
        int AutobotsRollOutID = Robot.getInstance().getRobotMap().getHardwareMap().appContext.getResources().getIdentifier("autobots_roll_out", "raw", Robot.getInstance().getRobotMap().getHardwareMap().appContext.getPackageName());
        //preload sound if it was successfully preloaded then play the sound
        if (SoundPlayer.getInstance().preload(Robot.getInstance().getRobotMap().getHardwareMap().appContext, AutobotsRollOutID)){
            SoundPlayer.getInstance().startPlaying(Robot.getInstance().getRobotMap().getHardwareMap().appContext, AutobotsRollOutID);
        }


    }

}
