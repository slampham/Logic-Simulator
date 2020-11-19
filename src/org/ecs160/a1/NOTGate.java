package org.ecs160.a1;

import java.util.ArrayList;

public class NOTGate extends Gate{
    public NOTGate (ArrayList<Boolean> input, Boolean output) {
        super(input, !input.get(0));
    }
}