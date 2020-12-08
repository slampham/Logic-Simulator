package org.ecs160.a2;

import com.codename1.ui.Container;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;

import java.util.Hashtable;

public class workSpace extends Container {

            private Integer[] buttonNames;
            private Hashtable<Integer, CustomizedButton> buttons = new Hashtable<Integer, CustomizedButton>();
            private Resources r;

            public workSpace() {
                super();
                this.setLayout(new GridLayout(12, 8));
                this.getAllStyles().setBgColor(0x6890b3); // light blue bkgrnd for graph paper illusion
                this.getAllStyles().setBgTransparency(255);

                // TODO: Frontend, changing how it's currently implemented to white squares
                buttonNames = new Integer[96];
                for (int i = 0; i < 96; i++) {buttonNames[i] = i;}

                for (Integer buttonName : buttonNames) {
                    CustomizedButton button = new CustomizedButton(buttonName);
                    buttons.put(buttonName, button);
                    this.add(button);
                }
            }

            public Hashtable<Integer, CustomizedButton> getWorkSpaceMap () { return buttons; }
            public CustomizedButton getGridCell (Integer s) {
                if (s >= 0 && s <= 95)
                    return buttons.get(s);
                else
                    return null;
            }
}
