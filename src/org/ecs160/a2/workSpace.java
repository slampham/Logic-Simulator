package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;

import java.util.Hashtable;

public class workSpace extends Container {

            private Integer[] buttonNames;
            private Hashtable<Integer, CustomizedButton> buttons = new Hashtable<>();

            static {
                Util.register("AND", ANDGate.class);
                Util.register("NAND", NANDGate.class);
                Util.register("NOR", NORGate.class);
                Util.register("NOT", NOTGate.class);
                Util.register("OR", ORGate.class);
                Util.register("XNOR", XNORGate.class);
                Util.register("XOR", XORGate.class);
                Util.register("Peripheral", Peripheral.class);
                Util.register("Grid cell", CustomizedButton.class);
            }

            public workSpace() {
                super();
                this.setLayout(new GridLayout(12, 8));
                this.getAllStyles().setBgColor(0x6890b3); // light blue bkgrnd for graph paper illusion
                this.getAllStyles().setBgTransparency(255);

                Hashtable<Integer, CustomizedButton> workspaceLoad = (Hashtable<Integer, CustomizedButton>) Storage.getInstance().readObject("workspace");

                buttonNames = new Integer[96];
                for (int i = 0; i < 96; i++) {buttonNames[i] = i;}

                if (workspaceLoad != null) {
                    for (Integer buttonName : buttonNames) {
                        buttons.put(buttonName, workspaceLoad.get(buttonName));
                        this.add(workspaceLoad.get(buttonName));
                    }
                } else {
                    for (Integer buttonName : buttonNames) {
                        CustomizedButton button = new CustomizedButton(buttonName);
                        buttons.put(buttonName, button);
                        this.add(button);
                    }
                }
            }

            public Hashtable<Integer, CustomizedButton> getWorkSpaceMap () { return buttons; }
            public CustomizedButton getGridCell (Integer s) {
                if (s >= 0 && s <= 95) return buttons.get(s);
                else return null;
            }
}
