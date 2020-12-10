package org.ecs160.a2;

import java.util.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;

public class toolBar extends Container {
    private Resources theme;

    private String[] toolBarButtonNames =
            { "Toggle", "LED", "Wire", "AND Gate", "NAND Gate", "NOR Gate", "XNOR Gate",
                    "OR Gate", "NOT Gate", "XOR Gate", };

    private String[] wireBarButtonNames =
            { "Back", "Vertical", "Horizontal", "9:30"};

    private Hashtable<String, CustomizedNav> buttons;

    public toolBar() {
        super();
        initToolBarButtons();
    }

    public void initToolBarButtons() {
        refreshForm();
        buttons = new Hashtable<>();
        this.setLayout(new GridLayout(1, 10));
        this.getAllStyles().setBgColor(0x686bb3);
        this.getAllStyles().setBgTransparency(255);
        for (String buttonName : toolBarButtonNames) {
            CustomizedNav button = new CustomizedNav(buttonName);
            buttons.put(buttonName, button);
            this.add(button);
        }
        this.getAllStyles().setPadding(100,100,0,0);
    }

    public void initWireBarButtons() {
        refreshForm();
        buttons = new Hashtable<String, CustomizedNav>();
        this.setLayout(new GridLayout(1, 4));
        for (String buttonName : wireBarButtonNames) {
            CustomizedNav button = new CustomizedNav(buttonName);
            buttons.put(buttonName, button);
            this.add(button);
        }
        this.getAllStyles().setPadding(100,100,0,0);
    }

    public Hashtable<String, CustomizedNav> getToolBarMap() { return buttons; }
    public CustomizedNav getButton(String s) { return buttons.get(s); }

    private void refreshForm() {
        if (buttons != null) {
            for (String key : buttons.keySet()) {
                this.removeComponent(buttons.get(key));
            }
        }
    }
}