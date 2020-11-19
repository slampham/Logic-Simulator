package org.ecs160.a1;
import java.util.ArrayList;

public class ANDGate extends Gate{
    public ANDGate (ArrayList<Boolean> input) {
        super(input, input.get(0) && input.get(1));
    }
}