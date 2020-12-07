package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class InitState implements MobiLogicState{
    private formApp app;
    public InitState(formApp app) {
        this.app = app;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        computeGridCellStates();
        clearBoardFunctionality();
        userSelectsFromNavBarEvent(context);
        userSelectsFromGridEvent(context);
        System.out.println("init state");
    }

    // attaches an action listener to the "clear" button to wipe components from every grid cell
    private void clearBoardFunctionality() {
        if (app.getMainMenu().getButton("CLEAR").getListeners() == null) {
            app.getMainMenu().getButton("CLEAR").addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    for (Integer key : app.getWorkSpace().getWorkSpaceMap().keySet()) {
                        app.getWorkSpace().getGridCell(key).removeComponent();
                    }
                    app.show(); // this line refreshes the screen
                }
            });
        }
    }

    // attaches button listeners to the toolbar
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

    // keeps track of user-selected gate and switches to userSelectsGateFromNavBarState
    private void userSelectsGateFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    String userSelectedComponent = button.getName();
                    app.getMainMenu().updateGateSelected(userSelectedComponent);
                    context.setState(new userSelectsGateFromNavBarState(app, userSelectedComponent));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    // keeps track of user-selected peripheral (LED/toggle) and switches to userSelectsPeripheralsFromNavBarState
    private void userSelectsPeripheralsFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    String userSelectedComponent = button.getName();
                    context.setState(new userSelectsPeripheralsFromNavBarState(app, userSelectedComponent));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    // switches to userSelectsWireMenu state
    private void userSelectsWireFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    context.setState(new userSelectsWireMenuState(app));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    // keeps track of user-selected grid cell and switches to userSelectsFromGridState
    private void userSelectsFromGridEvent(MobiLogicContext context) {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    Integer userSelectedGridCell = app.getWorkSpace().getGridCell(key).getCell();
                    context.setState(new userSelectsFromGridState(app, userSelectedGridCell));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    // this function updates state by iterating through each individual grid cell
    private void computeGridCellStates() {
        for (int key = 0; key < 96; key++) {
            app.getWorkSpace().getGridCell(key).updateState();
        }
        app.show();
    }

    // because grid cells have actions listeners that switch functionality depending on state,
    // remove all action listeners before each state switch
    private void removeActionListeners() {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
    }

    // removes every single action listener attached to a button
    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
            }
        }
    }
}
