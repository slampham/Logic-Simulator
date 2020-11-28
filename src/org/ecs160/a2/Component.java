package org.ecs160.a2;

import java.util.List;

public interface Component {
    Boolean output();



    void setInputs(List<Boolean> inputs);
    /*** FIXME: bad coding practice, but empty coding method in Toggle.java because I wanted to loop through all components,
     * and only setInputs for LED and Gates. So I left that method empty. However, if anyone know how to resolve this issue
     * without cluttering the workspace of Toggle, let me know! If we wanted to make sure classes dont have extra functions,
     * we would need to make three separate lists for LED, toggle, Gate! :L ***/

}
