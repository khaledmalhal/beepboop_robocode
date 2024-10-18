/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package beepboop;
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
            setAdjustRadarForGunTurn(true);
            setAdjustGunForRobotTurn(true);
            this.context.doAction();
            execute();
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent event) {
        System.out.printf("""
                          Enemy's bearing: %f
                          Enemy's name: %s
                          Enemy's distance: %f
                          """, 
                          event.getBearing(), event.getName(), event.getDistance());
        System.out.printf("""
                          My heading: %f
                          x, y: (%f, %f)
                          """, this.getHeading(), this.getX(), this.getY());
        this.fase0.setBearing(event.getBearing());
        this.fase0.setDistance(event.getDistance());
        this.fase0.setName(event.getName());
        this.context.setState(fase0);
        /*if (event.getDistance() < 100) {
            fire(3);
        } else {
            fire(1);
        }*/
    }
    
    public void onCustomEvent(CustomEvent e) {
        // System.out.printf("Got custom event! %s\n", e.getCondition().getName());
        if (e.getCondition().getName().contains("get_farthest_corner")) {
            System.out.println("Got farthest corner!");
            this.fase1.setCorner(this.fase0.getCorner());
            this.context.setState(fase1);
            removeCustomEvent(cornerCondition);
            // this.fase0.setCornerCalculated(false);
        }
    }
}