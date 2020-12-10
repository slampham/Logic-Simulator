package org.ecs160.a2;

import com.codename1.ui.util.Resources;
import java.io.IOException;

public class NOTGate extends Component {
    public NOTGate () {}

    public NOTGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
        image = r.getImage("not.png");
        delay = 0;
    }

    // calculates state depending on the square to its left
    @Override
    public void calculateOutput(formApp app) {
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled() &&
                (app.getWorkSpace().getGridCell(gridCell - 1).getComponent().getName().equals("Horizontal") ||
                app.getWorkSpace().getGridCell(gridCell - 1).getComponent().getName().equals("Toggle"))) {
            Integer input = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
            output = input ^ 1;
        } else
            output = -1;

    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "NOT";
    }
}