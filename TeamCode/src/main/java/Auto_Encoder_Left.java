import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Auto_Encoder_Left", group = "Tests")

public class Auto_Encoder_Left extends LinearOpMode {

    Hardwaremap hm = new Hardwaremap(this);
    Vuforia_Class V = new Vuforia_Class();
    private String auto_mode = "";

    @Override
    public void runOpMode() throws InterruptedException {

        V.Init_Vuforia(hardwareMap);
        hm.Init_robot(hardwareMap);


        while (opModeInInit()) {

            V.Track_Target();
            telemetry.addLine("Go, Go, GOOOO!");
            telemetry.addLine("Target Name : " + V.target_name);
            telemetry.update();

        }

        if ( V.target_name == "Sheep"){
            auto_mode = "drive_1";
        }

        if (V.target_name == "Hearts"){
            auto_mode = "drive_2";
        }

        if (V.target_name == "Stars"){
            auto_mode = "drive_3";
        }

        if (V.target_name == "Null"){
            auto_mode = "drive_alt";
        }

        waitForStart();

        //Close claw to grab cone
        hm.claw.setPosition(1);
        hm.liftArm(0.3, 500, 3.0);


        // Drive fwd 1 ft
        hm.encoderDrive(hm.DRIVE_SPEED, 9, 9, 9, 9, 5.0);

        // Turn to read cone
        hm.encoderDrive(hm.DRIVE_SPEED, 2.5, -2.5, 2.5, -2.5, 2.0);



            resetRuntime();

            while (opModeIsActive() && getRuntime() < 3.0) {

                V.Track_Target();
                telemetry.addLine("Double checking Vuforia");
                telemetry.addLine("Target Name : " + V.target_name);
                telemetry.update();

            }

            if ( V.target_name == "Sheep"){
                auto_mode = "drive_1";
            }

            if (V.target_name == "Hearts"){
                auto_mode = "drive_2";
            }

            if (V.target_name == "Stars"){
                auto_mode = "drive_3";
            }

            if (V.target_name == "Null"){
                auto_mode = "drive_alt";
            }

            telemetry.addData(" target " , V.target_name);
            telemetry.update();

//            sleep(5000);

        //Turn back
        hm.encoderDrive(hm.DRIVE_SPEED, -2.5, 2.5, -2.5, 2.5, 2.0);

        //Drive fwd Nat 2
        hm.encoderDrive(hm.DRIVE_SPEED, 17, 17, 17, 17, 5.0);

        //Strafe to tall pole
        hm.encoderDrive(hm.DRIVE_SPEED, 33, -33, -33, 33, 5.0);

        //Lift arm up
        hm.liftArm(0.3, 4250, 7.0);

        //Drive up to pole
        hm.encoderDrive(hm.DRIVE_SPEED, 2, 2, 2, 2, 4.0);

        //Lower arm
        hm.liftArm(0.3, 4000, 3.0);

        //Drop cone
        hm.claw.setPosition(0.0);

        //Bring arm up
        hm.liftArm(0.3,4250, 3.0);

        //Back up from pole
        hm.encoderDrive(hm.DRIVE_SPEED, -3, -3, -3, -3, 2.0);

        //Lower arm
        hm.liftArm(0.3, 0, 7.0);



        switch (auto_mode) {

            case "drive_1" :

                hm.encoderDrive(hm.DRIVE_SPEED, -38, 38, 38, -38, 5.0);
                hm.encoderDrive(hm.DRIVE_SPEED, -28, 28, 28, -28, 5.0);
                hm.encoderDrive(hm.DRIVE_SPEED, 10, 10, 10, 10, 5.0);


                break; //break for auto mode

            case "drive_2" :

                hm.encoderDrive(hm.DRIVE_SPEED, -35, 35, 35, -35, 5.0);
                hm.encoderDrive(hm.DRIVE_SPEED, 4, 4, 4, 4, 5.0);

                break; //break for auto mode

            case "drive_3" :

                hm.encoderDrive(hm.DRIVE_SPEED, -12, 12, 12, -12, 5.0);
                hm.encoderDrive(hm.DRIVE_SPEED, 4, 4, 4, 4, 5.0);

                break; //break for auto mode

            case "drive_alt" :

                hm.encoderDrive(hm.DRIVE_SPEED, -9, 9, 9, -9, 5.0);
                hm.encoderDrive(hm.DRIVE_SPEED, -25, -25, -25, -25, 4.0);

                break; //break for auto mode
        }

        hm.encoderDrive( 0.0, 0, 0, 0, 0, 0.0);

        sleep(1000);

    }
}
