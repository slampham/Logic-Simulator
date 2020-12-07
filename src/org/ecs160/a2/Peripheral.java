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
                if (app.getWorkSpace().getGridCell(gridCell - 1).getStateChanger() != null)
                    output = app.getWorkSpace().getGridCell(gridCell - 1).getStateChanger().getOutput();
                break;
            default:
                if (app.getWorkSpace().getGridCell(gridCell - 8).getStateChanger() != null)
                    output = app.getWorkSpace().getGridCell(gridCell - 8).getStateChanger().getOutput();
        }
    }

    // sets image based on state
    private void setImage(Boolean state) {
        switch(name) {
            case "Toggle":
                if (state) image = r.getImage("toggle_on.PNG");
                else image = r.getImage("toggle_off.PNG");
                break;
            case "LED":
                if (state) image = r.getImage("green_led.jpg");
                else image = r.getImage("red_led.jpg");
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
