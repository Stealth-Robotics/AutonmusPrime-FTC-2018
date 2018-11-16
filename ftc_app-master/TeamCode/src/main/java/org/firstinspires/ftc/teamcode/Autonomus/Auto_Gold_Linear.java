/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.DropHang;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralCKnockOff_G;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralLKnockOff_G;
import org.firstinspires.ftc.teamcode.Commands.MineralKnockOffPaths.MineralRKnockOff_G;
import org.firstinspires.ftc.teamcode.Commands.MineralPositionDetection;
import org.firstinspires.ftc.teamcode.Commands.Sounds;
import org.firstinspires.ftc.teamcode.Commands.TelemetryLog;
import org.firstinspires.ftc.teamcode.RobotCommands;
import org.firstinspires.ftc.teamcode.Utils.MineralPosition;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Auto Gold Linear", group="Linear Opmode")
//@Disabled
public class Auto_Gold_Linear extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private RobotCommands robotCommands;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robotCommands = new RobotCommands(hardwareMap, telemetry);

        //used to detect witch position the cube is in!
        MineralPositionDetection minPosDector = new MineralPositionDetection(hardwareMap, telemetry);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        //play autobots roll out sound
        Sounds.PlayAutobotsRollOut(hardwareMap);

        DropHang.Run(robotCommands);

        //robotCommands.DriveForTicks(2000, 2000);

        //detect witch position the cube is in!
        MineralPosition minPos = minPosDector.run();/*minPosDector.Run();*/

        //run the correct code to go the the correct spot and move the cube off
        if(minPos == MineralPosition.Left){
            MineralLKnockOff_G.Run(robotCommands);
        } else if(minPos == MineralPosition.Center){
            MineralCKnockOff_G.Run(robotCommands);
        } else {
            MineralRKnockOff_G.Run(robotCommands);
        }

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            TelemetryLog.Run(robotCommands, telemetry);
            telemetry.update();
        }

        robotCommands.KillDriveMotorPower();
    }
}
