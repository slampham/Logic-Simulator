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
    private String[] topRow = { "CLEAR", "TRASH"};

    // Numeric is for number input
    private TextField propagationDelay = new TextField("", "Prop. Delay", 5, TextArea.NUMERIC);

    private String botRow = "Selected Tool";

    private Hashtable<String, CustomizedMenu> buttons = new Hashtable<>();
    public menu() {
        super();
        this.setLayout(new GridLayout(2, 1));
        this.getAllStyles().setBgColor(0xb36890);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setPadding(50, 50, 0, 0);

        //TODO: frontend adding comments :p
        Container top_row = new Container(new GridLayout(1, 2));
        CustomizedMenu bClr = new CustomizedMenu(topRow[0]);
        buttons.put(topRow[0], bClr);
        top_row.add(bClr);
        CustomizedMenu bDel = new CustomizedMenu(topRow[1]);
        buttons.put(topRow[1], bDel);
        top_row.add(bDel);

        Container bot_row = new Container(new GridLayout(1, 2));

        bot_row.add(propagationDelay);
        CustomizedMenu bSpc = new CustomizedMenu(botRow);
        buttons.put(botRow, bSpc);
        bot_row.add(bSpc);

        this.add(top_row);
        this.add(bot_row);
    }

    public void updateGateSelected(String s) { buttons.get("Selected Tool").setText(s); }
    public CustomizedMenu getButton(String s) { return buttons.get(s); }
    public String getPropagationDelay() { return propagationDelay.getText(); }
    public void setPropagationDelay(String s) { propagationDelay.setText(s);}
    public TextField getTextField()  { return propagationDelay; }

}
