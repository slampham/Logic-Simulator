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
     *  Wires/LEDs/toggles fall under the "Peripheral" object umbrella. */

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

    public Component() {}  // Null constructor required for data persistence
    public Integer getOutput() { return output; }
    public String getName() { return name; }
    public Image getImage() { return image; }
    public Integer getDelay() { return delay; }
    public void setDelay(Integer delay) { this.delay = delay; }
    public void updateState(Integer state) {}

    public List<Integer> getInputs(formApp app) {
        /* Retrieve inputs from the top & left grid cells of the component. */

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
        /* Get delays from top & left grid cells of the component. */

        List<Integer> delays = new ArrayList<>();
        if (app.getWorkSpace().getGridCell(gridCell - 1) != null &&
                app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {// left
            delays.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }
        if (app.getWorkSpace().getGridCell(gridCell - 8) != null &&
                app.getWorkSpace().getGridCell(gridCell - 8).isFilled() // top and top isn't a horizontal wire
                && !app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal")) {
            delays.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }
        return delays;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        /* Autosave component during program termination so data persists */

        Util.writeObject(gridCell, out);
        Util.writeObject(output, out);
        Util.writeObject(name, out);
        Util.writeObject(image, out);
        Util.writeObject(delay, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        /* Retrieve most recent circuit upon program startup */

        gridCell = (Integer) Util.readObject(in);
        output = (Integer) Util.readObject(in);
        name = (String) Util.readObject(in);
        image = (Image) Util.readObject(in);
        delay = (Integer) Util.readObject(in);
    }
}