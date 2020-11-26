package org.ecs160.a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiLogic {
    Map<Gate, List<Gate>> gate_connections = new HashMap<>();
    /*** For each key (gate), the value (list) represents all the inputs into the key. For example:
     * A -----C
     * B ----/
     *
     * C has two inputs: A, B. In 'input_toggles', the map will contain the key 'Gate C'. The corresponding
     * list (value) is [A, B]. Hence, you can get all inputs of a gate just by looking it up in the map.
     * ***/

    Map<Gate, List<Toggle>> toggle_connections = new HashMap<>(); // Similar concept with input_toggles as with input_gates
    Map<LED, Gate> led_connections = new HashMap<>();

    public void update() {
        List<Boolean> inputs = new ArrayList<>();

        for (Map.Entry<Gate, List<Toggle>> entry : toggle_connections.entrySet()) {
            for (Toggle toggle : entry.getValue()) {
                inputs.add(toggle.output());
            }

            entry.getKey().setInputs(inputs);
        }

        inputs.clear();
        for (Map.Entry<Gate, List<Gate>> entry : gate_connections.entrySet()) {
            for (Gate input_gate : entry.getValue()) {
                inputs.add(input_gate.output());
            }

            entry.getKey().setInputs(inputs);
        }
    }

    public void connectToggle(Toggle toggle, Gate gate) {
        toggle_connections.get(gate).add(toggle);
        update();
    }

    public void disconnectToggle(Toggle toggle, Gate gate) {
        toggle_connections.get(gate).remove(toggle);
        update();
    }

    public void connectGates(Gate input_gate, Gate gate) {
        gate_connections.get(gate).add(input_gate);
        update();
    }

    public void disconnectGates(Gate input_gate, Gate gate) {
        gate_connections.get(gate).remove(input_gate);
        update();
    }

    public void connectLED(LED led, Gate gate) {
        led_connections.put(led, gate);
    }


}
