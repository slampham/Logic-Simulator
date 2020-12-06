package org.ecs160.a2;

public class MobiLogicApp {
    private toolBar tBar;
    private workSpace wSpace;
    private menu mainMenu;
    private formApp app;
    private MobiLogicContext context;

    public MobiLogicApp() {
        tBar = new toolBar();
        tBar.setScrollableX(true);
        wSpace = new workSpace();
        wSpace.setScrollableX(false);
        mainMenu = new menu();
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
