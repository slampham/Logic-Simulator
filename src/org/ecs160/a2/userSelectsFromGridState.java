package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsFromGridState implements MobiLogicState{
    private formApp app;
    private String userSelectedGridCell;

    public userSelectsFromGridState(formApp app, String userSelectedGridCell) {
        this.app = app;
        this.userSelectedGridCell = userSelectedGridCell;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        userSelectsFromGridAgainState(context);
        trashCanFunctionality(context);
        System.out.println("user selects from grid state");
    }

    private void userSelectsFromGridAgainState(MobiLogicContext context) {
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

    private void trashCanFunctionality(MobiLogicContext context) {
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
        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
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
