package org.ecs160.a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiLogic {
    Map<Gate, List<Gate>> input_gates = new HashMap<>();
    /*** For each key (gate), the value (list) represents all the inputs into the key. For example:
     * A -----C
     * B ----/
     *
     * C has two inputs: A, B. In 'input_toggles', the map will contain the key 'Gate C'. The corresponding
     * list (value) is [A, B]. Hence, you can get all inputs of a gate just by looking it up in the map.
     * ***/

    Map<Gate, List<Toggle>> input_toggles = new HashMap<>(); // Similar concept with input_toggles as with input_gates

    List<Boolean> leds; // TODO: refactor to LED's when LED class gets created

    public void update() {
        List<Boolean> inputs = new ArrayList<>();

        for (Map.Entry<Gate, List<Toggle>> entry : input_toggles.entrySet()) {
            for (Toggle toggle : entry.getValue()) {
                inputs.add(toggle.output());
            }

            entry.getKey().setInputs(inputs);
        }

        inputs.clear();
        for (Map.Entry<Gate, List<Gate>> entry : input_gates.entrySet()) {
            for (Gate input_gate : entry.getValue()) {
                inputs.add(input_gate.getOutput());
            }

            entry.getKey().setInputs(inputs);
        }
    }

    public void connectToggle(Toggle toggle, Gate gate) {
        input_toggles.get(gate).add(toggle);
    }

    public void connectInputGate(Gate gate, Gate input_gate) {
        input_gates.get(gate).add(input_gate);
    }
}
