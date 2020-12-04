package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsPeripheralsFromNavBarState implements MobiLogicState {
    private formApp app;
    private String userSelectedComponent;

    public userSelectsPeripheralsFromNavBarState(formApp app, String userSelectedComponent) {
        this.app = app;
        this.userSelectedComponent = userSelectedComponent;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        attachActionListenersToGrid(context);
        userSelectsFromNavBarAgainEvent(context);
        System.out.println("user selects peripheral from nav-bar state");
    }

    private void userSelectsFromNavBarAgainEvent(MobiLogicContext context) {
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
                app.getMainMenu().updateGateSelected("Gate Appears Here");
                context.setState(new userSelectsPeripheralsFromNavBarState(app, userSelectedComponent));
                context.getState().computeAction(context);
            }
        });
    }

    private void attachActionListenersToGrid (MobiLogicContext context) {
        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    app.getWorkSpace().getGridCell(key).addComponent(userSelectedComponent);
                    context.setState(new InitState(app));
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
    }

    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
            }
        }
    }
}
