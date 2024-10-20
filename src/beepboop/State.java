/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package beepboop;

/**
 * Interface of States for the Timidin robot.
 * @author kmalhal
 */
public interface State {
    /**
     * The interface method of some action that every state will execute.
     * @see StateContext
     * @see TimidinFaseDefault
     * @see TimidinFase0
     * @see TimidinFase1
     * @see TimidinFase2
     */
    public void doAction();
}
