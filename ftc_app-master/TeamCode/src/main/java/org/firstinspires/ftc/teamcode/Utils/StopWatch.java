package org.firstinspires.ftc.teamcode.Utils;

public class StopWatch {

    long mLastTime = 0;
    int mWaitTime = 0;

    public StopWatch(int waitTime) {
        mWaitTime = waitTime;
        reset();
    }

    public void setTime(int waitTime) {
        mWaitTime = waitTime;
        reset();
    }

    public boolean isExpired() {
        if ((System.currentTimeMillis() - mLastTime) > mWaitTime) {
            return true;
        }
        return false;
    }

    public void reset() {
        mLastTime = System.currentTimeMillis();
    }
}