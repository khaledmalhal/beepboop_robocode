/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;

import robocode.Rules;
import robocode.util.Utils;

/**
 * In this State, the robot scans the map by turning itself and fires bullets 
 * at the enemies with the power based on their distance.
 * @author ahkdel
 */
public class TimidinFase2 implements State
{
    private TimidinRobot robot;
    private boolean calculated = false;
    private boolean first = true;
    private boolean firing = false;

    private double diff = Rules.MAX_BULLET_POWER - Rules.MIN_BULLET_POWER;
    private double maxDist;
    private double slope;
    private double yInter;

    private double distance;

    /**
     * Constructor of the class. Ideally, it should be a singleton
     * @param robot The {@link TimidinRobot} in said state.
     */
    TimidinFase2(TimidinRobot robot) {
        this.robot = robot;
    }

    /**
     * Set this value to true when the robot is firing, ideally at some enemies.
     * @param firing Firing flag of the robot.
     */
    public void setFiring(boolean firing) {
        this.firing = firing;
    }
    
    /**
     * Set the distance between the robot and the enemy.
     * @param distance Distance between the robot and the enemy.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Turn the robot the other corners so it scans the map for more enemies.
     * Heavily based on {@link TimidinFase1#turnRobotToCorner()} this method turns
     * the robot to the desired position.
     * @param x X coordinates of the position to turn to.
     * @param y Y coordinates of the position to turn to.
     * @see TimidinFase1#turnRobotToCorner()
     */
    public void turnRobotToOtherCorner(double x, double y) {
        double x1 = x - this.robot.getX();
        double y1 = y - this.robot.getY();
        double angle = Math.atan2(x1, y1);
        double heading = this.robot.getHeadingRadians();

        double targetAngle = Utils.normalRelativeAngle(angle - heading);

        this.robot.setTurnRightRadians(targetAngle);
    }

    /**
     * Scan the map when you are sitting on a corner.
     * When the robot is at one corner, it will scan from and to the corners 
     * perpendicular to itself.
     */
    public void scan() {
        System.out.println("Scanning...");
        double minXCorner = 30.0;
        double maxXCorner = this.robot.getBattleFieldWidth();
        double minYCorner = 30.0;
        double maxYCorner = this.robot.getBattleFieldHeight();
        double x, y;

        if (first == true) {
            if (this.robot.getY() == minYCorner) {
                y = maxYCorner;
            } else {
                y = 0.0;
            }
            x = this.robot.getX();
            first = false;
        } else {
            if (this.robot.getX() == minXCorner) {
                x = maxXCorner;
            } else {
                x = 0.0;
            }
            y = this.robot.getY();
            first = true;
        }
        turnRobotToOtherCorner(x, y);
    }

    /**
     * Scan the map, calculate the power needed to shot at the enemy and fire bullets.
     * <p>
     * The power calculation is a linear function with a negative slope. When the 
     * enemy is far, it will shoot at minimal power, and vice versa when the enemy is close.
     * <p>
     * When the robot find an enemy, it stops turning and shoots at it until the enemy 
     * dies before continuing scanning the map again.
     */
    @Override
    public void doAction()
    {
        if (calculated == false) {
            /* This is here to make these calculation only once.
             * And we like it all parametrized so we don't have to put numerical
             * values in case that these might change.
             */
            maxDist = MyUtils.calculateDistance(0.0, 0.0,
                this.robot.getBattleFieldWidth(),
                this.robot.getBattleFieldHeight());
            slope = diff / (10 - maxDist);
            yInter = Rules.MIN_BULLET_POWER - (slope * maxDist);
            calculated = true;
        }
        if (this.robot.getTurnRemaining() == 0.0 && this.firing == false)
            scan();
        if (this.firing == true)
        {
            double power = (slope * this.distance) + yInter;

            System.out.printf("Firing bullet with power of %f (dist: %f)\n",
                    power, this.distance);
            this.robot.fireBullet(power);
        }
    }
}
