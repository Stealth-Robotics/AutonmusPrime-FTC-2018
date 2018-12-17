package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Systems.ClimbFeet;
import org.firstinspires.ftc.teamcode.Systems.ClimbHook;
import org.firstinspires.ftc.teamcode.Systems.MarkerDroper;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

public class Robot {

    public Robot() {
        robotMap = null;
        telemetry = null;
        vuforia = null;
    }

    private static Robot robotInstance;

    public static Robot getInstance(){
        if(robotInstance == null){
            robotInstance = new Robot();
        }

        return robotInstance;
    }

    //region robotMap
    private org.firstinspires.ftc.teamcode.RobotMap robotMap;

    public org.firstinspires.ftc.teamcode.RobotMap getRobotMap() {
        return robotMap;
    }

    public void setRobotMap(org.firstinspires.ftc.teamcode.RobotMap _robotMap) {
        robotMap = _robotMap;

        ClimbHook.CloseHook();
        MarkerDroper.UnFlip();

        ClimbFeet.ResetEncoders();
        Robot.getInstance().getRobotMap().ResetDriveEncoders();
    }
    //endregion robotMap

    //region telemetry
    private Telemetry telemetry;

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
    //endregion telemetry

    //region mineralPosition
    private MineralPosition mineralPosition;

    public MineralPosition getMineralPosition() {
        return mineralPosition;
    }

    public void setMineralPosition(MineralPosition _mineralPosition) {
        mineralPosition = _mineralPosition;
    }
    //endregion mineralPosition

    //region autoPosition
    private AutoPosition autoPosition;

    public AutoPosition getAutoPosition() {
        return autoPosition;
    }

    public void setAutoPosition(AutoPosition autoPosition) {
        this.autoPosition = autoPosition;
    }
    //endregion autoPosition

    //region vuforia
    /**
     * vuforia is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    private static final String VUFORIA_KEY = "AXwhG3X/////AAAAGeyrECmhIEnMtx00hFsdD204jVm8PsOfnpnVu7sU9FQnzbhyt14ohqXYBSOB2xsEM11XgvKUZE5HtTvnoQ7JNknNvg9GtTt9P+LCq/TNS3pam2n8FfmzKypVR5M2PZQ/d9MhR0AfHwJa+UE7G0b8NevUKCya1wd+qwK3k5pTEaI81q6Z4iHVl+u1O0eICRG6bj+M2q36ZKtm/CQzcnmCSQYJhIDQsZL2/78EJiWUtO/GjVrEYoNwNLxCkiln6UQEKO4zWW6TcuwRMgO9f++DI3EDQdp9ads3vxh33/I38KirR/izKL8Wf0/qrqucbxv8B8QWh1tR8/ojvNN12Vc6ib1yyAqYrjz7hJ4jqFGJ2ADY";

    public VuforiaLocalizer getVuforia() {
        return vuforia;
    }

    public void StopVuforia(){
        if(vuforia.getCamera() != null){
            vuforia.getCamera().close();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    public boolean initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.

        if (vuforia.getCamera() == null){
            return false;
        }

        return true;
    }
    //endregion vuforia
}
