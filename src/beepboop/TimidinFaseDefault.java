/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;
import robocode.*;

/**
 * The phase where the robot does nothing but scanning its surroundings for enemies.
 * @author ahkdel
 */
public class TimidinFaseDefault implements State
{
    private TimidinRobot robot;
    
    /**
     * <p>
     * This is the default state of the robot, where it'll be scanning for possible
     * enemies.
     * @param robot The user's robot.
     */
    TimidinFaseDefault(TimidinRobot robot) {
        this.robot = robot;
    }
    
    /**
     * <p>
     * It's an override implementation of the abstract class {@link State}.
     * <p>
     * It only turns itself around until it encounters an enemy. Only then, the
     * robot changes to the {@link Fase0} state.
     */
    @Override
    public void doAction() {
        System.out.println("Default fase");
        this.robot.setTurnRadarLeft(5);
        this.robot.execute();
    }
    
}
