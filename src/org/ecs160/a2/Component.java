package org.ecs160.a2;
import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Component implements Externalizable {
    /* This interface encapsulates gates, wires, LEDs/toggles.
     * Gates are implemented separately. Wires/LEDs/toggles fall under the "Peripheral" object umbrella.
     * I implemented such an interface because we need a way to attach these objects to a grid cell
     * such that they fall under a single umbrella; thus, "component". */

    protected Resources r;
    protected Integer gridCell;
    protected Integer output;
    protected String name;
    protected Image image;

    public abstract void calculateOutput(formApp app);
    public abstract int getVersion();
    public abstract String getObjectId();

    public Component() {}
    public Integer getOutput() { return output; }
    public String getName() {
        return name;
    }
    public Image getImage() { return image; }

    public void updateState(Integer state) {
        // FIXME: why is this pass??? Should we just remove this?
    }

    public List<Integer> getInputs(formApp app) {
        List<Integer> inputs = new ArrayList<>();
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {// left
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getOutput());
        }
        if (app.getWorkSpace().getGridCell(gridCell - 8).isFilled() // top and top isn't a horizontal wire
                && !app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal")) {
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 8).getOutput());
        }
        return inputs;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeObject(gridCell, out);
        Util.writeObject(output, out);
        Util.writeObject(name, out);
        Util.writeObject(image, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        gridCell = (Integer) Util.readObject(in);
        output = (Integer) Util.readObject(in);
        name = (String) Util.readObject(in);
        image = (Image) Util.readObject(in);
    }
}