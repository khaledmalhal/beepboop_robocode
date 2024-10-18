/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package beepboop;
import robocode.*;

/**
 *
 * @author kmalhal
 */
public class TimidinRobot extends Robot
{
    private final StateContext context = new StateContext();
    private final Fase0 fase0 = new Fase0();
    
    public void run() {
        this.context.setState(fase0);
        context.doAction();
    }
}