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
import java.util.concurrent.TimeUnit;

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
    protected Integer delay = 0;

    public abstract void calculateOutput(formApp app);
    public abstract void calculateDelay(formApp app);
    public abstract int getVersion();
    public abstract String getObjectId();

    public Component() {}
    public Integer getOutput() { return output; }
    public String getName() {
        return name;
    }
    public Image getImage() { return image; }
    public Integer getDelay() { return delay; }
    public void setDelay(Integer delay) { this.delay = delay; }

    public void updateState(Integer state) {
        // FIXME: perhaps bad code practice. updateState() only used for Peripherals
    }

    public List<Integer> getInputs(formApp app) {
        List<Integer> inputs = new ArrayList<>();
        if (app.getWorkSpace().getGridCell(gridCell - 1) != null &&
                app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {// left
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getOutput());
        }
        if (app.getWorkSpace().getGridCell(gridCell - 8) != null &&
                app.getWorkSpace().getGridCell(gridCell - 8).isFilled() // top and top isn't a horizontal wire
                && !app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal")) {
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 8).getOutput());
        }
        return inputs;
    }

    public List<Integer> getDelayInputs(formApp app) {
        List<Integer> inputs = new ArrayList<>();
        if (app.getWorkSpace().getGridCell(gridCell - 1) != null &&
                app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {// left
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }
        if (app.getWorkSpace().getGridCell(gridCell - 8) != null &&
                app.getWorkSpace().getGridCell(gridCell - 8).isFilled() // top and top isn't a horizontal wire
                && !app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal")) {
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }
        return inputs;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeObject(gridCell, out);
        Util.writeObject(output, out);
        Util.writeObject(name, out);
        Util.writeObject(image, out);
        Util.writeObject(delay, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        gridCell = (Integer) Util.readObject(in);
        output = (Integer) Util.readObject(in);
        name = (String) Util.readObject(in);
        image = (Image) Util.readObject(in);
        delay = (Integer) Util.readObject(in);
    }
}