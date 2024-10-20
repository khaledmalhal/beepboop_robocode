/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package beepboop;
import java.awt.*;
import robocode.*;

/**
 * The scared Robot that tries to go to the farthest possible from the enemy.
 * @author kmalhal
 */
public class TimidinRobot extends AdvancedRobot
{
    private final StateContext context = new StateContext(this);
    private final TimidinFase0 fase0 = new TimidinFase0(this);
    private final TimidinFase1 fase1 = new TimidinFase1(this);
    private final TimidinFase2 fase2 = new TimidinFase2(this);
    private final TimidinFaseDefault fdefault = new TimidinFaseDefault(this);
    
    private Condition cornerCondition = new Condition("get_farthest_corner") {
        public boolean test() {
            return (fase0.isCornerCalculated());
        }
    };
    
    public void run() {
        this.context.setState(fdefault);
        
        addCustomEvent(cornerCondition);
        
        while (true) {
            this.context.doAction();
            execute();
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent event) 
    {
        if (this.context.getState() == this.fdefault)
        {
            this.fase0.setBearing(event.getBearing());
            this.fase0.setDistance(event.getDistance());
            this.fase0.setName(event.getName());
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
            fire(1);
            fire(1);
            fire(1);
            setTurnLeft(60.0);
            setAhead(100.0);
            execute();
        }
    }
    
    public void onHitRobot(HitRobotEvent event) {
        setBack(100);
        if (event.getBearing() < 0) {
            setTurnRight(45.0);
        } else {
            setTurnLeft(45.0);
        }
    }
    
    public void onCustomEvent(CustomEvent e) {
        if (e.getCondition().getName().contains("get_farthest_corner")) {
            System.out.println("Got farthest corner!");
            this.fase1.setCorner(this.fase0.getCorner());
            this.context.setState(fase1);
            removeCustomEvent(cornerCondition);
        }
    }
    
    public void onPaint(Graphics2D g) {
        g.setColor(Color.ORANGE);
        if (this.context.getState() == this.fase1) {
            g.drawOval((int)this.fase0.getCorner()[0], (int)this.fase0.getCorner()[1], 10, 10);
            g.drawString("Best corner!", (int)this.fase0.getCorner()[0], (int)this.fase0.getCorner()[1]);
        }
    }
}