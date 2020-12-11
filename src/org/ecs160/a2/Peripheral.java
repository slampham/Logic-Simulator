package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Peripheral extends Component {

    private Resources r;
    private Integer gridCell;
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

    public Integer getOutput() { return output;  }

    @Override
    public String getName() { return name; }

    @Override
    public Image getImage() { return image; }

    @Override
    public void updateState(Integer state) { output = state; }

    @Override
    public void calculateOutput(formApp app) {
        /* Pass the input along from the previous component (gate / wire / led), check if it comes from the top or left,
        * and set the output. */

        switch(name) {
            case "Toggle":
                break;
            case "LED":
            case "Horizontal":
            case "9:30":
                if (app.getWorkSpace().getGridCell(gridCell - 1) != null &&
                        app.getWorkSpace().getGridCell(gridCell - 1).isFilled())
                    output = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
                else
                    output = -1;
                break;
            default: // vertical
                if (app.getWorkSpace().getGridCell(gridCell - 8) != null &&
                        app.getWorkSpace().getGridCell(gridCell - 8).isFilled()
                        && !(app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal")))
                    output = app.getWorkSpace().getGridCell(gridCell - 8).getOutput();
                else
                    output = -1;
        }
        setImage(app);
    }

    @Override
    public void calculateDelay(formApp app) {
        /* Pass the delay along from the previous component (gate), check if it comes from the top or left,
         * and set the delay. */

        switch(name) {
            case "Toggle":
            case "LED":
                break;
            case "Horizontal":
            case "9:30":
                if (app.getWorkSpace().getGridCell(gridCell - 1) != null &&
                        app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {
                    Integer newDelay = Math.max(app.getWorkSpace().getGridCell(gridCell - 1).getDelay(), delay);
                    app.getWorkSpace().getGridCell(gridCell).setDelay(newDelay);
                }
                break;
            default: // vertical
                if (app.getWorkSpace().getGridCell(gridCell - 8) != null &&
                        app.getWorkSpace().getGridCell(gridCell - 8).isFilled()
                        && !(app.getWorkSpace().getGridCell(gridCell - 8).getComponent().getName().equals("Horizontal"))) {
                    Integer newDelay = Math.max(app.getWorkSpace().getGridCell(gridCell - 8).getDelay(), delay);
                    app.getWorkSpace().getGridCell(gridCell).setDelay(newDelay);
                }
        }
    }

    /* README: Even though data persistence methods were defined in the super class Component, it is required here
       because Codename One requires child classes to have methods defined inside of it, not inherited. */
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
        output = (Integer) Util.readObject(in);
        name = (String) Util.readObject(in);
        image = (Image) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "Peripheral";
    }

    private void setImage(formApp app) {
        /* If output == 1, set a green image. If output == 0, set a blue image. If output == -1, set a red image. */

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
