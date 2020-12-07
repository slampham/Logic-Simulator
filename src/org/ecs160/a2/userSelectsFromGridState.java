package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsFromGridState implements MobiLogicState{
    /**
     * When the user selects a grid cell (to delete a component, to turn on/off a toggle, etc.),
     * we create a new state of this type.
     */

    private formApp app;
    private Integer userSelectedGridCell;

    public userSelectsFromGridState(formApp app, Integer userSelectedGridCell) {
        this.app = app;
        this.userSelectedGridCell = userSelectedGridCell;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        controlToggle();
        computeGridCellStates();
        userSelectsFromGridAgainState(context);
        trashCanFunctionality(context);
        System.out.println("user selects from grid state");
    }

    // attaches action listeners to all grid cells
    // takes care of the case that the user wants to select another grid cell from the workspace
    private void userSelectsFromGridAgainState(MobiLogicContext context) {
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

    // implements toggle functionality (turning it on/off)
    private void controlToggle() {
        if (app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger() != null &&
                app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().getName().equals("Toggle")) {
            Boolean previousState = app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().getOutput();
            app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().updateState(!previousState);
        }
    }

    // FIXME: terrible function
    private void computeGridCellStates() {
        for (int i = 0; i < 5; i++) {
            for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
                if (app.getWorkSpace().getGridCell(key).getStateChanger() != null) {
                    app.getWorkSpace().getGridCell(key).updateState();
                }
            }
        }
        app.show();
    }

    // attaches an action listener to the trash can, remove on state-change
    private void trashCanFunctionality(MobiLogicContext context) {
        if (app.getMainMenu().getButton("TRASH").getListeners() != null) {
            removeActionListener(app.getMainMenu().getButton("TRASH"));
        }
        app.getMainMenu().getButton("TRASH").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeActionListeners();
                app.getWorkSpace().getGridCell(userSelectedGridCell).removeComponent();
                app.show();
                context.setState(new InitState(app));
                context.getState().computeAction(context);
            }
        });
    }

    private void removeActionListeners() {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
        removeActionListener(app.getMainMenu().getButton("TRASH"));
    }

    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
            }
        }
    }
}
