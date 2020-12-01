package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;

import java.util.Hashtable;

public class menu extends Container {
    private String[] topRow =
            { "CLEAR", "TRASH"};

    // Numeric is for number input
    TextField propagation_delay = new TextField("", "Prop. Delay", 5, TextArea.NUMERIC);

    private String botRow = "Gates Appear Here";

    private Hashtable<String, CustomizedMenu> buttons = new Hashtable<String, CustomizedMenu>();
    public menu() {
        super();
        this.setLayout(new GridLayout(2, 1));
        this.getAllStyles().setBgColor(0xb36890);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setPadding(50, 50, 0, 0);

        Container top_row = new Container(new GridLayout(1, 2));
        CustomizedMenu bClr = new CustomizedMenu(topRow[0]);
        buttons.put(topRow[0], bClr);
        top_row.add(bClr);
        CustomizedMenu bDel = new CustomizedMenu(topRow[1]);
        buttons.put(topRow[0], bDel);
        top_row.add(bDel);

        Container bot_row = new Container(new GridLayout(1, 2));

        bot_row.add(propagation_delay);
        CustomizedMenu bSpc = new CustomizedMenu(botRow);
        buttons.put(botRow, bSpc);
        bot_row.add(bSpc);

        this.add(top_row);
        this.add(bot_row);
    }

    public void updateGateSelected(String s) {
        buttons.get("Gates Appear Here").setText(s);
    };
}
