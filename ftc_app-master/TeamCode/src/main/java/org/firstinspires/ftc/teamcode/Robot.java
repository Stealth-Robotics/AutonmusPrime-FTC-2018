package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Systems.DriveBase;
import org.firstinspires.ftc.teamcode.Utils.AutoPosition;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;
import org.firstinspires.ftc.teamcode.Utils.StopWatch;

public class Robot {

    public Robot() {
        robotMap = null;
        telemetry = null;

        initVuforia();
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

    public void setMineralPosition(MineralPosition mineralPosition) {
        this.mineralPosition = mineralPosition;
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
    //endregion vuforia
}
