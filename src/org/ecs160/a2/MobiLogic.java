package org.ecs160.a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiLogic {
    Map<Gate, List<Gate>> gate_connections = new HashMap<>();

    List<Toggle> toggles;
    List<Boolean> leds; // TODO: refactor to LED's when LED class gets created

    public void connectInputGate(Gate gate, Gate input_gate) {
        gate_connections.get(input_gate).add(gate);
    }

    public void connectOutputGate(Gate gate, Gate output_gate) {
        gate_connections.get(gate).add(output_gate);
    }
}
