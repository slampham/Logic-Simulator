package org.ecs160.a2;

import com.codename1.ui.Container;
import com.codename1.ui.Form;

public class appLogic {
    private Form other;
    private workSpace workSpaceRef;
    private toolBar toolBarRef;
    private boolean userDrag;
    public appLogic(menu main_menu, workSpace workSpaceRef, toolBar toolBarRef) {
        //reset
        //event listeners for buttons and whatnot
        this.addDragListener();

    /*
    public void resetModes() {
        appendMode = false;
        decimalPointMode = false;
        justEntered = false;
        rootCurveMode = false;
        linearCurveMode = LC_STAGE.FALSE;
    }
     */
    }
    public void addDragListener() {

//        CustomizedButton piButton = this.keypadRef.getButton("PI");
//        piButton.addActionListener((evt) -> {
//            unaryOperation(Math.PI);
//        });
//        CustomizedButton eulerButton = this.keypadRef.getButton("EU");
//        eulerButton.addActionListener((evt) -> {
//            unaryOperation(Math.E);
//        });

        // goal:
        // check if user dragged a button
        // if so, allow drag, and reset toolbar [how do we do this?]
    }

    public void resetModes() {
        userDrag = false;
    }
    // implement event listeners
    // implement how reset works
}
