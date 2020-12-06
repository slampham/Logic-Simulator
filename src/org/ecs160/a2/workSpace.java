package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.util.Hashtable;

public class workSpace extends Container {

            private String[] buttonNames;
            private Hashtable<String, CustomizedButton> buttons = new Hashtable<String, CustomizedButton>();
            private Resources r;

            public workSpace() {
                super();
                this.setLayout(new GridLayout(12, 8));
                this.getAllStyles().setBgColor(0x6890b3); // light blue bkgrnd for graph paper illusion
                this.getAllStyles().setBgTransparency(255);

                // TODO: Frontend, changing how it's currently implemented to white squares
                buttonNames = new String[96];
                for (int i = 0; i < 96; i++) {buttonNames[i] = Integer.toString(i);}

                for (String buttonName : buttonNames) {
                    // kody's idea: no hashtable. will have to change a lot of code tho
                    CustomizedButton button = new CustomizedButton(buttonName);
                   // Button button = new Button();
                    //button.getAllStyles().setBgImage(r.getImage("white_square.PNG"));
                    buttons.put(buttonName, button);
                    this.add(button);
                }
            }

            public Hashtable<String, CustomizedButton> getWorkSpaceMap () { return buttons; }
            public CustomizedButton getGridCell (String s) {return buttons.get(s); }
}
