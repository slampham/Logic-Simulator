package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.events.ActionListener;

public class userSelectsWireState implements MobiLogicState{
    /**
     * When the user has selected a wire from the wire menu (and possible wants to place it down),
     * we create a new state of this type.
     */

    private formApp app;
    private String userSelectedComponent;

    public userSelectsWireState (formApp app, String userSelectedComponent) {
        this.app = app;
        this.userSelectedComponent = userSelectedComponent;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        refreshScreen();
        attachActionListenersToGrid(context);
    }

    // attaches action listeners to all grid cells
    // takes care of the case that the user wants to select another wire from the wire menu
    private void attachActionListenersToGrid (MobiLogicContext context) {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(evt -> {
                removeActionListeners();
                app.getWorkSpace().getGridCell(key).addComponent(app, userSelectedComponent);
                app.getMainMenu().updateGateSelected("Selected Tool");
                context.setState(new userSelectsWireMenuState(app));
                context.getState().computeAction(context);
            });
        }
    }

    // this function updates state by iterating through each individual grid cell
    private void refreshScreen() {
        app.getMainMenu().getTextField().clear();
        for (int key = 0; key < 96; key++) {
            app.getWorkSpace().getGridCell(key).unhighlightGridCell();
            app.getWorkSpace().getGridCell(key).updateState(app);
        }
        Storage.getInstance().writeObject("workspace", app.getWorkSpace().getWorkSpaceMap());
        app.show();
    }

    // because grid cells have actions listeners that switch functionality depending on state,
    // this function is used in every function that leads to a state-switch
    private void removeActionListeners() {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
    }

    // removes every single action listener attached to a button
    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener<?>) button.getListeners().toArray()[i]);
            }
        }
    }
}
