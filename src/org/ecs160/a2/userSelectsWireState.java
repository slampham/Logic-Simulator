package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
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
        computeGridCellStates();
        attachActionListenersToGrid(context);
        System.out.println("user selects wire state");
    }

    // attaches action listeners to all grid cells
    // takes care of the case that the user wants to select another wire from the wire menu
    private void attachActionListenersToGrid (MobiLogicContext context) {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    app.getWorkSpace().getGridCell(key).addComponent(app, userSelectedComponent);
                    context.setState(new userSelectsWireMenuState(app));
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
