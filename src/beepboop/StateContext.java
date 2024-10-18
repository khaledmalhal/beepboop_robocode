/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;

/**
 *
 * @author kmalhal
 */
public class StateContext implements State
{    
    private State state;
    
    public void setState(State state) {
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
