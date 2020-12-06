
package org.ecs160.a2;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;

public class formApp extends Form {
    private toolBar tBar;
    private menu mainMenu;
    private workSpace wSpace;

    // TODO: adjust formapp to accept both bars, and toolbar to be the same as wirebar
    public formApp(menu main_menu, workSpace workSpaceRef, toolBar toolBarRef) {
        super("Logism 2.0");
        this.setLayout(new BorderLayout());

        this.add(BorderLayout.NORTH, main_menu);
        this.add(BorderLayout.CENTER, workSpaceRef);
        this.add(BorderLayout.SOUTH, toolBarRef);

        tBar = toolBarRef;
        mainMenu = main_menu;
        wSpace = workSpaceRef;
    }

    public toolBar getToolBar() { return tBar; }
    public menu getMainMenu() { return mainMenu; }
    public workSpace getWorkSpace() { return wSpace; }
}