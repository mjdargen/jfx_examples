package jfx_examples;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class _13Character extends Rectangle {
  private int xVel = 5;
  private int yVel = 0;

  public _13Character(int x, int y, int width, int height, Color color) {
    super(x, y, width, height);
    setFill(color);
  }

  public int getxVel() {
    return xVel;
  }

  public void setxVel(int xVel) {
    this.xVel = xVel;
  }

  public int getyVel() {
    return yVel;
  }

  public void setyVel(int yVel) {
    this.yVel = yVel;
  }

}