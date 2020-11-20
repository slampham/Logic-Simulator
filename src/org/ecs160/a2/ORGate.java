package org.ecs160.a2;
import java.util.ArrayList;

public class ORGate extends Gate{
    public ORGate (ArrayList<Boolean> input) {
        super(input, input.get(0) || input.get(1));
    }
}