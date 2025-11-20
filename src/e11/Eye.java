package e11;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Eye {
  private int width; // width of eye
  private int height; // heigh of eye
  private int x; // center x coordinate
  private int y; // center y coordinate
  private int openHeight; // height of the open
  private int blinkRate; // number of frames between update - period
  private int frames = 0; // number of frames since eye created
  private boolean closing = true; // if closing
  private boolean blinking = true; // if blinking activated
  private Color irisColor; // pupil color

  public Eye(int x, int y, int width, int height, Color irisColor) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.openHeight = height;
    this.blinkRate = (int) Math.random() * 3 + 2;
    this.irisColor = irisColor;
  }

  public Eye(int x, int y, int width, Color irisColor) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = (int) width / 2;
    this.openHeight = height;
    this.blinkRate = (int) Math.random() * 3 + 2;
    this.irisColor = irisColor;
  }

  public Group draw() {
    blinkUpdate();

    Group group = new Group();

    Ellipse sclera = new Ellipse(this.x, this.y, this.width, this.openHeight);
    sclera.setStroke(Color.BLACK);
    sclera.setFill(Color.WHITE);

    Ellipse iris = new Ellipse(this.x, this.y, this.width / 2, this.openHeight - 2);
    iris.setStroke(Color.TRANSPARENT);
    iris.setFill(this.irisColor);

    Ellipse pupil = new Ellipse(this.x, this.y, this.width / 4, this.openHeight - 2);
    pupil.setStroke(Color.TRANSPARENT);
    pupil.setFill(Color.BLACK);

    group.getChildren().addAll(sclera, iris, pupil);

    return group;

  }

  private void blinkUpdate() {
    this.frames++;
    if (this.frames % (this.blinkRate * this.height) == 0)
      this.blinking = true;

    if (this.blinking && this.frames % this.blinkRate == 0) {
      if (this.closing)
        this.openHeight -= (int) this.height / 8;
      else
        this.openHeight += (int) this.height / 8;
      if (this.openHeight <= 0)
        this.closing = false;
      if (this.openHeight > this.height) {
        this.blinking = false;
        this.closing = true;
        this.openHeight = this.height;
      }
    }
  }

}
