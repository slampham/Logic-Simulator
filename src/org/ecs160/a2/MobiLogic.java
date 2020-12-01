package org.ecs160.a2;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiLogic {
    private toolBar tBar;
    private workSpace wSpace;
    private menu mainMenu;
    private formApp app;

    private String userSelectedGate;

    public MobiLogic() {
        tBar = new toolBar();
        tBar.setScrollableX(true);
        wSpace = new workSpace();
        wSpace.setScrollableX(false);
        mainMenu = new menu();
        app = new formApp(mainMenu, wSpace, tBar);

        userSelectedGate = "None";
    }

    public void initUIComponents() {
        app.show();
    }

    public void initAppLogic() {
        attachActionListenersToGrid();
        userSelectsGateFromNavBarEvent();
    }

    private void userSelectsGateFromNavBarEvent() {
        userSelectsGateFromNavBarEvent(tBar.getButton("AND Gate"));
        userSelectsGateFromNavBarEvent(tBar.getButton("NAND Gate"));
        userSelectsGateFromNavBarEvent(tBar.getButton("NOR Gate"));
        userSelectsGateFromNavBarEvent(tBar.getButton("NOT Gate"));
        userSelectsGateFromNavBarEvent(tBar.getButton("OR Gate"));
        userSelectsGateFromNavBarEvent(tBar.getButton("XNOR Gate"));
        userSelectsGateFromNavBarEvent(tBar.getButton("XOR Gate"));
    }

    private void userSelectsGateFromNavBarEvent(CustomizedNav button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userSelectedGate = button.getName();
                mainMenu.updateGateSelected(userSelectedGate);
            }
        });
    }

    private void attachActionListenersToGrid () {
        for (String key: wSpace.getWorkSpace().keySet()) {
            wSpace.getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (!userSelectedGate.equals("None")) {
                        wSpace.getGridCell(key).addComponent(userSelectedGate);
                        userSelectedGate = "None";
                    }
                }
            });
        }
    }














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

            if (entry instanceof Gate) { // FIXME: bad coding practice, but not sure how to refactor this
                ((Gate) entry.getKey()).setInputs(inputs);
            }
            else if (entry instanceof LED) {
                ((LED) entry.getKey()).setInput(inputs.get(0));
            }
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
