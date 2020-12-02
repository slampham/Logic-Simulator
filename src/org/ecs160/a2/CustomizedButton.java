package org.ecs160.a2;

import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;

import java.awt.*;
import java.io.IOException;

public class CustomizedButton extends Button {
    private Boolean filled;
    private Resources r;

    public CustomizedButton(String txt) {
        super(txt);
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.getAllStyles().setFgColor(0xffffff);
        this.getAllStyles().setBgColor(0xffffff);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setMargin(3, 3, 3,3);
        filled = false;
        //this.setSize(new Dimension(8,10));
    }

    public void addComponent(String s) {
        if (!filled) this.getAllStyles().setBgImage(chooseComponent(s));
        filled = true;
    }

    public void removeComponent() {
        if (filled) { this.getAllStyles().setBgImage(null); }
        filled = false;
    }

    private Image chooseComponent (String s) {
        Image component;
        switch(s) {
            case "AND Gate":
                component = r.getImage("and.png");
                break;
            case "NAND Gate":
                component = r.getImage("nand.png");
                break;
            case "NOR Gate":
                component = r.getImage("nor.png");
                break;
            case "NOT Gate":
                component = r.getImage("not.png");
                break;
            case "OR Gate":
                component = r.getImage("or.png");
                break;
            case "XNOR Gate":
                component = r.getImage("xnor.png");
                break;
            case "XOR Gate":
                component = r.getImage("xor.png");
                break;
            case "Toggle":
                component = r.getImage("toggle.png");
                break;
            case "LED":
                component = r.getImage("led.jpeg");
                break;
            default:
                component = r.getImage("empty.png");
        }
        return component;
    }
}