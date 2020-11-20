package org.ecs160.a1;

import java.util.ArrayList;

public class NOTGate extends Gate{
    public NOTGate (ArrayList<Boolean> input) {
        super(input, !input.get(0));
    }
}