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
    
    /**
     * Change the state to the "Fase" that the robot is on.
     * <p>
     * Ideally, you should set it to {@link TimidinFaseDefault} then {@link TimidinFase0}, then
     * {@link TimidinFase1} and then {@link TimidinFase2}.
     * @param state The desired State to change.
     */
    public void setState(State state) {
        if (this.state != state) {
            System.out.println("Changed state!");
            this.state = state;
        }
    }
    
    /**
     * Returns the state that the robot is currently on.
     * @return Returns a state.
     */
    public State getState() {
        return this.state;
    }
    
    /**
     * This only calls the action that every state has implemented.
     * Each implementation are {@link TimidinFaseDefault#doAction()}, 
     * {@link TimidinFase0#doAction()}, {@link TimidinFase1#doAction()} and 
     * {@link TimidinFase2#doAction()}
     */
    @Override
    public void doAction() {
        this.state.doAction();
    }
    
}
