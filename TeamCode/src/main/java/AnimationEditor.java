import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.android.dx.util.ByteArray;


@TeleOp(name="Animation Editor", group="Iterative Opmode")
public class AnimationEditor extends LinearOpMode {

    byte[] a = new byte[80];
    //ByteArray[] a = new ByteArray[80];

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

            //gamepad1.fromByteArray(a);
            a = gamepad1.toByteArray();


            telemetry.addData( " idk " , a);
            telemetry.update();


        }

    }
}
