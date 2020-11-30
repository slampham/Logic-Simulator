package org.ecs160.a2;

import com.codename1.ui.CheckBox;

public class LED extends CheckBox implements Component {
    public void setInput(Boolean input) {
        setSelected(input);
    }

    public Boolean output() {
        return isSelected();
    }

    public void toggle() {
        setSelected(!isSelected());
    }

}
