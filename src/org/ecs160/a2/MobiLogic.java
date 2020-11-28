package org.ecs160.a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiLogic {
    Map<Component, List<Component>> connections = new HashMap<>();
    /*** For each key (gate), the value (list) represents all the inputs into the key. For example:
     * A -----C
     * B ----/
     *
     * C has two inputs: A, B. In 'connections', the map will contain the key 'Gate C'. The corresponding
     * list (value) is [A, B]. Hence, you can get all inputs of a gate just by looking it up in the map.
     *
     * Same thing for LED. Note that LED can only have one input, but it will still be contained in that "list".
     *
     * ***/

    public void update() {
        List<Boolean> inputs = new ArrayList<>();
        for (Map.Entry<Component, List<Component>> entry : connections.entrySet()) {
            for (Component input_comp : entry.getValue()) {
                inputs.add(input_comp.output());
            }

            entry.getKey().setInputs(inputs);
        }
    }

    public void connect(Component comp, Component input) {
        connections.get(comp).add(input);
        update();
    }

    public void disconnect(Component comp, Component input) {
        connections.get(comp).remove(input);
        update();
    }
}
