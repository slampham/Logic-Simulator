package org.ecs160.a1;

import java.util.ArrayList;

public class XNORGate extends Gate{
    public XNORGate (ArrayList<Boolean> input, Boolean output) {
        super(input, input.get(0) == input.get(1));
    }
}