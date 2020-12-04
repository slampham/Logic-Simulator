package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class InitState implements MobiLogicState{
    private formApp app;
    private int counter = 0;
    public InitState(formApp app) {
        this.app = app;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        clearBoardFunctionality();
        userSelectsFromNavBarEvent(context);
        userSelectsFromGridEvent(context);
        System.out.println("init state");
    }

    private void clearBoardFunctionality() {
        app.getMainMenu().getButton("CLEAR").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                app.getMainMenu().getButton("CLEAR").removeActionListener(this);
                for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
                    app.getWorkSpace().getGridCell(key).removeComponent();
                }
                app.show(); // this line refreshes the screen
            }
        });
    }

    public void userSelectsFromNavBarEvent(MobiLogicContext context) {
        for (String key: app.getToolBar().getToolBarMap().keySet()) {
            if (!key.equals("Wire") && !key.equals("Toggle") && !key.equals("LED")) // gates
                userSelectsGateFromNavBarEvent(app.getToolBar().getButton(key), context);
            else if (key.equals("Toggle") || key.equals("LED")) // toggles, led
                userSelectsPeripheralsFromNavBarEvent(app.getToolBar().getButton(key), context);
            // TODO: wires
        }
    }

    private void userSelectsGateFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeActionListeners();
                String userSelectedComponent = button.getName();
                app.getMainMenu().updateGateSelected(userSelectedComponent);
                context.setState(new userSelectsFromNavBarState(app, userSelectedComponent));
                context.getState().computeAction(context);
            }
        });
    }

    private void userSelectsPeripheralsFromNavBarEvent(CustomizedNav button, MobiLogicContext context) {
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

    private void userSelectsFromGridEvent(MobiLogicContext context) {
        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    String userSelectedGridCell = app.getWorkSpace().getGridCell(key).getCell();
                    context.setState(new userSelectsFromGridState(app, userSelectedGridCell));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    private void removeActionListeners() {
        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }

        for (String key: app.getToolBar().getToolBarMap().keySet()) {
            /* FIXME: the wire button currently has not been implemented (12/2/2020).
                Once the wire state/button is implemented, then remove if-statement below.
                There is a known bug: if wire button is pressed, nav-bar stuff sometimes won't work.
                I say sometimes because the program loses track of state, and therefore action listeners
                aren't being deleted correctly. Hence "sometimes."
            */
            if (!key.equals("Wire"))
                removeActionListener(app.getToolBar().getButton(key));
        }
        removeActionListener(app.getMainMenu().getButton("CLEAR"));
    }

    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
            }
        }
    }
}
