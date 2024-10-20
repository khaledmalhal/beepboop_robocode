/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;

import robocode.Rules;
import robocode.util.Utils;

/**
 *
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

    private double[] corner;
    private double distance;


    TimidinFase2(TimidinRobot robot) {
        this.robot = robot;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public void setCorner(double[] corner) {
        this.corner = corner;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void turnRobotToOtherCorner(double x, double y) {
        double x1 = x - this.robot.getX();
        double y1 = y - this.robot.getY();
        double angle = Math.atan2(x1, y1);
        double heading = this.robot.getHeadingRadians();

        double targetAngle = Utils.normalRelativeAngle(angle - heading);

        this.robot.setTurnRightRadians(targetAngle);
    }

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
