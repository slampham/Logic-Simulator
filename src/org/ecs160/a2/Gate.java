package org.ecs160.a2;
import java.util.ArrayList;

public class Gate {

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

    public ArrayList<Boolean> input;
    public Boolean output;

    public Gate (ArrayList<Boolean> input, Boolean output) {
        this.input = input;
        this.output = output;
    }

    public ArrayList<Boolean> getInput () {return input;}
    public void setInput (ArrayList<Boolean> input) {this.input = input;}

    public Boolean getOutput () {return output;}
}