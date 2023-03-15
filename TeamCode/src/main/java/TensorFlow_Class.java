import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class TensorFlow_Class {

    public TensorFlow_Class () {
    }

    HardwareMap hwMap = null;

    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";

    public static final String TFOD_MODEL_FILE  = "model.tflite";


    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
//              "Side 1",
//              "Side 2",
//              "Side 3"
    };

    private static final String VUFORIA_KEY =
            "AcJhST3/////AAABmQsmq4P3zkFnqg3cvXigJxQ+6i3wG1BTzbSeuNQ9SxblohOEY0Drp4ToQsJp0LjXYH6KO6QnPczhEy42M4YH+u4eqemPUWwSKQ4MWCPIkkLMjM3DGDYm5J79F6WtkPjJPfP1vtSliP7H/gkCbUAu6fubhjHB+47fqkoQV/+XD5Z/h4OypR8sWi6Qzs6ZXedASeiSy72ajoMVKwt3LO4VWalhDSx0Q0BwGu0OOsQqXkNct5pzhQwiKQDjRBZBL92I3U3apXuvtlCwIJ9Bw7DLX66rqDFdC8yI0731qRiDsZ+cNSRzqh5Yj1hojeh07XXEi92vIZ3OenuPSH/RGJEQL+P9XEp+szT0xk1StdeBdlWD";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public String Label = "" , Size = "" , Top = "" , Bottom = "" , Left = "" , Right = "";


    public void TF_read(){

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.0, 16.0/9.0);
        }

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                Size = String.valueOf(updatedRecognitions.size());

                // Note: "Image number" refers to the randomized image orientation/number
                for (Recognition recognition : updatedRecognitions) {

                    Top = String.valueOf(recognition.getTop());
                    Bottom = String.valueOf(recognition.getBottom());
                    Left = String.valueOf(recognition.getLeft());
                    Right = String.valueOf(recognition.getRight());
                    Label = recognition.getLabel();

                }
            }
        }

    }





    public void initVuforia(HardwareMap ahwMap) {

        hwMap = ahwMap;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hwMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }


    public void initTfod(HardwareMap ahwMap) {

        hwMap = ahwMap;

        int tfodMonitorViewId = hwMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hwMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = false;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);


        //tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }



}
