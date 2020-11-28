package org.ecs160.a2;

import com.codename1.ui.CheckBox;

import java.util.List;

public class Toggle extends CheckBox implements Component {
    public void toggle() {
        setSelected(!isSelected());
    }

    public Boolean output() {
        return isSelected();
    }

    /*** FIXME: bad coding practice, but empty coding method because I wanted to loop through all components,
     * and only setInputs for LED and Gates. So I left this empty. However, if anyone know how to resolve this issue
     * without cluttering the workspace of Toggle, let me know! If we wanted to make sure classes dont have extra functions,
     * we would need to make three separate lists for LED, toggle, Gate! :L ***/
    @Override
    public void setInputs(List<Boolean> inputs) { }
}
