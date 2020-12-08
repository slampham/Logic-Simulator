package org.ecs160.a2;

import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.IOException;

public class Peripheral implements StateChanger {

    private Resources r;
    private formApp app;
    private Integer gridCell;
    private Boolean output = false;
    private String name;
    private Image image;

    public Peripheral(formApp app, Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.app = app;
        this.gridCell = gridCell;
        this.name = name;
        calculateOutput();
        setImage(output);
    }

    @Override
    public Boolean getOutput() {
        return output;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getImage() {
        setImage(output);
        return image;
    }

    // this function is primarily used for the toggle since we need to change its state externally
    // TODO: consider separating toggle from LED/wires
    @Override
    public void updateState(Boolean state) {
        output = state;
        setImage(state);
    }

    // calculates output depending on grid cell positions
    @Override
    public void calculateOutput() {
        switch(name) {
            case "Toggle":
                break;
            case "LED":
            case "Horizontal":
            case "9:30":
                if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled())
                    output = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
                else
                    output = false;
                break;
            default:
                if (app.getWorkSpace().getGridCell(gridCell - 8).isFilled())
                    output = app.getWorkSpace().getGridCell(gridCell - 8).getOutput();
                else
                    output = false;
        }
    }

    // sets image based on state
    private void setImage(Boolean state) {
        switch(name) {
            case "Toggle":
                if (state) {
                    image = r.getImage("toggle_on.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0x01FF15);
                }
                else {
                    image = r.getImage("toggle_off.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0x00A650);
                }
                break;
            case "LED":
                if (state) {
                    image = r.getImage("green_led.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0x01FF15);
                }
                else {
                    image = r.getImage("red_led.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0xFC0204);
                }
                break;
            case "Vertical":
                image = r.getImage("vertical.png");
                break;
            case "Horizontal":
                image = r.getImage("horizontal.png");
                break;
            default:
                image = r.getImage("nine_thirty.png");
        }
    }
}
