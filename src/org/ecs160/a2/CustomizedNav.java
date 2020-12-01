package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Font;
import com.codename1.ui.Stroke;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;

import java.util.ArrayList;
import java.util.Arrays;

import static com.codename1.ui.CN.*;

public class CustomizedNav extends Button {
    private String buttonName;

    public CustomizedNav(String txt) {
        super(txt);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        this.getAllStyles().setFgColor(0xffffff);

        this.getUnselectedStyle().setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));

        this.getAllStyles().setBgColor(0x000000);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setPadding(2,2,2,2);
        this.getAllStyles().setMargin(10, 10, 7,7);

        buttonName = txt;
    }

    public String getName() { return buttonName; }
}