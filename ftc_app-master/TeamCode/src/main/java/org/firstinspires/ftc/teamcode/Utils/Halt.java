package org.firstinspires.ftc.teamcode.Utils;

public class Halt {

    public static boolean Hold(int HoldTime){
        StopWatch timer = new StopWatch(100);

        timer.setTime(HoldTime);

        while (!timer.isExpired()){
        }

        return true;
    }
}
