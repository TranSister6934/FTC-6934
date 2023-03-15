import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.Scanner;

@TeleOp(name="TeleOp Single", group="Iterative Opmode")

public class TeleOp_Single extends OpMode {

    public int arm_pos , arm_flag , arm_level;

    Hardwaremap hm = new Hardwaremap( );
    int EV = 0;

//    Scanner keyboard = new Scanner(System.in);

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




//        // Arm
//        // (UP)
        if (gamepad1.dpad_up &&  hm.Arm.getCurrentPosition() < 4360)
        {
            hm.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// added
            hm.Arm.setPower(0.5);
            arm_pos = hm.Arm.getCurrentPosition();// added
            hm.Arm.setTargetPosition( arm_pos ); // added
            arm_level = 0;
        }

        // (DOWN)
        else if (gamepad1.dpad_down  &&  hm.Arm.getCurrentPosition( ) > 0)
        {
            hm.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // added
            hm.Arm.setPower(-0.5);
            arm_pos = hm.Arm.getCurrentPosition(); // added
            hm.Arm.setTargetPosition( arm_pos ); // added
            arm_level = 0;
        }

        // added code ....start
        else
        {
            hm.Arm.setTargetPosition(arm_pos);
            hm.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        if (gamepad1.right_trigger > 0.0) {

            if (arm_flag == 0) {

                arm_level = arm_level + 1;

                if (arm_level > 4) arm_level = 4;

                arm_flag = 1;

            }

        } else if (gamepad1.left_trigger > 0.0) {

            if (arm_flag == 0) {

                arm_level = arm_level - 1;

                if (arm_level < 1) arm_level = 1;

                arm_flag = 1;

            }

        } else {

            arm_flag = 0;
        }


        if (arm_level == 1) arm_pos = 0;
        if (arm_level == 2) arm_pos = 1850;
        if (arm_level == 3) arm_pos = 3025;
        if (arm_level == 4) arm_pos = 4255;



        ////////////////////////////////////////////////////////////////////////////////


        // claw
        if (gamepad1.b)
        {
            hm.claw.setPosition(0.0);
        }
        else if (gamepad1.a)
        {
            hm.claw.setPosition(1);
        }


        hm.drive(-gamepad1.left_stick_y * 0.8, gamepad1.left_stick_x * 0.8, gamepad1.right_stick_x * 0.8);

        telemetry.addData("left Y", -gamepad1.left_stick_y);
        telemetry.addData("left X", gamepad1.left_stick_x);
        telemetry.addData("right X", gamepad1.right_stick_x);

        telemetry.addData("Arm", hm.Arm.getCurrentPosition());

        telemetry.addData("Arm Level" , arm_level);


//        String f = keyboard.next();
//        telemetry.addData("key " , f);
//        telemetry.addData("key " , keyboard.nextInt());

        if (arm_level == 1) telemetry.addLine("Ground");
        if (arm_level == 2) telemetry.addLine("Low");
        if (arm_level == 3) telemetry.addLine("Medium");
        if (arm_level == 4) telemetry.addLine("High");
        if (arm_level == 0) telemetry.addLine("Custom");



    }
}
