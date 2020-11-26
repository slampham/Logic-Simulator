
package org.ecs160.a2;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;

public class formApp extends Form {

    public formApp(menu main_menu, workSpace workSpaceRef, Container toolBarRef) {
        super("Logism 2.0");
        this.setLayout(new BorderLayout());

        this.add(BorderLayout.NORTH, main_menu);
        this.add(BorderLayout.CENTER, workSpaceRef);
        this.add(BorderLayout.SOUTH, toolBarRef);
    }
}