package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.EventDispatcher;

public class userSelectsFromNavBarState implements MobiLogicState {
    private formApp app;
    private String userSelectedComponent;

    public userSelectsFromNavBarState (formApp app, String userSelectedComponent) {
        this.app = app;
        this. userSelectedComponent = userSelectedComponent;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        attachActionListenersToGrid(context);
    }

    private void attachActionListenersToGrid (MobiLogicContext context) {
        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    app.getWorkSpace().getGridCell(key).addComponent(userSelectedComponent);
                    app.getMainMenu().updateGateSelected("Gate Appears Here");
                    context.setState(new InitState(app));
                    context.getState().computeAction(context);
                    removeListeners();
                }
            });
        }
    }

    private void removeListeners() {
        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
    }

    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            ActionListener l = (ActionListener) button.getListeners().toArray()[0];
            button.removeActionListener(l);
        }
    }
}
