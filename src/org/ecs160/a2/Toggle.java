package org.ecs160.a2;

import com.codename1.ui.CheckBox;

public class Toggle extends CheckBox implements Component {
    public void toggle() {
        setSelected(!isSelected());
    }

    public Boolean output() {
        return isSelected();
    }
}
