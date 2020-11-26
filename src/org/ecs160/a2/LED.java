package org.ecs160.a2;

import com.codename1.ui.CheckBox;

public class LED extends CheckBox implements Component {
    public void set(Boolean input) {
        setSelected(input);
    }

    public void toggle() {
        setSelected(!isSelected());
    }

    public Boolean output() {
        return isSelected();
    }
}
