package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;

import java.awt.*;

public class CustomizedButton extends Button {
    public CustomizedButton(String txt) {
        super(txt);
        this.getAllStyles().setFgColor(0xffffff);
        this.getAllStyles().setBgColor(0xffffff);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setMargin(3, 3, 3,3);
        //this.setSize(new Dimension(8,10));
    }
}