

package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


import org.firstinspires.ftc.teamcode.MecanumDrive;
import java.util.ArrayList;


@Autonomous
public class BlueLeftAutoClosePark extends LinearOpMode {
    OpenCvCamera camera;

    static final double FEET_PER_METER = 3.28084;

    int LEFT = 0;
    int MIDDLE = 1;
    int RIGHT = 2;
    // AprilTagDetection tagOfInterest = null;
    DcMotor leftWinch;
    DcMotor rightWinch;

    DcMotor intake;

    DcMotor shooter;

    Servo hips;
    Servo arch;
    Servo frontLeg;
    Servo backLeg;

    Servo sneakyLink; // facing robot (sneaky link suuuus)
    Servo sneakyRink;
    MecanumDrive drive;
    int zone = 0;


    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode () throws InterruptedException {
        leftWinch = hardwareMap.dcMotor.get("leftWinch");
        rightWinch = hardwareMap.dcMotor.get("rightWinch");
        shooter = hardwareMap.dcMotor.get("shooter");
        intake = hardwareMap.dcMotor.get("intake");
        hips = hardwareMap.servo.get("hips");
        arch = hardwareMap.servo.get("arch");
        frontLeg = hardwareMap.servo.get("frontLeg");
        backLeg = hardwareMap.servo.get("backLeg");
        sneakyLink = hardwareMap.servo.get("sneakyLink");
        sneakyRink = hardwareMap.servo.get("sneakyRink");
        drive = new MecanumDrive(hardwareMap, new Pose2d(12.00, 63, Math.toRadians(90)));
        TeamPropDetector.startPropDetection(hardwareMap, telemetry);
        drive.setTelemetry(telemetry);


        rightWinch.setDirection(DcMotorSimple.Direction.REVERSE);
        leftWinch.setDirection((DcMotorSimple.Direction.FORWARD));


        leftWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightWinch.setTargetPosition(0);
        leftWinch.setTargetPosition(0);
//        chainBar.setTargetPosition(ARM_0);
        rightWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hips.setPosition(.27);
        arch.setPosition(.69);
        backLeg.setPosition(.77);  // .9 is closed pos
        frontLeg.setPosition(.52); //  .6 is closed pos
        sneakyLink.setPosition(1); //  up from 0
        sneakyRink.setPosition(0); // down from 0


        waitForStart();
        zone = TeamPropDetector.getBluePropZone();
        TeamPropDetector.endPropDetection();
        //Actions.runBlocking(drive.actionBuilder(new Pose2d(12.00, 63, Math.toRadians(90))).strafeTo(new Vector2d(12,30)).build());
        //Drive to SPIke Mark
        if (zone == 1) {
            Actions.runBlocking(drive.actionBuilder(new Pose2d(12.00, 63, Math.toRadians(90)))
                    .strafeToLinearHeading(new Vector2d(13, 33), Math.toRadians(110))// y down is more up
                    .strafeTo(new Vector2d(14, 40))
                    .strafeToLinearHeading(new Vector2d(14, 34), Math.toRadians(180))
                    .build());
            backLeg.setPosition(.9);
            frontLeg.setPosition(.7);
            sleep(300);
            rightWinch.setTargetPosition(-800);
            rightWinch.setPower(.8);
            leftWinch.setTargetPosition(-800);
            leftWinch.setPower(.8);
            sleep(1000);
            arch.setPosition(.363);
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(50,35))
                    .build());
            sleep(300);
            hips.setPosition(.27);
            backLeg.setPosition(.62);  // .9 is closed pos
            frontLeg.setPosition(.4);
            sleep(300);
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(38,14))
                    .build());

            backLeg.setPosition(.85);  // .9 is closed pos
            sleep(50);
            frontLeg.setPosition(.6);
            sleep(50);
            hips.setPosition(.18);
            sleep(200);
            arch.setPosition(.69);
            sleep(50);
            rightWinch.setTargetPosition(-20);
            rightWinch.setPower(1);
            leftWinch.setTargetPosition(-20);
            leftWinch.setPower(1);
            hips.setPosition(.27);
            backLeg.setPosition(.76);  // .9 is closed pos
            frontLeg.setPosition(.52);


        } else if (zone == 2) {
            Actions.runBlocking(drive.actionBuilder(new Pose2d(12.00, 63, Math.toRadians(90)))
                    .strafeTo(new Vector2d(11, 34))
                    .strafeTo(new Vector2d(11, 40))
                    .turnTo(Math.toRadians(180))
                    .strafeTo(new Vector2d(44, 40))
                    .build());
            backLeg.setPosition(.9);
            frontLeg.setPosition(.7);
            sleep(300);
            rightWinch.setTargetPosition(-800);
            rightWinch.setPower(.8);
            leftWinch.setTargetPosition(-800);
            leftWinch.setPower(.8);
            sleep(1000);
            arch.setPosition(.363);
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(51,44))
                    .build());
            sleep(300);
            hips.setPosition(.27);
            backLeg.setPosition(.62);  // .9 is closed pos
            frontLeg.setPosition(.4);
            sleep(300);
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(47,30))
                    .build());

            backLeg.setPosition(.85);  // .9 is closed pos
            sleep(50);
            frontLeg.setPosition(.6);
            sleep(50);
            hips.setPosition(.18);
            sleep(200);
            arch.setPosition(.69);
            sleep(50);
            rightWinch.setTargetPosition(-20);
            rightWinch.setPower(1);
            leftWinch.setTargetPosition(-20);
            leftWinch.setPower(1);
            hips.setPosition(.27);
            backLeg.setPosition(.76);  // .9 is closed pos
            frontLeg.setPosition(.52);
        } else {
            Actions.runBlocking(drive.actionBuilder(new Pose2d(12.00, 63, Math.toRadians(90)))
                    .strafeToLinearHeading(new Vector2d(8, 32), Math.toRadians(65))
                    .strafeToLinearHeading(new Vector2d(-2, 50), Math.toRadians(65))//                    .strafeToConstantHeading(new Vector2d(15, 38.51))
//                    .strafeToLinearHeading(new Vector2d(8.83, 33.33), Math.toRadians(0))
//                    .strafeToConstantHeading(new Vector2d(14, 35.33))
                    .build());
        }
        drive.updatePoseEstimate();
        telemetry.addLine("Pose" + drive.pose.position);
        telemetry.addLine("HEading" + Math.toDegrees(drive.pose.heading.log()));
        telemetry.update();


        //Drive to Backdrop

        if (zone == 1) {
            sleep(1000);
            Actions.runBlocking(drive.actionBuilder(drive.pose)

//                    .strafeTo(new Vector2d(43, 66))
                    .strafeToLinearHeading(new Vector2d(38,13), Math.toRadians(270))
                                    .strafeTo(new Vector2d(20,17))
//                    .strafeTo(new Vector2d(36,65)) // x down moves it left, y down moves it up
                    //.strafeToLinearHeading(new Vector2d(14, 34), Math.toRadians(180))

                    .build());
            sleep(100);
        } else if (zone == 2) {
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                   // .strafeTo(new Vector2d(45, 30))
                    .strafeToLinearHeading(new Vector2d(45, 30), Math.toRadians(270))
                    .strafeTo(new Vector2d(45, 40))
                            .strafeTo(new Vector2d(35,40))

                    .build());
        } else  Actions.runBlocking(drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(-2, 50), Math.toRadians(180))
                .strafeToConstantHeading(new Vector2d(12,45))

                .build());
        drive.updatePoseEstimate();
        telemetry.addLine("Pose" + drive.pose.position);
        telemetry.addLine("HEading" + Math.toDegrees(drive.pose.heading.log()));
        telemetry.update();


        if(zone == 1){
            sleep(100);
//            backLeg.setPosition(.9);
//            frontLeg.setPosition(.7);
//            sleep(300);
//            rightWinch.setTargetPosition(-1000);
//            rightWinch.setPower(.8);
//            leftWinch.setTargetPosition(-1000);
//            leftWinch.setPower(.8);
//            sleep(1000);
//            arch.setPosition(.363);
//            Actions.runBlocking(drive.actionBuilder(drive.pose)
//                    .strafeToConstantHeading(new Vector2d(45,30))
//                    .build());
//            sleep(300);
//            hips.setPosition(.27);
//            backLeg.setPosition(.62);  // .9 is closed pos
//            frontLeg.setPosition(.4);
//            sleep(300);
        }
        else if(zone == 2 ){
            sleep(200);
        }
        else{

            backLeg.setPosition(.9);
            frontLeg.setPosition(.7);
            sleep(300);
            rightWinch.setTargetPosition(-800);
            rightWinch.setPower(.8);
            leftWinch.setTargetPosition(-800);
            leftWinch.setPower(.8);
            sleep(1000);
            arch.setPosition(.363);
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(29,59))
                    .build());
            sleep(300);
            hips.setPosition(.27);
            backLeg.setPosition(.62);  // .9 is closed pos
            frontLeg.setPosition(.4);
            sleep(300);

        }

        drive.updatePoseEstimate();
        telemetry.addLine("Pose" + drive.pose.position);
        telemetry.addLine("HEading" + Math.toDegrees(drive.pose.heading.log()));
        telemetry.update();


        if(zone ==1 ){

//            Actions.runBlocking(drive.actionBuilder(drive.pose)
//                    .strafeToConstantHeading(new Vector2d(40,35))
//                    .build());

            sleep(100);
        }
        else if(zone == 2 ){
            sleep(200);


        }
        else{
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(25,65))
                    .build());

            backLeg.setPosition(.85);  // .9 is closed pos
            sleep(50);
            frontLeg.setPosition(.6);
            sleep(50);
            hips.setPosition(.18);
            sleep(200);
            arch.setPosition(.69);
            sleep(50);
            rightWinch.setTargetPosition(-20);
            rightWinch.setPower(1);
            leftWinch.setTargetPosition(-20);
            leftWinch.setPower(1);
            hips.setPosition(.27);
            backLeg.setPosition(.76);  // .9 is closed pos
            frontLeg.setPosition(.52);

        }


        //PARK
        if(zone ==1 ){

//            Actions.runBlocking(drive.actionBuilder(drive.pose)
//                    .strafeTo(new Vector2d(40, 10))
//                    .build());
            sleep(100);
        }
        else if(zone == 2 ){
            sleep(200);
        }
        else{
            Actions.runBlocking(drive.actionBuilder(drive.pose)
                    .strafeTo(new Vector2d(25, 22))
                    .turnTo(Math.toRadians(270))
                    .strafeToConstantHeading(new Vector2d(12,26))//
                    .build());
        }



    }









}

