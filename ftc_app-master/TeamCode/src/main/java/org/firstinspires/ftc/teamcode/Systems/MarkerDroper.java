package org.firstinspires.ftc.teamcode.Systems;

import org.firstinspires.ftc.teamcode.Robot;

public class MarkerDroper {
    public static void Flip(){
        Robot.getInstance().getRobotMap().markerDroper.setPosition(0.6);
    }

    public static void UnFlip(){
        Robot.getInstance().getRobotMap().markerDroper.setPosition(0);
    }
}
