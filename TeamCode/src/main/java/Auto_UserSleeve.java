import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Auto_UserSleeve", group = "Tests")
public class Auto_UserSleeve extends OpMode {

    Hardwaremap hm = new Hardwaremap();
    Vuforia_Class V = new Vuforia_Class();
    private String auto_mode = "";
    private int mode = 0;
    private double time;


    @Override
    public void init() {

        V.Init_Vuforia(hardwareMap);
        hm.Init_robot(hardwareMap);

    }

    @Override
    public void init_loop() {

        V.Track_Target();
        telemetry.addLine("Go, Go, GOOOO!");
        telemetry.addLine("Target Name : " + V.target_name);

    }

    @Override
    public void start() {

        time = 0.5;

        resetRuntime();
        if ( V.target_name == "Sheep"){
            auto_mode = "drive_1";
            mode = 1;
        }

        resetRuntime();
        if (V.target_name == "Hearts"){
            auto_mode = "drive_2";
            mode = 1;
        }

        resetRuntime();
        if (V.target_name == "Stars"){
            auto_mode = "drive_3";
            mode = 1;
        }

        resetRuntime();
        if (V.target_name == "Null"){
            auto_mode = "sneak";
            mode = 1;
        }

    }

    @Override
    public void loop() {

        telemetry.addData("drive mode  " , auto_mode);
        telemetry.addData("time  " , time);

        switch (auto_mode) {

            case "sneak" :
                switch (mode) {

                    case 1 :

                        hm.drive(0.8,0.0,0.0);
                        hm.claw.setPosition(1);
                        if (this.getRuntime() > 0.5) {
                            hm.drive(0.0,0.0,0.0);
                            resetRuntime();
                            mode = 2;
                            auto_mode = "get_vuforia";
                        }

                        break;

                }



                break; //break for auto mode

            case "get_vuforia" :
                V.Track_Target();
                if (this.getRuntime() > 3.0) {
                    time = 0.3;
                    resetRuntime();
                    if ( V.target_name == "Sheep"){
                        auto_mode = "drive_1";
                        mode = 1;
                    }

                    if (V.target_name == "Hearts"){
                        auto_mode = "drive_2";
                        mode = 1;
                    }

                    if (V.target_name == "Stars"){
                        auto_mode = "drive_3";
                        mode = 1;
                    }

                    if (V.target_name == "Null"){
                        auto_mode = "drive_alt";
                        mode = 1;

                    }
                }
                break;

            case "drive_1" :

                switch (mode) {

                    case 1 :

                        hm.drive(1.0,0.0,0.0);
                        hm.claw.setPosition(1);
                        if (this.getRuntime() > time) {
                            resetRuntime();
                            mode = 2;
                        }

                        break;

                    case 2 :

                        hm.drive(-0.0,0.0,1.0);
                        if (this.getRuntime() > 0.45) {
                            resetRuntime();
                            mode = 3;
                        }

                        break;

                    case 3 :

                        hm.drive(-1.0,-0.0,0.0);
                        if (this.getRuntime() > 0.45) {
                            resetRuntime();
                            mode = 0;
                            auto_mode = "stop";
                        }

                        break;

                }

                break; //break for auto mode

            case "drive_2" :

                switch (mode) {

                    case 1 :

                        hm.drive(1.0,0.0,0.0);
                        hm.claw.setPosition(1);
                        if (this.getRuntime() > time) {
                            resetRuntime();
                            mode = 0;
                            auto_mode = "stop";
                        }

                        break;

                }

                break; //break for auto mode

            case "drive_3" :

                switch (mode) {

                    case 1 :

                        hm.drive(1.0,0.0,0.0);
                        hm.claw.setPosition(1);
                        if (this.getRuntime() > time) {
                            resetRuntime();
                            mode = 2;
                        }

                        break;

                    case 2 :

                        hm.drive(-0.0,0.0,-1.0);
                        if (this.getRuntime() > 0.45) {
                            resetRuntime();
                            mode = 3;
                        }

                        break;

                    case 3 :

                        hm.drive(-1.0,0.0,0.0);
                        if (this.getRuntime() > 0.45) {
                            resetRuntime();
                            mode = 3;
                            auto_mode = "stop";
                        }

                        break;


                }

                break; //break for auto mode

            case "drive_alt" :

                switch (mode) {

                    case 1 :

                        hm.drive(-1.0,0.0,0.0);
                        hm.claw.setPosition(1);
                        if (this.getRuntime() > 0.4) {
                            resetRuntime();
                            mode = 2;
                        }

                    case 2 :

                        hm.drive(-0.0,1.0,-0.0);
                        if (this.getRuntime() > 0.7) {
                            resetRuntime();
                            mode = 0;
                            auto_mode = "stop";
                        }

                        break;


                }



                break; //break for auto mode

            case "stop" :

                hm.drive(-0.0,0.0,0.0);
                //resetRuntime();
                auto_mode = "";

                break;

            case "" :
                telemetry.addLine("Ta-da!!");

                break;


        }

    }
}
