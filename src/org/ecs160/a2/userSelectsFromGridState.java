package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import java.util.ArrayList;
import java.util.Arrays;

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
        refreshScreen();
        highlightUserSelectedGridCell();
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
        if (app.getWorkSpace().getGridCell(userSelectedGridCell).isFilled() &&
                app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().getName().equals("Toggle")) {
            //Boolean previousState = app.getWorkSpace().getGridCell(userSelectedGridCell).getOutput();
            Integer previousState = app.getWorkSpace().getGridCell(userSelectedGridCell).getOutput();
            System.out.println("This is previous state: " + previousState);

            app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().updateState((previousState ^ 1));
            app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().calculateOutput(app);
        }
    }

    private void refreshScreen() {
        for (int key = 0; key < 96; key++) {
            app.getWorkSpace().getGridCell(key).unhighlightGridCell();
            app.getWorkSpace().getGridCell(key).updateState(app);
        }
        Storage.getInstance().writeObject("workspace", app.getWorkSpace().getWorkSpaceMap());
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

    //FIXME:
    // need to refactor gates to superclass in order to cast getStateChanger
    // need to assign prop delay to selected gate
//    private void propDelayFunctionality(MobiLogicContext context) {
//        ArrayList<String> Gates = new ArrayList<String>(
//                Arrays.asList("AND Gate", "NAND Gate", "NOR Gate", "XNOR Gate",
//                        "OR Gate", "NOT Gate", "XOR Gate"));
//
//        if (app.getMainMenu().propagation_delay.getDoneListener() != null) {
//            removeActionListener(app.getMainMenu().propagation_delay);
//        }
//        // determine if grid cell selected is gate
//        // extract data with getText()
//        // clear text with clear() once user clicks somewhere else
//        app.getMainMenu().propagation_delay.setDoneListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                removeActionListeners();
//
//                //checking if the grid cell selected is a gate
//                if (Gates.contains(app.getWorkSpace().getGridCell(userSelectedGridCell).getStateChanger().getName())) {
//                    String propDelay = app.getMainMenu().propagation_delay.getText();
//
//                }
//                app.getWorkSpace().getGridCell(userSelectedGridCell).removeComponent();
//                app.show();
//                context.setState(new InitState(app));
//                context.getState().computeAction(context);
//            }
//        });
//    }

    private void highlightUserSelectedGridCell() {
        app.getWorkSpace().getGridCell(userSelectedGridCell).highlightGridCell();
        app.show();
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
