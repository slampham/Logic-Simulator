package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Stroke;
import com.codename1.ui.plaf.RoundRectBorder;

public class CustomizedMenu extends Button {
    /* customizing the "CLEAR", "TRASH", and "Gates appear here" components */

    public CustomizedMenu(String txt) {
        super(txt);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);

        if (txt.equals("Gates Appear Here")) {
            this.getAllStyles().setFgColor(0x000000);
            this.getAllStyles().setBgColor(0xffffff);
        } else {
            this.getAllStyles().setFgColor(0xffffff);
            this.getAllStyles().setBgColor(0x000000);
        }

        // rounded edges for the buttons
        this.getUnselectedStyle().setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));


        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setMargin(10, 10, 7,7);
    }
}