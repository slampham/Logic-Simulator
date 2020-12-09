package org.ecs160.a2;
import com.codename1.ui.util.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ANDGate extends Component {
    public ANDGate () {}

    public ANDGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.name = name;
        this.gridCell = gridCell;
        image = r.getImage("and.png");
    }

    @Override
    public void calculateOutput(formApp app) {
        List<Integer> inputs = getInputs(app);
        List<Integer> delays = new ArrayList<>();

        if (inputs.size() == 2){
            output = inputs.get(0) & inputs.get(1);
        }
        else {
            output = -1;
        }

       // propDelay = Collections.max(delays);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public String getObjectId() {
        return "AND";
    }
}