package org.ecs160.a2;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsWireState implements MobiLogicState {
    private formApp app;

    public userSelectsWireState(formApp app) {
        this.app = app;
        app.getToolBar().initWireBarButtons();
        app.show();
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        backFunctionality(context);
        System.out.println("user selects wire state");
    }

    private void backFunctionality(MobiLogicContext context) {
        app.getToolBar().getButton("Back").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                app.getToolBar().initToolBarButtons();
                app.show();
                context.setState(new InitState(app));
                context.getState().computeAction(context);
            }
        });
    }
}
