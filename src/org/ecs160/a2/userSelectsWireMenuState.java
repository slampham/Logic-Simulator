package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.events.ActionListener;

public class userSelectsWireMenuState implements MobiLogicState {
    /**
     * When the user has selected the "Wire" button from the nav-bar,
     * we create a new state of this type.
     */

    private formApp app;

    public userSelectsWireMenuState(formApp app) {
        this.app = app;
        app.getToolBar().initWireBarButtons();
        app.show();
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        refreshScreen();
        backFunctionality(context);
        userSelectsWireEvent(context);
        userSelectsFromGridEvent(context);
    }

    // attaches an action listener to the "Back" button of the wire menu
    // takes care of the case: user wants to go back to normal nav-bar
    private void backFunctionality(MobiLogicContext context) {
        if (app.getToolBar().getButton("Back").getListeners() == null) {
            app.getToolBar().getButton("Back").addActionListener(evt -> {
                removeActionListeners();
                app.getToolBar().initToolBarButtons();
                app.show();
                context.setState(new InitState(app));
                context.getState().computeAction(context);
            });
        }
    }

    // attaches action listeners to wire-menu toolbar
    // like the main tool bar, these action listeners are also persistent
    private void userSelectsWireEvent(MobiLogicContext context) {
        for (String key: app.getToolBar().getToolBarMap().keySet()) {
            if (!key.equals("Back"))
                userSelectsWireEvent(app.getToolBar().getButton(key), context);
        }
    }

    // changes state to userSelectsWireState when the user selects a wire
    private void userSelectsWireEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(evt -> {
                removeActionListeners();
                String userSelectedComponent = button.getName();
                app.getMainMenu().updateGateSelected(userSelectedComponent + " wire");
                context.setState(new userSelectsWireState(app, userSelectedComponent));
                context.getState().computeAction(context);
            });
        }
    }

    // attaches action listeners to all grid cells
    // takes care of the case that the user wants to select another grid cell from the workspace
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
