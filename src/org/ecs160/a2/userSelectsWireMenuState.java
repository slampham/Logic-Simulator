package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsWireMenuState implements MobiLogicState {
    private formApp app;

    public userSelectsWireMenuState(formApp app) {
        this.app = app;
        app.getToolBar().initWireBarButtons();
        app.show();
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        backFunctionality(context);
        userSelectsWireEvent(context);
        userSelectsFromGridEvent(context);
        System.out.println("user selects wire menu state");
    }

    private void backFunctionality(MobiLogicContext context) {
        if (app.getToolBar().getButton("Back").getListeners() == null) {
            app.getToolBar().getButton("Back").addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    app.getToolBar().initToolBarButtons();
                    app.show();
                    context.setState(new InitState(app));
                    context.getState().computeAction(context);
                }
            });
        }
    }

    private void userSelectsWireEvent(MobiLogicContext context) {
        for (String key: app.getToolBar().getToolBarMap().keySet()) {
            if (!key.equals("Back"))
                userSelectsWireEvent(app.getToolBar().getButton(key), context);
        }
    }

    private void userSelectsWireEvent(CustomizedNav button, MobiLogicContext context) {
        if (button.getListeners() == null) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    removeActionListeners();
                    String userSelectedComponent = button.getName();
                    context.setState(new userSelectsWireState(app, userSelectedComponent));
                    context.getState().computeAction(context);
                }
            });
        }
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
    }

    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
            }
        }
    }
}
