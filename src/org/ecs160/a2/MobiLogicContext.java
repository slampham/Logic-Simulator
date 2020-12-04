package org.ecs160.a2;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class MobiLogicContext {
    private MobiLogicState state;

    public MobiLogicContext() { }

    public void setState(MobiLogicState state){
        this.state = state;
    }
    public MobiLogicState getState(){
        return state;
    }
}
