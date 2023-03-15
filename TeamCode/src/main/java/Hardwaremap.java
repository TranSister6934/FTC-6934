import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardwaremap {

       //Constructor
    LinearOpMode opmode;

    public Hardwaremap(LinearOpMode opmode ){
        this.opmode = opmode;
    }

    public Hardwaremap(){
    }


    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 537.7  ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    public double     DRIVE_SPEED             = 0.4;
    public double     TURN_SPEED              = 0.5;

    public DcMotor FrontRight = null;
    public DcMotor FrontLeft = null;
    public DcMotor BackRight = null;
    public DcMotor BackLeft = null;
    public DcMotor Arm = null;
    public Servo claw = null;

    HardwareMap hwMap = null;

    public void Init_robot(HardwareMap ahwMap) {

        hwMap = ahwMap;

        FrontRight = hwMap.dcMotor.get("FR");
        FrontLeft = hwMap.dcMotor.get("FL");
        BackRight = hwMap.dcMotor.get("BR");
        BackLeft = hwMap.dcMotor.get("BL");
        Arm = hwMap.dcMotor.get("Arm");
        claw = hwMap.get(Servo.class, "Claw");

        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        Arm.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ///////////////////////////////////////////////////////////////

        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /////////////////////////////////////////////////////////////

        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive(double axial, double lateral, double yaw) {


        double max;

        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }


        FrontRight.setPower(rightFrontPower);
        FrontLeft.setPower(leftFrontPower);
        BackRight.setPower(rightBackPower);
        BackLeft.setPower(leftBackPower);

    }

    public void moveArm(int ticks, double timeoutS){
        Arm.setTargetPosition(ticks);
        Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Arm.setPower(1);

        runtime.reset();

        while ( opmode.opModeIsActive() && (runtime.seconds() < timeoutS) &&
                (Arm.isBusy())) {
        }

        Arm.setPower(0);

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches, double backLeftInches, double backRightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        if (opmode.opModeIsActive()) {

            newLeftTarget = FrontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = FrontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newBackLeftTarget = BackLeft.getCurrentPosition() + (int)(backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = BackRight.getCurrentPosition() + (int)(backRightInches * COUNTS_PER_INCH);


            FrontLeft.setTargetPosition(newLeftTarget);
            FrontRight.setTargetPosition(newRightTarget);
            BackLeft.setTargetPosition(newBackLeftTarget);
            BackRight.setTargetPosition(newBackRightTarget);

            FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            FrontLeft.setPower(Math.abs(speed));
            FrontRight.setPower(Math.abs(speed));
            BackLeft.setPower(Math.abs(speed));
            BackRight.setPower(Math.abs(speed));

            while (opmode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (FrontLeft.isBusy() && FrontRight.isBusy() && BackLeft.isBusy() && BackRight.isBusy())) {
            }

            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);


            FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            opmode.sleep(250);
        }
    }

    public void liftArm(double speed, int ticks, double timeoutS) {

        if (opmode.opModeIsActive()) {

            Arm.setTargetPosition(ticks);

            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            Arm.setPower(Math.abs(speed));

            while (opmode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (Arm.isBusy())) {
            }

            Arm.setPower(0.1);

//            Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }





}
