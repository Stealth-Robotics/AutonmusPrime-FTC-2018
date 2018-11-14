package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

import java.util.List;

public class MineralPositionDetection {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AXwhG3X/////AAAAGeyrECmhIEnMtx00hFsdD204jVm8PsOfnpnVu7sU9FQnzbhyt14ohqXYBSOB2xsEM11XgvKUZE5HtTvnoQ7JNknNvg9GtTt9P+LCq/TNS3pam2n8FfmzKypVR5M2PZQ/d9MhR0AfHwJa+UE7G0b8NevUKCya1wd+qwK3k5pTEaI81q6Z4iHVl+u1O0eICRG6bj+M2q36ZKtm/CQzcnmCSQYJhIDQsZL2/78EJiWUtO/GjVrEYoNwNLxCkiln6UQEKO4zWW6TcuwRMgO9f++DI3EDQdp9ads3vxh33/I38KirR/izKL8Wf0/qrqucbxv8B8QWh1tR8/ojvNN12Vc6ib1yyAqYrjz7hJ4jqFGJ2ADY";

    /**
     * vuforia is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * tfod is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    public MineralPositionDetection(HardwareMap hw, Telemetry tel) {
        hardwareMap = hw;
        telemetry = tel;

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        //get camera viewId to display stuffs
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        //increase the minimum confidence for objects from the 0.4 default
        tfodParameters.minimumConfidence = 0.65f;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private MineralPosition GetMaterialPos(){
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {

                telemetry.addData("# Object Detected", updatedRecognitions.size());

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
                            telemetry.addData("Gold Mineral Position", "Left");
                            return MineralPosition.Left;
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            return MineralPosition.Right;
                        } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                            return MineralPosition.Center;
                        }
                    }

                    telemetry.update();
                }
            }
        }
        return null;
    }

    public MineralPosition Run(){

        if (tfod == null) {
            return null;
        }

        MineralPosition position = null;

        // Activate Tensor Flow Object Detection.
        tfod.activate();

        while (position == null){
            position = GetMaterialPos();
        }

        // Shutdown Tensor Flow Object Detection.
        tfod.shutdown();

        return position;
    }
}
