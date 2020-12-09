package org.ecs160.a2;

import com.codename1.io.Util;
import com.codename1.ui.Image;
import com.codename1.ui.util.Resources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class XNORGate implements StateChanger{

    private Resources r;
    private Integer gridCell;
    //private Boolean output;
    private Integer output;
    private String name;
    private Image image;
    private Integer propDelay;

    public XNORGate (Integer gridCell, String name) {
        try { r = Resources.open("/theme.res"); }
        catch (IOException e) { e.printStackTrace(); }
        this.gridCell = gridCell;
        this.name = name;
        image = r.getImage("xnor.png");
    }

    public XNORGate () {}

//    @Override
//    public Boolean getOutput() { return output; }

    @Override
    public Integer getOutput() { return output; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getImage() { return image; }

//    @Override
//    public Integer getDelay() { return propDelay; }

//    @Override
//    public void updateState(Boolean state) { /* pass */ }

    @Override
    public void updateState(Integer state) { /* pass */ }

    // calculates state depending on the square to its left and the square directly above it
    @Override
    public void calculateOutput(formApp app) {
//        ArrayList<Boolean> inputs = new ArrayList<Boolean>();
        ArrayList<Integer> inputs = new ArrayList<Integer>();
        ArrayList<Integer> delays = new ArrayList<Integer>();
        if (app.getWorkSpace().getGridCell(gridCell - 1).isFilled()) {// left

            inputs.add(app.getWorkSpace().getGridCell(gridCell - 1).getOutput());
            //    delays.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }
        if (app.getWorkSpace().getGridCell(gridCell - 8).isFilled() // top and top isn't a horizontal wire
                && !app.getWorkSpace().getGridCell(gridCell - 8).getStateChanger().getName().equals("Horizontal")) {
            inputs.add(app.getWorkSpace().getGridCell(gridCell - 8).getOutput());
            //     delays.add(app.getWorkSpace().getGridCell(gridCell - 1).getDelay());
        }

        if (inputs.size() == 2) output = (inputs.get(0) ^ inputs.get(1)) ^ 1;
        else output = -1;
      //  propDelay = Collections.max(delays);
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
        return "XNOR";
    }
}