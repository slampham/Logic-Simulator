package org.ecs160.a2;

public interface MobiLogicState {
    /**
     * MobiLogicState is an interface for all future state objects. It's sole function `computeAction`
     * is responsible for computing the action for the particular state object it is attached to. That is,
     * this function is responsible for attaching/detaching action listeners, state transitions, etc.
     * @param context
     */
    void computeAction (MobiLogicContext context);
}
