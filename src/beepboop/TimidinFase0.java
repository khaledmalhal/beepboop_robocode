/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;
import robocode.*;

/**
 * <p>
 * In this State, the robot will calculate the farthest corner in the map 
 * when it encounters an enemy.
 * @see <p>Calculations are based on this <a href="https://stackoverflow.com/a/22743501">answer</a>.</p>
 * @author kmalhal
 */
public class TimidinFase0 implements State 
{
    private TimidinRobot robot;
    private double _bearing;
    private double _distance;
    private boolean cornerCalculated = false;
    private double[] corner;
    
    /**
     * Initializes the Fase0 State. Ideally, it should be a singleton.
     * @param robot It's the robot that will calculate the best corner in the map 
     * when it encounters an enemy. 
     */
    public TimidinFase0(TimidinRobot robot) {
        this.robot = robot;
    }

    /**
     * The flag that determines if the corner has been calculated or not.
     * @return If the corner has been calculated, this will return true. Otherwise, returns false.
     */
    public boolean isCornerCalculated() {
        return this.cornerCalculated;
    }
    
    /**
     * After the custom event in {@link TimidinRobot} finishes catching the event 
     * {@code "get_farthest_corner"} you should change this value to false.
     * @param cornerCalculated The flag which determines if the corner has been calculated or not.
     */
    public void setCornerCalculated(boolean cornerCalculated) {
        // System.out.println("Changing corner calculated.");
        this.cornerCalculated = cornerCalculated;
    }

    /**
     * Returns the best corner in the map after being calculated.
     * @return The corner that the robot should go to.
     */
    public double[] getCorner() {
        return this.corner;
    }

    /**
     * Sets the bearing of the enemy robot. 
     * This is specially useful for the calculations.
     * @param _bearing The angle between the robot's heading and the enemy.
     */
    public void setBearing(double _bearing) {
        this._bearing = _bearing;
    }

    /**
     * Sets the distance from the user's robot to the enemy's robot.
     * This is specially useful for the calculations.
     * @param _distance The distance from the robot to the enemy.
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * Calculates the farthest corner of a robot. 
     * <p>
     * This is used for getting the corner where our {@link TimidinRobot} will go after
     * calculating the enemy's position.
     * @param x The enemy's X coordinate.
     * @param y The enemy's Y coordinate.
     * @return An array of the farthest corner, where array[0] is the X coordinates 
     * and array[1] is the Y coordinates.
     */
    public double[] calculateFarthestCorner(double x, double y) 
    {
        double adjust = 30.0;
        double width = this.robot.getBattleFieldWidth();
        double height = this.robot.getBattleFieldHeight();
        double[] coord = {0.0, 0.0};
        double[] corner2 = {width, 0.0};
        double[] corner3 = {0.0, height};
        double[] corner4 = {width, height};
        double far = MyUtils.calculateDistance(x, y, coord[0], coord[1]);
        double res = MyUtils.calculateDistance(x, y, corner2[0], corner2[1]);
        if (res > far) {
            far = res;
            coord = corner2;
            coord[0] = coord[0] - adjust;
            coord[1] = coord[1] + adjust;
        } else {
            coord[0] = coord[0] + adjust;
            coord[1] = coord[1] + adjust;
        }
        res = MyUtils.calculateDistance(x, y, corner3[0], corner3[1]);
        if (res > far) {
            far = res;
            coord = corner3;
            coord[0] = coord[0] + adjust;
            coord[1] = coord[1] - adjust;
        }
        res = MyUtils.calculateDistance(x, y, corner4[0], corner4[1]);
        if (res > far) {
            far = res;
            coord = corner4;
            coord[0] = coord[0] - adjust;
            coord[1] = coord[1] - adjust;
        }
        return coord;
    }
    
    /**
     * Returns the angle from 0º to the enemy.
     * The angle is calculated based on the heading of the user's robot.
     * @return The angle to the enemy in Radians.
     * @see <p>Calculations are based on this <a href="https://stackoverflow.com/a/22743501">answer</a>.</p>
     */
    public double getEnemyAngle() {
        double bearing = this._bearing;
        double angle = (bearing % 360) + this.robot.getHeading();
        return Math.toRadians(angle);
    }
    /**
     * The Fase 0 State's action.
     * <p>
     * It first calculates the enemy's position, then from this position, it
     * tries to find what's the best corner that is farthest to the enemy.
     * <p>
     * The enemy position is based on the trigonometric functions 
     * {@code cosθ = adjacent / hypotenuse} and {@code sinθ = opposite / hypotenuse},
     * with the hypotenuse being the distance to the enemy and adding the robot's position.
     */
    @Override
    public void doAction() {
        double angle = getEnemyAngle();
        System.out.println("Timidin. Fase 0");
        double xEnemy = (Math.sin(angle) * this._distance) + this.robot.getX();
        double yEnemy = (Math.cos(angle) * this._distance) + this.robot.getY();
        System.out.printf("Angle: %f. Enemy is at (%f, %f)\n", 
                           angle, xEnemy, yEnemy);
        double[] coord = calculateFarthestCorner(xEnemy, yEnemy);
        System.out.printf("Best corner: (%f, %f)\n", coord[0], coord[1]);
        this.corner = coord;
        setCornerCalculated(true);
    }    
}
