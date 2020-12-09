package org.ecs160.a2;
import com.codename1.io.Externalizable;
import com.codename1.ui.Image;

public interface Component extends Externalizable {
    /**
     * This interface encapsulates gates, wires, LEDs/toggles.
     * Gates are implemented separately. Wires/LEDs/toggles fall under the "Peripheral" object umbrella.
     * I implemented such an interface because we need a way to attach these objects to a grid cell
     * such that they fall under a single umbrella; thus, "state changer". Sue me, I'm terrible at naming things.
     *
     * Each function below is unique to gates, wires, LEDs/toggles.
     */

    //public Boolean getOutput();
    //public Integer getDelay();
    //public void updateState(Boolean state);

    Integer getOutput();
    String getName();
    Image getImage();

    void updateState(Integer state);
    void calculateOutput(formApp app);
}