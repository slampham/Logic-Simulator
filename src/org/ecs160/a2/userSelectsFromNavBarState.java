//package org.ecs160.a2;
//
//import com.codename1.ui.Button;
//import com.codename1.ui.events.ActionEvent;
//import com.codename1.ui.events.ActionListener;
//
//public class userSelectsFromNavBarState implements MobiLogicState {
//    private formApp app;
//    private String userSelectedComponent;
//
//    public userSelectsFromNavBarState (formApp app, String userSelectedComponent) {
//        this.app = app;
//        this.userSelectedComponent = userSelectedComponent;
//    }
//
//    @Override
//    public void computeAction(MobiLogicContext context) {
//        attachActionListenersToGrid(context);
//        System.out.println("user selects gate from nav-bar state");
//    }
//
//    private void attachActionListenersToGrid (MobiLogicContext context) {
//        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
//            app.getWorkSpace().getGridCell(key).addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    removeActionListeners();
//                    app.getWorkSpace().getGridCell(key).addComponent(userSelectedComponent);
//                    app.getMainMenu().updateGateSelected("Gate Appears Here");
//                    context.setState(new InitState(app));
//                    context.getState().computeAction(context);
//                }
//            });
//        }
//    }
//
//    private void removeActionListeners() {
//        for (String key: app.getWorkSpace().getWorkSpaceMap().keySet()) {
//            removeActionListener(app.getWorkSpace().getGridCell(key));
//        }
//    }
//
//    private void removeActionListener(Button button) {
//        if (button != null && !button.getListeners().isEmpty()) {
//            for (int i = 0; i < button.getListeners().toArray().length; i++) {
//                button.removeActionListener((ActionListener) button.getListeners().toArray()[i]);
//            }
//        }
//    }
//}
