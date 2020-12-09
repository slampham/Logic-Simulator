package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Peripheral implements StateChanger {

    private Resources r;
    private Integer gridCell;
  //  private Boolean output = false;
    private String name;
    private Image image;
    private Integer output = 0; //red by default; green = 1; blue = 0

    public Peripheral(Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
    }

    public Peripheral () {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
    }

//    @Override
//    public Boolean getOutput() {
//        return output;
//    }
    public Integer getOutput() {
        return output;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getImage() {
        return image;
    }

    // this function is primarily used for the toggle since we need to change its state externally
//    @Override
//    public void updateState(Boolean state) {
//        output = state;
//    }
    @Override
    public void updateState(Integer state) {
        output = state;
    }

    // calculates output depending on grid cell positions
    @Override
    public void calculateOutput(formApp app) {
        // switch statement to calculate output
        switch(name) {
            case "Toggle":
                break;
            case "LED":
            case "Horizontal":
            case "9:30":
                if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled())
                    output = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
                else
                    //output = false;
                    output = -1;
                break;
            default: // vertical
                if (app.getWorkSpace().getGridCell(gridCell - 8).isFilled()
                        && !(app.getWorkSpace().getGridCell(gridCell - 8).getStateChanger().getName().equals("Horizontal")))
                    output = app.getWorkSpace().getGridCell(gridCell - 8).getOutput();
                else
                    //output = false;
                    output = -1;
        }
        setImage(app);
    }

    @Override
    public int getVersion() {
        return 0;
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
        //output = (Boolean) Util.readObject(in);
        output = (Integer) Util.readObject(in);
        name = (String) Util.readObject(in);
        image = (Image) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Peripheral";
    }

    private void setImage(formApp app) {
        switch(name) {
            case "Toggle":
                if (output == 1) {
                    image = r.getImage("toggle_on.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0x01FF15);
                }
                else {
                    image = r.getImage("toggle_off.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0x00A650);
                }
                break;
            case "LED":
                if (output == 1) {
                    image = r.getImage("green_led.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0x01FF15);
                }
                else {
                    image = r.getImage("red_led.png");
                    app.getWorkSpace().getGridCell(gridCell).getAllStyles().setFgColor(0xFC0204);
                }
                break;
            case "Vertical":

                if (output == 1) {
                    image = r.getImage("green_vertical.png");
                }
                else if (output == 0){
                    image = r.getImage("blue_vertical.png");
                }
                else {
                    image = r.getImage("red_vertical.png");
                }
                break;
            case "Horizontal":

                if (output == 1) {
                    image = r.getImage("green_horizontal.png");
                }
                else if (output == 0){
                    image = r.getImage("blue_horizontal.png");
                }
                else {
                    image = r.getImage("red_horizontal.png");
                }
                break;
            default:

                if (output == 1) {
                    image = r.getImage("green_nine_thirty.png");
                }
                else if (output == 0){
                    image = r.getImage("blue_nine_thirty.png");
                }
                else {
                    image = r.getImage("red_nine_thirty.png");
                }
                break;
        }
    }
}
