package org.ecs160.a2;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class userSelectsFromGridState implements MobiLogicState{
    private formApp app;
    private String userSelectedGridCell;

    public userSelectsFromGridState(formApp app, String userSelectedGridCell) {
        this.app = app;
        this. userSelectedGridCell = userSelectedGridCell;
    }

    @Override
    public void computeAction(MobiLogicContext context) {
        trashCanFunctionality(context);
    }

    private void trashCanFunctionality(MobiLogicContext context) {
        app.getMainMenu().getButton("TRASH").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                app.getWorkSpace().getGridCell(userSelectedGridCell).removeComponent();
                context.setState(new InitState(app));
                context.getState().computeAction(context);
                app.show();
                app.getMainMenu().getButton("TRASH").removeActionListener(this);
            }
        });
    }
}
