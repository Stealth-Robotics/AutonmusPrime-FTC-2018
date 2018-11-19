package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.iCommand;

import java.util.List;

public class MineralPositionDetection implements iCommand {
    private boolean isDone = false;

    private int timer = 5000;
    private boolean useTimeout = true;
    private int runSequence;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";


    /**
     * tfod is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public MineralPositionDetection(int _runSequence, boolean _useTimeout) {
        runSequence = _runSequence;
        useTimeout = _useTimeout;
    }

    public void Init() {
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            Robot.getInstance().getTelemetry().addData("Sorry!", "This device is not compatible with TFOD");
        }

        // Activate Tensor Flow Object Detection.
        tfod.activate();
    }

    public void Run(double dt) {
        if (tfod == null) {
            isDone = true;
            return;
        }

        timer -= dt;

        MineralPosition position = GetMaterialPos();

        if(position != null || (timer <= 0 && useTimeout)) {
            Robot.getInstance().setMineralPosition(position);
            isDone = true;
        }
    }

    public void Stop() {
        // Shutdown Tensor Flow Object Detection.
        tfod.shutdown();
    }

    public boolean IsDone() {
        return isDone;
    }

    public int GetRunSequence() {
        return runSequence;
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        //get camera viewId to display stuffs
        int tfodMonitorViewId = Robot.getInstance().getRobotMap().getHardwareMap().appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", Robot.getInstance().getRobotMap().getHardwareMap().appContext.getPackageName());

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        //increase the minimum confidence for objects from the 0.4 default
        tfodParameters.minimumConfidence = 0.3f;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, Robot.getInstance().getVuforia());
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private MineralPosition GetMaterialPos(){
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {

                Robot.getInstance().getTelemetry().addData("# Object Detected", updatedRecognitions.size());

                if (updatedRecognitions.size() == 3) {

                    //init vars to -1
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;

                    //foreach object recognised set its x (getLeft) to the correct value
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }

                    //based on where the gold is relative to silver minerals return the correct value
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            Robot.getInstance().getTelemetry().addData("Gold Mineral Position", "Left");
                            return MineralPosition.Left;
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            Robot.getInstance().getTelemetry().addData("Gold Mineral Position", "Right");
                            return MineralPosition.Right;
                        } else {
                            Robot.getInstance().getTelemetry().addData("Gold Mineral Position", "Center");
                            return MineralPosition.Center;
                        }
                    }

                    Robot.getInstance().getTelemetry().update();
                }
            }
        }
        return null;
    }

}
