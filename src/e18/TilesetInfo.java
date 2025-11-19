import javafx.scene.image.Image;

public class TilesetInfo {
  public final Image image;
  public final int tileWidth;
  public final int tileHeight;
  public final int margin;
  public final int spacing;
  public final int sheetCols;
  public final int sheetRows;

  public TilesetInfo(Image image, int tileWidth, int tileHeight, int margin, int spacing, int sheetCols,
      int sheetRows) {
    this.image = image;
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
    this.margin = margin;
    this.spacing = spacing;
    this.sheetCols = sheetCols;
    this.sheetRows = sheetRows;
  }
}
