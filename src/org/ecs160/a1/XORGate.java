package org.ecs160.a1;

import java.util.ArrayList;

public class XORGate extends Gate{
    public XORGate (ArrayList<Boolean> input, Boolean output) {
        super(input, input.get(0) ^ input.get(1));
    }
}