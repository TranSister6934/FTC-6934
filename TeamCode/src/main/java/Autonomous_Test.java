import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Autonomous_Test", group = "Test")

public class Autonomous_Test extends LinearOpMode {

    Hardwaremap hm = new Hardwaremap (this);

    @Override
    public void runOpMode() throws InterruptedException {

        hm.Init_robot(hardwareMap);

        waitForStart();

        hm.encoderDrive(hm.DRIVE_SPEED, -96, 96, 96,  -96, 100.0);

        telemetry.addData( "Front Left", hm.FrontLeft.getCurrentPosition());
        telemetry.addData( "Front Right", hm.FrontRight.getCurrentPosition());
        telemetry.addData( "Back Left", hm.BackLeft.getCurrentPosition());
        telemetry.addData( "Back Right", hm.BackRight.getCurrentPosition());
        telemetry.update();


        sleep(1000000);

    }
}
