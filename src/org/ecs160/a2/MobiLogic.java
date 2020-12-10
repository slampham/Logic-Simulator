package org.ecs160.a2;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiLogic {
    private toolBar tBar;
    private wireBar wBar;
    private workSpace wSpace;
    private menu mainMenu;
    private formApp app;

    private String userSelectedNavbarGate = "None";
    private String userSelectedNavbarPeripheral = "None";
    private String userSelectedBoardComponent = "None";

    public MobiLogic() {
        tBar = new toolBar();
        tBar.setScrollableX(true);
        wBar = new wireBar();
        wBar.setScrollableX(true);
        wSpace = new workSpace();
        wSpace.setScrollableX(false);
        mainMenu = new menu();
        // TODO: adjust formapp to accept both bars, and toolbar to be the same as  wirebar
        app = new formApp(mainMenu, wSpace, tBar);
    }

    public void initUIComponents() {
        app.show();
    }

    public void initAppLogic() {
        attachActionListenersToGrid();
        userSelectsGateFromNavBarEvent();
        userSelectsPeripheralsFromNavBarEvents();
        clearBoardFunctionality();
        trashCanFunctionality();
    }
    private void attachActionListenersToGrid () {
        for (String key: wSpace.getWorkSpaceMap().keySet()) {
            wSpace.getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (!userSelectedNavbarGate.equals("None")) {
                        wSpace.getGridCell(key).addComponent(userSelectedNavbarGate);
                        userSelectedNavbarGate = "None";
                        mainMenu.updateGateSelected("Gate Appears Here");
                    }
                    else if (!userSelectedNavbarPeripheral.equals("None")) {
                        wSpace.getGridCell(key).addComponent(userSelectedNavbarPeripheral);
                        userSelectedNavbarPeripheral = "None";
                    }
                    else if (wSpace.getGridCell(key).isFilled()) {
                        userSelectedBoardComponent = wSpace.getGridCell(key).getCell();
                    }
                }
            });
        }
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
                userSelectedNavbarGate = button.getName();
                mainMenu.updateGateSelected(userSelectedNavbarGate);
            }
        });
    }

    private void userSelectsPeripheralsFromNavBarEvents() {
        userSelectsPeripheralsFromNavBarEvents(tBar.getButton("Toggle"));
        userSelectsPeripheralsFromNavBarEvents(tBar.getButton("LED"));
    }

    private void userSelectsPeripheralsFromNavBarEvents(CustomizedNav button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userSelectedNavbarPeripheral = button.getName();
            }
        });
    }

    private void clearBoardFunctionality() {
        mainMenu.getButton("CLEAR").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                for (String key: wSpace.getWorkSpaceMap().keySet()) {
                    wSpace.getGridCell(key).removeComponent();
                }
                app.show(); // this line refreshes the screen
            }
        });
    }
    
    private void trashCanFunctionality() {
        mainMenu.getButton("TRASH").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!userSelectedBoardComponent.equals("None")) {
                    wSpace.getGridCell(userSelectedBoardComponent).removeComponent();
                    userSelectedBoardComponent = "None";
                    app.show();
                }
            }
        });
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

    private void update() {
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

    public void connect(Component input, Component comp) {
        /* Connect inputting component to component (ex: connect AND gate to OR gate in front) */
        connections.get(comp).add(input);
        update();
    }

    public void disconnect(Component input, Component comp) {
        /* Disconnect inputting component to component (ex: disconnect AND gate from OR gate in front) */
        connections.get(comp).remove(input);
        update();
    }
}
