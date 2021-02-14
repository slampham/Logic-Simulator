package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import java.util.*;

public class userSelectsFromGridState implements MobiLogicState{
    /**
     * When the user selects a grid cell (to delete a component, to turn on/off a toggle, etc.),
     * we create a new state of this type. Aside from InitState, this state sees the most action.
     */

    private formApp app;
    private Integer userSelectedGridCell;
    private ArrayList<String> gateList = new ArrayList<>(
            Arrays.asList("AND Gate", "NAND Gate", "NOR Gate", "XNOR Gate",
                    "OR Gate", "NOT Gate", "XOR Gate"));

    public userSelectsFromGridState(formApp app, Integer userSelectedGridCell) {
        this.app = app;
        this.userSelectedGridCell = userSelectedGridCell;
    }

    @Override
    public void computeAction(MobiLogicContext context){
        controlToggle();
        userSetsPropagationDelayFunctionality();
        refreshScreen();
        highlightUserSelectedGridCell();
        userSelectsFromGridAgainState(context);
        trashCanFunctionality(context);
    }

    /* Attaches action listeners to all grid cells.
       Takes care of the case that the user wants to select another grid cell from the workspace. */
    private void userSelectsFromGridAgainState(MobiLogicContext context) {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            app.getWorkSpace().getGridCell(key).addActionListener(evt -> {
                removeActionListeners();
                Integer userSelectedGridCell = app.getWorkSpace().getGridCell(key).getCell();
                context.setState(new userSelectsFromGridState(app, userSelectedGridCell));
                context.getState().computeAction(context);
            });
        }
    }

    /* Implements toggle functionality (turning it on/off.) */
    private void controlToggle() {
        if (app.getWorkSpace().getGridCell(userSelectedGridCell).isFilled() &&
                app.getWorkSpace().getGridCell(userSelectedGridCell).getComponent().getName().equals("Toggle")) {
            Integer previousState = app.getWorkSpace().getGridCell(userSelectedGridCell).getOutput();

            app.getWorkSpace().getGridCell(userSelectedGridCell).getComponent().updateState((previousState ^ 1));
            app.getWorkSpace().getGridCell(userSelectedGridCell).getComponent().calculateOutput(app);
        }
    }

    /* Refreshes screen based on propagation delay attached to gates. */
    private void refreshScreen(){
        // loops through all grid cells, updating each grid cell's delay value and state value
        // any gates found have their delay updated, but are skipped over before state is updated
        for (int key = 0; key < 96; key++) {
            app.getWorkSpace().getGridCell(key).unhighlightGridCell();
            app.getWorkSpace().getGridCell(key).updateDelay(app);
            // if a gate is found, attach a timer that updates state based on the grid cell's delay value
            if (app.getWorkSpace().getGridCell(key).getComponent() != null &&
                    gateList.contains(app.getWorkSpace().getGridCell(key).getComponent().getName())) {
                Timer timer = new Timer();
                int gateKey = key;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // updates state from the gate at `gateKey` until the next gate
                        // this prevents any overlapping state updates
                        app.getWorkSpace().getGridCell(gateKey).updateState(app);
                        for (int i = gateKey + 1; i < 96; i++) {
                            if (app.getWorkSpace().getGridCell(i).getComponent() != null &&
                                    gateList.contains(app.getWorkSpace().getGridCell(i).getComponent().getName()))
                                break;
                            app.getWorkSpace().getGridCell(i).updateState(app);
                        }
                        app.show();
                    }
                }, app.getWorkSpace().getGridCell(key).getDelay() * 1000);
                continue;
            }
            app.getWorkSpace().getGridCell(key).updateState(app);
        }
        Storage.getInstance().writeObject("workspace", app.getWorkSpace().getWorkSpaceMap());
        app.show();
    }

    /* Attaches an action listener to the trash can, remove on state-change. */
    private void trashCanFunctionality(MobiLogicContext context) {
        if (app.getMainMenu().getButton("TRASH").getListeners() != null) {
            removeActionListener(app.getMainMenu().getButton("TRASH"));
        }
        app.getMainMenu().getButton("TRASH").addActionListener(evt -> {
            removeActionListeners();
            app.getWorkSpace().getGridCell(userSelectedGridCell).removeComponent();
            app.show();
            context.setState(new InitState(app));
            context.getState().computeAction(context);
        });
    }

    /* Allows user to set propagation delay based on menu's text-field. */
    private void userSetsPropagationDelayFunctionality() {
        Component selectedComp = app.getWorkSpace().getGridCell(userSelectedGridCell).getComponent();
        if (selectedComp != null && gateList.contains(selectedComp.getName())) {
            app.getMainMenu().getTextField().setEditable(true);
            app.getMainMenu().setPropagationDelay(Integer.toString(selectedComp.getDelay()));
            // update gate's prop delay after user hits [ENTER]
            app.getMainMenu().getTextField().setDoneListener(evt -> {
                if (!app.getMainMenu().getPropagationDelay().isEmpty() &&
                        isValidInteger(app.getMainMenu().getPropagationDelay()) &&
                        Integer.parseInt(app.getMainMenu().getPropagationDelay()) >= 0) {
                    selectedComp.setDelay(Integer.parseInt(app.getMainMenu().getPropagationDelay()));
                    app.getWorkSpace().getGridCell(userSelectedGridCell).updateDelay(app);
                }
                else selectedComp.setDelay(0);
            });
        } else {
            app.getMainMenu().getTextField().clear();
            app.getMainMenu().getTextField().setEditable(false);
        }
    }

    /* Embolden margins of user selected grid cell as a visual cue for the user. */
    private void highlightUserSelectedGridCell() {
        app.getWorkSpace().getGridCell(userSelectedGridCell).highlightGridCell();
        app.show();
    }

    /* Because grid cells have actions listeners that switch functionality depending on state,
       this function is used in every function that leads to a state-switch. */
    private void removeActionListeners() {
        for (Integer key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
            removeActionListener(app.getWorkSpace().getGridCell(key));
        }
        removeActionListener(app.getMainMenu().getButton("TRASH"));
    }

    /* Removes every single action listener attached to a button. */
    private void removeActionListener(Button button) {
        if (button != null && !button.getListeners().isEmpty()) {
            for (int i = 0; i < button.getListeners().toArray().length; i++) {
                button.removeActionListener((ActionListener<?>) button.getListeners().toArray()[i]);
            }
        }
    }

    /* Used in `userSetsPropagationDelayFunctionality` to check for valid user input into menu text-field. */
    private Boolean isValidInteger (String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException er) { return false; }
    }
}
