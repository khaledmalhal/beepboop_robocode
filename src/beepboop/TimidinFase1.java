/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;

/**
 *
 * @author ahkdel
 */
public class TimidinFase1 implements State{

    private TimidinRobot robot;
    private double[] corner;

    public void setCorner(double[] corner) {
        this.corner = corner;
    }
    
    TimidinFase1(TimidinRobot robot) {
        this.robot = robot;
    }
    
    @Override
    public void doAction() {
        System.out.println("Fase 1");
        
    }
}
