package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XORGate implements StateChanger{

    private Resources r;
    private Integer gridCell;
    private Boolean output;
    private String name;
    private Image image;

    public XORGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
        image = r.getImage("xor.png");
    }

    public XORGate () {}

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

    // calculates state depending on the square to its left and the square directly above it
    @Override
    public void calculateOutput(formApp app) {
        ArrayList<Boolean> inputs = new ArrayList<Boolean>();
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled())
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getOutput());
        if (app.getWorkSpace().getGridCell(gridCell - 8).isFilled()
                && !app.getWorkSpace().getGridCell(gridCell - 8).getStateChanger().getName().equals("Horizontal"))
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 8).getOutput());
        if (inputs.size() == 2) output = inputs.get(0) ^ inputs.get(1);
        else output = false;
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
        return "XOR";
    }
}