/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;

/**
 * Context class that acts as a wrapper for the State's implementation.
 * @author kmalhal
 */
public class StateContext implements State
{    
    private State state;
    private TimidinRobot robot;

    public StateContext(TimidinRobot robot) {
        this.robot = robot;
    }
    
    public void setState(State state) {
        System.out.println("Changed state!");
        this.state = state;
    }
    
    public State getState() {
        return this.state;
    }
    
    @Override
    public void doAction() {
        this.state.doAction();
    }
    
}
