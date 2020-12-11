package org.ecs160.a2;

public class MobiLogicContext {
    /**
     * MobiLogicContext is the context class that controls state transitions.
     * An object of this class is passed down through all state transitions to keep track of
     * state as well.
     */
    private MobiLogicState state;

    public MobiLogicContext() { }

    public void setState(MobiLogicState state){
        this.state = state;
    }
    public MobiLogicState getState(){
        return state;
    }
}
