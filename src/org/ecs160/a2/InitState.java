package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.events.ActionListener;

public class InitState implements MobiLogicState{
    /**
     * InitState is the initial state the app begins in.
     * This state is also the state other states return to.
     * If viewed in a diagram, this state is the central state
     * where every other state evolves from.
     */
    private formApp app;
    public InitState(formApp app) {
        this.app = app;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        refreshScreen();
        clearBoardFunctionality();
        userSelectsFromNavBarEvent(context);
        userSelectsFromGridEvent(context);
    }

    /* Attaches an action listener to the "clear" button to wipe components from every grid cell. */
    private void clearBoardFunctionality() {
        if (app.getMainMenu().getButton("CLEAR").getListeners() == null) {
            app.getMainMenu().getButton("CLEAR").addActionListener(evt -> {
                for (Integer key : app.getWorkSpace().getWorkSpaceMap().keySet()) {
                    app.getWorkSpace().getGridCell(key).removeComponent();
                }
                Storage.getInstance().writeObject("workspace", app.getWorkSpace().getWorkSpaceMap());
                app.show(); // this line refreshes the screen
            });
        }
    }

    /* Attaches action listeners to the toolbar.
       The action listeners attached to this toolbar are persistent (not removed after every state switch).
       That is, this function (and the toolbar) can be used from other states due to persistent action listeners. */
    public void userSelectsFromNavBarEvent(MobiLogicContext context) {
        for (String key: app.getToolBar().getToolBarMap().keySet()) {
            if (!key.equals("Wire") && !key.equals("Toggle") && !key.equals("LED")) // gates
                userSelectsGateFromNavBarEvent(app.getToolBar().getButton(key), context);
            else if (key.equals("Toggle") || key.equals("LED")) // toggles, led
                userSelectsPeripheralsFromNavBarEvent(app.getToolBar().getButton(key), context);
            else
                userSelectsWireFromNavBarEvent(app.getToolBar().getButton(key), context);
        }
    }

    /* Keeps track of user-selected gate and switches to userSelectsGateFromNavBarState. */
    private void userSelectsGateFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(evt -> {
                removeActionListeners();
                String userSelectedComponent = button.getName();
                app.getMainMenu().updateGateSelected(userSelectedComponent);
                context.setState(new userSelectsGateFromNavBarState(app, userSelectedComponent));
                context.getState().computeAction(context);
            });
        }
    }

    /* Keeps track of user-selected peripheral (LED/toggle) and switches to userSelectsPeripheralsFromNavBarState. */
    private void userSelectsPeripheralsFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(evt -> {
                removeActionListeners();
                String userSelectedComponent = button.getName();
                app.getMainMenu().updateGateSelected(userSelectedComponent);
                context.setState(new userSelectsPeripheralsFromNavBarState(app, userSelectedComponent));
                context.getState().computeAction(context);
            });
        }
    }

    /* Switches to userSelectsWireMenu state. */
    private void userSelectsWireFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(evt -> {
                removeActionListeners();
                context.setState(new userSelectsWireMenuState(app));
                context.getState().computeAction(context);
            });
        }
    }

    /* Keeps track of user-selected grid cell and switches to userSelectsFromGridState. */
    private void userSelectsFromGridEvent(MobiLogicContext context) {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(evt -> {
                removeActionListeners();
                Integer userSelectedGridCell = app.getWorkSpace().getGridCell(key).getCell();
                context.setState(new userSelectsFromGridState(app, userSelectedGridCell));
                context.getState().computeAction(context);
            });
        }
    }

    /* This function updates state by iterating through each individual grid cell. */
    private void refreshScreen() {
        app.getMainMenu().getTextField().clear();
        for (int key = 0; key < 96; key++) {
            app.getWorkSpace().getGridCell(key).unhighlightGridCell();
            app.getWorkSpace().getGridCell(key).updateState(app);
        }
        Storage.getInstance().writeObject("workspace", app.getWorkSpace().getWorkSpaceMap());
        app.show();
    }

    /* Because grid cells have actions listeners that switch functionality depending on state,
       this function is used in every function that leads to a state-switch. */
    private void removeActionListeners() {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
    }

    /* Removes every single action listener attached to a button. */
    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener<?>) button.getListeners().toArray()[i]);
            }
        }
    }
}
