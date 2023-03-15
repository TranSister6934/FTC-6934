import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

public class Vuforia_Class {

    public Vuforia_Class () {}

    //variables
    private VuforiaLocalizer vuforia    = null;
    private VuforiaTrackables targets   = null;
    List<VuforiaTrackable> allTrackables;
    public String target_name;
    private WebcamName webcamName       = null;


    //Vuforia Set Up
    public void Init_Vuforia(HardwareMap hwmp) {

        webcamName = hwmp.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hwmp.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwmp.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcJhST3/////AAABmQsmq4P3zkFnqg3cvXigJxQ+6i3wG1BTzbSeuNQ9SxblohOEY0Drp4ToQsJp0LjXYH6KO6QnPczhEy42M4YH+u4eqemPUWwSKQ4MWCPIkkLMjM3DGDYm5J79F6WtkPjJPfP1vtSliP7H/gkCbUAu6fubhjHB+47fqkoQV/+XD5Z/h4OypR8sWi6Qzs6ZXedASeiSy72ajoMVKwt3LO4VWalhDSx0Q0BwGu0OOsQqXkNct5pzhQwiKQDjRBZBL92I3U3apXuvtlCwIJ9Bw7DLX66rqDFdC8yI0731qRiDsZ+cNSRzqh5Yj1hojeh07XXEi92vIZ3OenuPSH/RGJEQL+P9XEp+szT0xk1StdeBdlWD";
        parameters.useExtendedTracking = false;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        targets = vuforia.loadTrackablesFromAsset("powerplay6934");
        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targets);

        identifyTarget(0, "Hearts");
        identifyTarget(1, "Stars");
        identifyTarget(2, "Sheep");

        targets.activate();

    }

    public  void Track_Target() {

        target_name = "Null";
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                target_name = trackable.getName();
            }
        }

    }


    void    identifyTarget(int targetIndex, String targetName) {
        VuforiaTrackable aTarget = targets.get(targetIndex);
        aTarget.setName(targetName);
    }


}