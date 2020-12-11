package org.ecs160.a2;

import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.List;

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
            Integer input = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
            output = input ^ 1;
        } else
            output = -1;
    }

    @Override
    public void calculateDelay(formApp app) {
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled() &&
                (app.getWorkSpace().getGridCell(gridCell - 1).getComponent().getName().equals("Horizontal") ||
                        app.getWorkSpace().getGridCell(gridCell - 1).getComponent().getName().equals("Toggle"))) {
            Integer newDelay = delay + app.getWorkSpace().getGridCell(gridCell - 1).getDelay();
            app.getWorkSpace().getGridCell(gridCell).setDelay(newDelay);
        }
        else app.getWorkSpace().getGridCell(gridCell).setDelay(delay);
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