package org.ecs160.a2;

import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NORGate extends Component {
    public NORGate () {}

    public NORGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
        image = r.getImage("nor.png");
    }

    // calculates state depending on the square to its left and the square directly above it
    @Override
    public void calculateOutput(formApp app) {
        List<Integer> inputs = getInputs(app);

        if (inputs.size() == 2){
            output = (inputs.get(0) | inputs.get(1)) ^ 1;
        }
        else output = -1;
    }

    @Override
    public void calculateDelay(formApp app) {
        List<Integer> inputs = getDelayInputs(app);
        if (inputs.size() == 2){
            Integer newDelay = delay + Math.max(inputs.get(0), inputs.get(1));
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
        return "NOR";
    }
}