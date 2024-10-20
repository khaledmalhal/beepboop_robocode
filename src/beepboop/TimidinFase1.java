/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;
import robocode.*;
import robocode.util.Utils;

/**
 * In this State, the robot calculates the route it needs to take to move to the desired corner.
 * @author ahkdel
 */
public class TimidinFase1 implements State {

    private TimidinRobot robot;
    private double[] corner;
    private boolean needCalculation = true;
    private boolean turning = true;

    /**
     * Constructor of the class. Ideally, it's a singleton.
     * @param robot The robot that will be moving to the desired corner.
     */
    TimidinFase1(TimidinRobot robot) {
        this.robot = robot;
    }
    
    /**
     * If calculations are needed, this returns true.
     * <p>
     * In the case that the robot might be doing something else, like colliding 
     * with another enemy, this will be ideally be false so the robot can finish 
     * fixing its situation before going back to moving to the corner.
     * <p>
     * Another scenario is when the robot has reached its destination and this is set to false.
     * @return If calculations are needed, this returns true.
     */
    public boolean isNeedCalculation() {
        return needCalculation;
    }
    
    /**
     * Set the need for calculations for the route to the corner.
     * @param needCalculation True if you want to continue calculating the route, false if otherwise.
     */
    public void setNeedCalculation(boolean needCalculation) {
        this.needCalculation = needCalculation;
    }

    /**
     * Returns if the robot is turning itself around.
     * @return If this robot is turning itself around, return true. Otherwise, false.
     */
    public boolean isTurning() {
        return turning;
    }

    /**
     * Set the status of the robot if it's turning itself or not.
     * @param turning True if the robot it turning itself. Otherwise, false.
     */
    public void setTurning(boolean turning) {
        this.turning = turning;
    }
    
    /**
     * Set the desired corner that the robot has to go.
     * @param corner It's an array in the format [x, y].
     */
    public void setCorner(double[] corner) {
        this.corner = corner;
    }
    
    /**
     * Calculates the angles that the robots has to turn to go to the desired corner.
     * @see <a href="https://robowiki.net/wiki/GoTo">Snippet code.</a>
     */
    public void turnRobotToCorner() {
        double x = corner[0] - this.robot.getX();
        double y = corner[1] - this.robot.getY();
        double angle = Math.atan2(x, y);
        double heading = this.robot.getHeadingRadians();
        
        double targetAngle = Utils.normalRelativeAngle(angle - heading);
        
        this.robot.setTurnRightRadians(targetAngle);
        this.needCalculation = false;
        this.turning = true;
    }
    
    /**
     * State action of turning and moving the robot to the desired corner.
     * The robot first calculates the angles needed to take to turn itself around 
     * to the desired corner (calculated previously in {@link TimidinFase0}), then 
     * it moves in a straight line to the desired corner.
     * <p>
     * If there are obstacles like another enemies, it will stop, fire a bullet at
     * the enemy, go back, turn itself around to avoid the obstacle, then recalculate the route once again.
     * <p>
     * It might run around for a while before it finally finds the corner.
     */
    @Override
    public void doAction() {
        if (this.needCalculation == true && this.robot.getTurnRemainingRadians() == 0.0) {
            turnRobotToCorner();
        }
        else if (this.turning == true && this.robot.getTurnRemainingRadians() == 0.0) {
            setTurning(false);
        }
        else if (this.turning == false && 
            this.robot.getTurnRemainingRadians() == 0.0) {
            setNeedCalculation(true);
            double x = this.robot.getX();
            double y = this.robot.getY();
            double dist = MyUtils.calculateDistance(x, y, corner[0], corner[1]);
            this.robot.setAhead(dist);
        }
    }
}
