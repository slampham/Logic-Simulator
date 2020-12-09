package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NOTGate implements StateChanger{

    private Resources r;
    private Integer gridCell;
    private Boolean output;
    private String name;
    private Image image;

    public NOTGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
        image = r.getImage("not.png");
    }

    public NOTGate () {}

    @Override
    public Boolean getOutput() {
        return output;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getImage() { return image; }

    @Override
    public void updateState(Boolean state) { /* pass */ }

    // calculates state depending on the square to its left
    @Override
    public void calculateOutput(formApp app) {
        if (app.getWorkSpace().getGridCell(gridCell - 1) != null &&
                app.getWorkSpace().getGridCell(gridCell - 1).isFilled() &&
                (app.getWorkSpace().getGridCell(gridCell - 1).getStateChanger().getName().equals("Horizontal") ||
                app.getWorkSpace().getGridCell(gridCell - 1).getStateChanger().getName().equals("Toggle"))) {
            Boolean input = app.getWorkSpace().getGridCell(gridCell - 1).getOutput();
            output = !input;
        } else
            output = false;
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
        output = (Boolean) Util.readObject(in);
        name = (String) Util.readObject(in);
        image = (Image) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "NOT";
    }
}