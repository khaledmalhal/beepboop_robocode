/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package beepboop;
import java.awt.*;
import robocode.*;

/**
 * The scared Robot that tries to go to the farthest corner possible from the enemy.
 * @author kmalhal
 */
public class TimidinRobot extends AdvancedRobot
{
    private String lockedEnemy = "";
    private final StateContext context = new StateContext();
    private final TimidinFase0 fase0 = new TimidinFase0(this);
    private final TimidinFase1 fase1 = new TimidinFase1(this);
    private final TimidinFase2 fase2 = new TimidinFase2(this);
    private final TimidinFaseDefault fdefault = new TimidinFaseDefault(this);

    private double[] corner;

    /**
     * This condition is triggered when the corner has been calculated.
     */
    private Condition cornerCondition = new Condition("get_farthest_corner") {
        public boolean test() {
            return (fase0.isCornerCalculated());
        }
    };
    
    /**
     * This condition is triggered when the robot has arrived at the corner.
     */
    private Condition arrivedCorner = new Condition("arrived_corner") {
        public boolean test() {
            return (context.getState() == fase1 &&
                    MyUtils.calculateDistance(getX(), getY(), corner[0], corner[1]) == 0.0);
        }
    };

    /**
     * Core execution of the core.
     * Initially sets the state to {@link TimidinFaseDefault}, enables customs events 
     * and calls for the selected {@link State}'s action in a loop.
     */
    public void run() {
        this.context.setState(fdefault);

        addCustomEvent(cornerCondition);
        addCustomEvent(arrivedCorner);

        while (true) {
            this.context.doAction();
            execute();
        }
    }
    
    /**
     * This event is triggered when the robot scans an enemy.
     * For each state, it's an useful mechanism to change to one another.
     * <p>
     * On default state, change to {@link TimidinFase0} when an enemy has been detected.
     * <p>
     * On {@link TimidinFase1} if an enemy is found while trying to go to the corner, it stops, 
     * fires a bullet and tries to turn around to continue going to the corner.
     * <p>
     * And on {@link TimidinFase2} If an enemy is found while at the corner, stop scanning for more
     * enemies and fire to the enemy until it dies.
     * @param event The enemy event that has been recently scanned.
     */
    public void onScannedRobot(ScannedRobotEvent event) 
    {
        if (this.context.getState() == this.fdefault)
        {
            this.fase0.setBearing(event.getBearing());
            this.fase0.setDistance(event.getDistance());
            this.context.setState(fase0);
            // Reset Radar to same angle as the robot's heading.
            turnRadarLeft(getRadarHeading() - getHeading());
        }
        else if (this.context.getState() == this.fase1 && 
                 this.fase1.isNeedCalculation() == false &&
                 this.fase1.isTurning() == false)
        {
            System.out.println("Found another enemy!");
            setStop();
            fireBullet(Rules.MAX_BULLET_POWER);
            setTurnLeft(60.0);
            setAhead(100.0);
            execute();
        }
        else if (this.context.getState() == this.fase2) {
            lockedEnemy = event.getName();
            this.fase2.setFiring(true);
            this.fase2.setDistance(event.getDistance());
            stop();
            setTurnRight(event.getBearing());
            execute();
        }
    }
    /**
     * This event is triggered when the robot collides with another.
     * If there's a collision with another robot, try to go back, turn
     * yourself around and recalculate the route to the corner.
     * @param event The enemy robot that has collided with the robot.
     */
    public void onHitRobot(HitRobotEvent event) {
        
        setBack(100);
        if (event.getBearing() < 0) {
            setTurnRight(45.0);
        } else {
            setTurnLeft(45.0);
        }
    }
    /**
     * This event is triggered when a robot has died.
     * In the {@link TimidinFase2}, if the robot is firing at a robot and it dies,
     * we change the value of the firing status with {@link TimidinFase2#setFiring(boolean)}
     * and then resume the scanning.
     */
    public void onRobotDeath(RobotDeathEvent event) {
        
        this.fase2.setFiring(false);
        if (event.getName().contains(lockedEnemy)) {
            lockedEnemy = "";
            setResume();
        }
    }

    /**
     * Custom triggered events.
     * There are two custom conditions that we are interested in:
     * <p>
     * One condition is triggered when the corner has been calculated. The other is 
     * when the robot has arrived at the corner.
     * <p>
     * For the first condition we change the robot's State to {@link TimidinFase1} and 
     * for the second one we change to {@link TimidinFase2}.
     * @param e The custom event.
     */
    public void onCustomEvent(CustomEvent e) {
        if (e.getCondition().getName().contains("get_farthest_corner")) {
            // We get the farthest corner, set its values and change state.
            System.out.println("Got farthest corner!");
            corner = this.fase0.getCorner();
            this.fase1.setCorner(corner);
            this.context.setState(fase1);
            removeCustomEvent(cornerCondition);
        }
        else if (e.getCondition().getName().contains("arrived_corner")) {
            // We have arrived at the corner and we change state.
            System.out.println("I got to my corner!");
            removeCustomEvent(arrivedCorner);
            this.context.setState(fase2);
        }
    }

    /**
     * This paints a text on the best corner.
     * @param g The graphical object used to draw.
     */
    public void onPaint(Graphics2D g) {
        // Paint where it's the best corner.
        g.setColor(Color.ORANGE);
        if (this.context.getState() == this.fase1) {
            g.drawOval((int)corner[0], (int)corner[1], 10, 10);
            g.drawString("Best corner!", (int)corner[0], (int)corner[1]);
        }
    }
}