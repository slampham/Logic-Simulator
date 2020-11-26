package org.ecs160.a2;
import java.util.List;

public class Gate implements Component {

    /**
     * The Gate class is a template for the logic gates we want to implement. It uses an ArrayList to store inputs.
     * We use an ArrayList to keep input agnostic. That is, most logic gates use 2 inputs with the exception
     * of the NOT gate. As such, future gate classes that derive from this super class can determine the number of inputs
     * in their own constructors.
     *
     * For state values, I decided to use Java's Boolean values: TRUE and FALSE.
     *
     * The Gate class implements the getters/setters that future gate classes can use. Each gate class that derives from
     * this class should implicitly/internally calculate its output.
     */

    public List<Boolean> inputs; // Use list instead of ArrayList to allow flexibility. See: https://stackoverflow.com/questions/2279030/type-list-vs-type-arraylist-in-java
    public Boolean output;

    public Gate (List<Boolean> inputs, Boolean output) {
        this.inputs = inputs;
        this.output = output;
    }

    public List<Boolean> getInputs() {return inputs;}

    public void setInputs(List<Boolean> inputs) {this.inputs = inputs;}

    public Boolean output() {return output;}
}