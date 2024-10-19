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
 * @author kmalhal
 */
public class TimidinFase0 implements State 
{
    private TimidinRobot robot;
    private double _bearing;
    private double _distance;
    private String _name;
    private boolean cornerCalculated = false;
    private double[] corner;
    
    /**
     * @param robot It's the robot that will calculate the best corner in the map 
     * when it encounters an enemy. 
     */
    public TimidinFase0(TimidinRobot robot) {
        this.robot = robot;
    }

    /**
     * @return If the corner has been calculated, this will return true.
     */
    public boolean isCornerCalculated() {
        return this.cornerCalculated;
    }
    
    /**
     * After the custom event in {@link TimidinRobot} finishes catching the event "get_farthest_corner"
     * you should change this value to false.
     * @param cornerCalculated 
     */
    public void setCornerCalculated(boolean cornerCalculated) {
        // System.out.println("Changing corner calculated.");
        this.cornerCalculated = cornerCalculated;
    }

    /**
     * @return The corner that the robot should go to.
     */
    public double[] getCorner() {
        return this.corner;
    }

    /**
     * Sets the bearing of the enemy robot.
     * @param _bearing 
     */
    public void setBearing(double _bearing) {
        this._bearing = _bearing;
    }

    /**
     * Sets the distance from the user's robot to the enemy's robot.
     * @param _distance 
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * Sets the enemy's robot name.
     * @param _name 
     */
    public void setName(String _name) {
        this._name = _name;
    }
    
    /**
     * Obtains the farthest corner of a robot. 
     * <p>
     * This is used for getting the corner where our {@link robot} will go after
     * calculating the enemy's position.
     * @param x The enemy's X coordinate.
     * @param y The enemy's Y coordinate.
     * @return An array of the farthest corner, where array[0] is the X and array[1] is the Y coordinates.
     */
    public double[] getFarthestCorner(double x, double y) 
    {
        double width = this.robot.getBattleFieldWidth();
        double height = this.robot.getBattleFieldHeight();
        double[] coord = {0.0, 0.0};
        double[] corner2 = {width, 0.0};
        double[] corner3 = {0.0, height};
        double[] corner4 = {width, height};
        double far = Utils.calculateDistance(x, y, coord[0], coord[1]);
        double res = Utils.calculateDistance(x, y, corner2[0], corner2[1]);
        if (res > far) {
            far = res;
            coord = corner2;
        }
        res = Utils.calculateDistance(x, y, corner3[0], corner3[1]);
        if (res > far) {
            far = res;
            coord = corner3;
        }
        res = Utils.calculateDistance(x, y, corner4[0], corner4[1]);
        if (res > far) {
            far = res;
            coord = corner4;
        }
        return coord;
    }
    
    public double getEnemyAngle() {
        double bearing = this._bearing;
        double angle = (bearing % 360) + this.robot.getHeading();
        return Math.toRadians(angle);
    }
    
    @Override
    public void doAction() {
        double angle = getEnemyAngle();
        System.out.println("Timidin. Fase 0");
        double xEnemy = (Math.sin(angle) * this._distance) + this.robot.getX();
        double yEnemy = (Math.cos(angle) * this._distance) + this.robot.getY();
        System.out.printf("Angle: %f. Enemy is at (%f, %f)\n", 
                           angle, xEnemy, yEnemy);
        double[] coord = getFarthestCorner(xEnemy, yEnemy);
        System.out.printf("Best corner: (%f, %f)\n", coord[0], coord[1]);
        this.corner = coord;
        setCornerCalculated(true);
    }    
}
