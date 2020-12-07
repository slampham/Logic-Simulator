package org.ecs160.a2;

import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;

import java.io.IOException;

public class CustomizedButton extends Button {
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

    // updates state of the grid cell based on the stateChanger attached
    // used to refresh states when circuit is changed
    public void updateState() {
        if (stateChanger != null) {
            stateChanger.calculateOutput();
            output = stateChanger.getOutput();
            this.getAllStyles().setBgImage(stateChanger.getImage());
        } else {
            output = false;
        }
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
        }
        filled = false;
    }

    private Image chooseComponent (formApp app, String s) {
        Image component;
        switch(s) {
            case "AND Gate":
                stateChanger = new ANDGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "NAND Gate":
                stateChanger = new NANDGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "NOR Gate":
                stateChanger = new NORGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "NOT Gate":
                stateChanger = new NOTGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "OR Gate":
                stateChanger = new ORGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "XNOR Gate":
                stateChanger = new XNORGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "XOR Gate":
                stateChanger = new XORGate(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            case "Toggle":
            case "LED":
            case "Vertical":
            case "Horizontal":
            case "9:30":
                stateChanger = new Peripheral(app, cellName, s);
                component = stateChanger.getImage();
                output = stateChanger.getOutput();
                break;
            default:
                component = r.getImage("white_square.PNG");
        }
        return component;
    }
}