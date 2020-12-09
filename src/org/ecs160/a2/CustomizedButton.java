package org.ecs160.a2;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;

import java.io.*;

public class CustomizedButton extends Button implements Externalizable {
    private StateChanger stateChanger;
    private Integer cellName;
    private Boolean filled = false;
    private Boolean output = false;
    private Resources r;

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
        if (stateChanger != null) {
            stateChanger.calculateOutput(app);
            output = stateChanger.getOutput();
            this.getAllStyles().setBgImage(stateChanger.getImage());
        } else {
            output = false;
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

    public Boolean getOutput() { return output; }

    public StateChanger getStateChanger() { return stateChanger; }

    public Integer getCell() { return cellName; }

    public Boolean isFilled() { return filled; }

    public void addComponent(formApp app, String s) {
        if (!filled)
            this.getAllStyles().setBgImage(chooseComponent(app, s));
        filled = true;
    }

    public void removeComponent() {
        if (filled) {
            stateChanger = null;
            this.getAllStyles().setBgImage(null);
            this.getAllStyles().setFgColor(0xffffff);
        }
        filled = false;
    }

    private Image chooseComponent (formApp app, String s) {
        Image component;
        switch(s) {
            case "AND Gate":
                stateChanger = new ANDGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "NAND Gate":
                stateChanger = new NANDGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "NOR Gate":
                stateChanger = new NORGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "NOT Gate":
                stateChanger = new NOTGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "OR Gate":
                stateChanger = new ORGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "XNOR Gate":
                stateChanger = new XNORGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "XOR Gate":
                stateChanger = new XORGate(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "Toggle":
            case "LED":
            case "Vertical":
            case "Horizontal":
            case "9:30":
                stateChanger = new Peripheral(cellName, s);
                stateChanger.calculateOutput(app);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
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
        Util.writeObject(stateChanger, out);
        Util.writeObject(cellName, out);
        Util.writeObject(filled, out);
        Util.writeObject(output, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        stateChanger = (StateChanger) Util.readObject(in);
        cellName = (Integer) Util.readObject(in);
        filled = (Boolean) Util.readObject(in);
        output = (Boolean) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Grid cell";
    }
}