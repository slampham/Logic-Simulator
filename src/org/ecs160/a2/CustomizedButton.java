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
//    private Boolean output = false;
    private Integer output = 0;
    private Resources r;
    //private Integer delay;

    public CustomizedButton(Integer txt) {
        super(Integer.toString(txt));
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.getAllStyles().setFgColor(0xffffff);

        this.getAllStyles().setBgColor(0xffffff);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setMargin(3, 3, 3,3);

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

    // updates state of the grid cell based on the stateChanger attached
    // used to refresh states when circuit is changed
    public void updateState(formApp app) {
        if (component != null) {
            component.calculateOutput(app);
            output = component.getOutput();
           // delay = stateChanger.getDelay();
            this.getAllStyles().setBgImage(component.getImage());
        } else {
            //output = false;
            output = -1;
        }
    }

    // used to invoke a visual cue when the user selects a particular grid cell
    public void highlightGridCell() {
        this.getAllStyles().setMargin(10, 10, 10,10);
    }

    // used to refresh the margins of user-highlighted grid cells
    public void unhighlightGridCell() {
        this.getAllStyles().setMargin(3, 3, 3,3);
    }

    //public Boolean getOutput() { return output; }
    public Integer getOutput() { return output; }

   // public Integer getDelay() { return delay; }

    public Component getStateChanger() { return component; }

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
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        component = (Component) Util.readObject(in);
        cellName = (Integer) Util.readObject(in);
        filled = (Boolean) Util.readObject(in);
        //output = (Boolean) Util.readObject(in);
        output = (Integer) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Grid cell";
    }
}