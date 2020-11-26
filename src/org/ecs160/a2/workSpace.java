package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;

import java.util.Hashtable;

public class workSpace extends Container {

            private String[] buttonNames = { " ", " ", " ", " ", " ", " ", " "," ",
                    " ", " ", " ", " ", " ", " ", " "," ",
                    " ", " ", " ", " ", " ", " ", " "," ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
                    " ", " ", " ", " ", " ", " ", " ", " ",
//                    " ", " ", " ", " ", " ", " ", " ", " ",
//                    " ", " ", " ", " ", " ", " ", " ", " ",
//                    " ", " ", " ", " ", " ", " ", " ", " ",
                    //" ", " ", " ", " ", " ", " ", " ", " ",
                    //" ", " ", " ", " ", " ", " ", " ", " ",
                    //" ", " ", " ", " ", " ", " ", " ", " ",
                    //" ", " ", " ", " ", " ", " ", " ", " ",
                    //" ", " ", " ", " ", " ", " ", " ", " ",
            };

            private Hashtable<String, CustomizedButton> buttons = new Hashtable<String, CustomizedButton>();

            public workSpace() {
                super();
                this.setLayout(new GridLayout(12, 8));
                this.getAllStyles().setBgColor(0x6890b3);
                this.getAllStyles().setBgTransparency(255);
                for (String buttonName : buttonNames) {
                    CustomizedButton button = new CustomizedButton(buttonName);
                    buttons.put(buttonName, button);
                    this.add(button);
                }
                //this.getAllStyles().setMargin(0, 100,0,0);
            }

}
