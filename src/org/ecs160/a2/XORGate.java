package org.ecs160.a2;

import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

public class XORGate extends Component {
    public XORGate () {}

    public XORGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
        image = r.getImage("xor.png");
    }

    // calculates state depending on the square to its left and the square directly above it
    @Override
    public void calculateOutput(formApp app) {
        ArrayList<Integer> inputs = new ArrayList<Integer>();
        ArrayList<Integer> delays = new ArrayList<Integer>();
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {// left

            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getOutput());
            //    delays.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }
        if (app.getWorkSpace().getGridCell(gridCell - 8).isFilled() // top and top isn't a horizontal wire
                && !app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal")) {
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 8).getOutput());
            //     delays.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }

        if (inputs.size() == 2) output = inputs.get(0) ^ inputs.get(1);
        else output = -1;
      //  propDelay = Collections.max(delays);
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getObjectId() {
        return "XOR";
    }
}