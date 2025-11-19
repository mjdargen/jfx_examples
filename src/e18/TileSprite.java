import javafx.scene.image.Image;

public class TileSprite {
  public final Image image;
  public final double x;
  public final double y;
  public final double width;
  public final double height;
  public final boolean flipH;
  public final boolean flipV;
  public final boolean flipD;

  public TileSprite(Image image, double x, double y, double width, double height,
      boolean flipH, boolean flipV, boolean flipD) {
    this.image = image;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.flipH = flipH;
    this.flipV = flipV;
    this.flipD = flipD;
  }
}