package org.ecs160.a2;

import com.codename1.ui.CheckBox;

import java.util.List;

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

    @Override
    public void setInputs(List<Boolean> inputs) {
        setInput(inputs.get(0));
    }
}
