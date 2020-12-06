package org.ecs160.a2;

public class MobiLogicApp {
    private toolBar tBar;
    private wireBar wBar;
    private workSpace wSpace;
    private menu mainMenu;
    private formApp app;
    private MobiLogicContext context;

    public MobiLogicApp() {
        tBar = new toolBar();
        tBar.setScrollableX(true);
        wBar = new wireBar(); // just added. Backend can connect it. :p ~ Kody
        wBar.setScrollableX(true);
        wSpace = new workSpace();
        wSpace.setScrollableX(false);
        mainMenu = new menu();
        // TODO: adjust formapp to accept both bars, and toolbar to be the same as  wirebar
        app = new formApp(mainMenu, wSpace, tBar);
        context = new MobiLogicContext();
    }

    public void initUIComponents() {
        app.show();
    }

    public void initAppLogic() {
        context.setState(new InitState(app));
        context.getState().computeAction(context);
    }

}
