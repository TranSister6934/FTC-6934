import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOp Auto", group="Iterative Opmode")

public class TeleOp_auto extends OpMode {

    Hardwaremap hm = new Hardwaremap( );
    int EV = 0;

    @Override
    public void init() {

        hm.Init_robot(hardwareMap);

        hm.Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hm.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hm.Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hm.claw.setPosition(0);

    }

    public void init_loop() {

    }
    public void start() {

    }



    @Override
    public void loop() {

        if (gamepad2.dpad_down) {

            if (hm.Arm.getCurrentPosition() > -4360) {

                hm.Arm.setPower(0.5);
                EV = hm.Arm.getCurrentPosition();
                hm.Arm.setTargetPosition(EV);

            } else {
                hm.Arm.setPower(0);
            }

        } else if (gamepad2.dpad_up) {

            if (hm.Arm.getCurrentPosition() < 15) {

                hm.Arm.setPower(-0.5);

            } else {
                hm.Arm.setPower(0);
            }

        } else {
            hm.Arm.setPower(0.0);
        }


        // claw
        if (gamepad2.b)
        {
            hm.claw.setPosition(0.0);
        }
        else if (gamepad2.a)
        {
            hm.claw.setPosition(1);
        }


       hm.drive(-gamepad1.left_stick_y * 0.8, gamepad1.left_stick_x * 0.8, gamepad1.right_stick_x * 0.8);

       telemetry.addData("left Y", -gamepad1.left_stick_y);
       telemetry.addData("left X", gamepad1.left_stick_x);
       telemetry.addData("right X", gamepad1.right_stick_x);

        telemetry.addData("Arm", hm.Arm.getCurrentPosition());

        
    }
}

//blahjddertyujhgfdfr