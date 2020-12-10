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
    }

    // calculates state depending on the square to its left
    @Override
    public void calculateOutput(formApp app) {
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled() &&
                (app.getWorkSpace().getGridCell(gridCell - 1).getComponent().getName().equals("Horizontal") ||
                app.getWorkSpace().getGridCell(gridCell - 1).getComponent().getName().equals("Toggle"))) {
//            Boolean input = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
            Integer input = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
      //      propDelay = app.getWorkSpace().getGridCell(gridCell - 1).getDelay();
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