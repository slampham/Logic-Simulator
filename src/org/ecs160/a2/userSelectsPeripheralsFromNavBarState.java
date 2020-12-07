package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsPeripheralsFromNavBarState implements MobiLogicState {
    /**
     * When the user has selected a toggle/LED from the nav-bar (and possibly wants to place it down),
     * we create a new state of this type.
     */

    private formApp app;
    private String userSelectedComponent;

    public userSelectsPeripheralsFromNavBarState(formApp app, String userSelectedComponent) {
        this.app = app;
        this.userSelectedComponent = userSelectedComponent;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        computeGridCellStates();
        attachActionListenersToGrid(context);
        System.out.println("user selects peripheral from nav-bar state");
    }

    // attaches an action listener to each grid cell, remove on state-change
    // takes care of the case: user wants to place the toggle/LED they've selected from the nav-bar on the workspace
    private void attachActionListenersToGrid (MobiLogicContext context) {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    app.getWorkSpace().getGridCell(key).addComponent(app, userSelectedComponent);
                    context.setState(new InitState(app));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    private void computeGridCellStates() {
        for (int key = 0; key < 96; key++) {
            if (app.getWorkSpace().getGridCell(key).getStateChanger() != null) {
                app.getWorkSpace().getGridCell(key).updateState();
            }
        }
        app.show();
    }

    private void removeActionListeners() {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
    }

    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
            }
        }
    }
}
