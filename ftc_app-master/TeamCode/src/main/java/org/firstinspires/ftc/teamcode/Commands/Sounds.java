package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Sounds {

    public static void PlayAutobotsRollOut(HardwareMap hardwareMap){

        // Determine Resource IDs for sounds built into the RC application.
        int AutobotsRollOutID = hardwareMap.appContext.getResources().getIdentifier("autobots_roll_out", "raw", hardwareMap.appContext.getPackageName());
        //preload sound if it was successfully preloaded then play the sound
        if (SoundPlayer.getInstance().preload(hardwareMap.appContext, AutobotsRollOutID)){
            SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, AutobotsRollOutID);
        }


    }

}
