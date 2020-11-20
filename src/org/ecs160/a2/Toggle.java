package org.ecs160.a2;

public class Toggle {
  public Boolean output = false;

  public void toggle() {
    output = !output;
  }

  public Boolean output() {
    return output;
  }
}
