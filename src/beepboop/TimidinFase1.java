/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;
import robocode.*;
import robocode.util.Utils;

/**
 *
 * @author ahkdel
 */
public class TimidinFase1 implements State {

    private TimidinRobot robot;
    private double[] corner;
    private boolean needCalculation = true;
    
    public void setNeedCalculation(boolean needCalculation) {
        this.needCalculation = needCalculation;
    }

    TimidinFase1(TimidinRobot robot) {
        this.robot = robot;
    }
    
    public void setCorner(double[] corner) {
        this.corner = corner;
    }
    
    /**
     * Calculates the angles that the robots has to turn to go to the desired corner, then it is set to go ahead.
     * Snippet code obtained from https://robowiki.net/wiki/GoTo.
     */
    public void turnRobotToCorner() {
        double x = corner[0] - this.robot.getX();
        double y = corner[1] - this.robot.getY();
        double angle = Math.atan2(x, y);
        double heading = this.robot.getHeadingRadians();
        /*double maxY = this.robot.getBattleFieldHeight();
        double dist = MyUtils.calculateDistance(x, y, corner[0], corner[1]);
        
        if (corner[1] == 0.0) {
            angle = Math.acos(y/dist);
        } else {
            angle = Math.acos((maxY-y)/dist);
        }*/
        double targetAngle = Utils.normalRelativeAngle(angle - heading);
        
        this.robot.setTurnRightRadians(targetAngle);
        this.needCalculation = false;
    }
    
    @Override
    public void doAction() {
        // System.out.println("Fase 1");
        if (this.needCalculation == true)
            turnRobotToCorner();
        
        if (this.robot.getTurnRemainingRadians() == 0) {
            double x = this.robot.getX();
            double y = this.robot.getY();
            double dist = MyUtils.calculateDistance(x, y, corner[0], corner[1]);
            this.robot.setAhead(dist);
        }
    }
}
