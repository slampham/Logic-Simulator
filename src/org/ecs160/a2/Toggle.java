package org.ecs160.a2;

import com.codename1.ui.Button;

public class Toggle extends Button {
    public void toggle() {
        if (getState() == STATE_PRESSED) {
            released();
        }
        else {
            pressed();
        }
    }

    public Boolean output() {
        return getState() == STATE_PRESSED;
    }
}
