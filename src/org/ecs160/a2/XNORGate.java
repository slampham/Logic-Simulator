package org.ecs160.a2;

import java.util.ArrayList;

public class XNORGate extends Gate{
    public XNORGate (ArrayList<Boolean> input) {
        super(input, input.get(0) == input.get(1));
    }
}