package org.ecs160.a1;
import java.util.ArrayList;

public class ORGate extends Gate{
    public ORGate (ArrayList<Boolean> input, Boolean output) {
        super(input, output);
    }

    public void setOutput () {output = input.get(0) || input.get(1);}
}
