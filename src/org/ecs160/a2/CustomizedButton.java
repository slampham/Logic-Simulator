package org.ecs160.a2;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;

import java.io.*;

public class CustomizedButton extends Button implements Externalizable {
    private Component component;
    private Integer cellName;
    private Boolean filled = false;
    private Integer output = 0;
    private Resources r;
    private Integer delay = 0;

    public CustomizedButton(Integer txt) {
        /* class created for the graph paper display */

        super(Integer.toString(txt));
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.getAllStyles().setFgColor(0xffffff);

        this.getAllStyles().setBgColor(0xffffff);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setMargin(3, 3, 3,3);

        // white squares and default button spacing
        cellName = txt;
    }

    public CustomizedButton() {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.getAllStyles().setFgColor(0xffffff);
        this.getAllStyles().setBgColor(0xffffff);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setMargin(3, 3, 3,3);
    }

    public void updateState(formApp app) {
        /* updates state of the grid cell based on the component attached
         * used to refresh states when circuit is changed */

        if (component != null) {
            component.calculateOutput(app);
            output = component.getOutput();
            this.getAllStyles().setBgImage(component.getImage());
        } else {
            output = -1;
        }
    }

    public void updateDelay(formApp app) {
        /* updates grid cell's delay based on position
         * does not affect component's delay */

        if (component != null) {
            component.calculateDelay(app);
        }
    }

    public void highlightGridCell() {
        /* used to invoke a visual cue when the user selects a particular grid cell */

        this.getAllStyles().setMargin(10, 10, 10,10);
    }


    public void unhighlightGridCell() {
        /* used to refresh the margins of user-highlighted grid cells */

        this.getAllStyles().setMargin(3, 3, 3,3);
    }

    public Integer getOutput() { return output; }

    public void setDelay(Integer delay) { this.delay = delay; }

    public Integer getDelay() { return delay; }

    public Component getComponent() { return component; }

    public Integer getCell() { return cellName; }

    public Boolean isFilled() { return filled; }

    public void addComponent(formApp app, String s) {
        if (!filled)
            this.getAllStyles().setBgImage(chooseComponent(app, s));
        filled = true;
    }

    public void removeComponent() {
        if (filled) {
            component = null;
            this.getAllStyles().setBgImage(null);
            this.getAllStyles().setFgColor(0xffffff);
        }
        filled = false;
    }

    private Image chooseComponent (formApp app, String s) {
        /* assigning image to each component on the screen */

        Image component;
        switch(s) {
            case "AND Gate":
                this.component = new ANDGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "NAND Gate":
                this.component = new NANDGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "NOR Gate":
                this.component = new NORGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "NOT Gate":
                this.component = new NOTGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "OR Gate":
                this.component = new ORGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "XNOR Gate":
                this.component = new XNORGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "XOR Gate":
                this.component = new XORGate(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            case "Toggle":
            case "LED":
            case "Vertical":
            case "Horizontal":
            case "9:30":
                this.component = new Peripheral(cellName, s);
                this.component.calculateOutput(app);
                component = this.component.getImage();
                output = this.component.getOutput();
                break;
            default:
                component = r.getImage("white_square.PNG");
        }
        return component;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeObject(component, out);
        Util.writeObject(cellName, out);
        Util.writeObject(filled, out);
        Util.writeObject(output, out);
        Util.writeObject(delay, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        component = (Component) Util.readObject(in);
        cellName = (Integer) Util.readObject(in);
        filled = (Boolean) Util.readObject(in);
        output = (Integer) Util.readObject(in);
        delay = (Integer) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Grid cell";
    }
}