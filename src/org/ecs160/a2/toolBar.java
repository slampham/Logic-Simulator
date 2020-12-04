package org.ecs160.a2;

import java.util.*;
import static com.codename1.ui.CN.*;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.plaf.UIManager;

import java.io.IOException;

public class toolBar extends Container {
    private Resources theme;

    private String[] buttonNames =
            { "Toggle", "LED", "Wire", "AND Gate", "NAND Gate", "NOR Gate", "XNOR Gate",
                    "OR Gate", "NOT Gate", "XOR Gate", };

    private Hashtable<String, CustomizedNav> buttons = new Hashtable<String, CustomizedNav>();

    public toolBar() {
        super();
        this.setLayout(new GridLayout(1, 10));
        this.getAllStyles().setBgColor(0x686bb3);
        this.getAllStyles().setBgTransparency(255);
        for (String buttonName : buttonNames) {
            CustomizedNav button = new CustomizedNav(buttonName);
            buttons.put(buttonName, button);
            this.add(button);
        }
        this.getAllStyles().setPadding(100,100,0,0);
    }

    public Hashtable<String, CustomizedNav> getToolBarMap() { return buttons; }
    public CustomizedNav getButton(String s) { return buttons.get(s); }
}