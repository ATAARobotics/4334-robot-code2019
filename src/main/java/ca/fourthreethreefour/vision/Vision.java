package ca.fourthreethreefour.vision;

import ca.fourthreethreefour.teleop.Teleop;

/**
 * Vision
 */
public class Vision {

    Teleop teleop;
    VisionProcessing visionProcessing = new VisionProcessing();

    public Vision(Teleop teleop){
        this.teleop = teleop;
    }

    public void visionInit(){
        visionProcessing.startVision();
    }

    public void endVision(){
        visionProcessing.stopVision();
    }

    public void drive(){
        double turnAngle = visionProcessing.getVisionRotation();
        double speed = visionProcessing.getVisionRotation();
        teleop.ExtArcadeDrive(speed, turnAngle);
    }


}