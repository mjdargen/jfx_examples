import javafx.scene.shape.Shape;
import java.util.List;
import java.util.Map;

public class TiledMap {
  public final Map<String, List<TileSprite>> tileLayers;
  public final Map<String, List<Shape>> objectLayers;
  public final int mapTileWidth;
  public final int mapTileHeight;

  public TiledMap(Map<String, List<TileSprite>> tileLayers,
      Map<String, List<Shape>> objectLayers,
      int mapTileWidth, int mapTileHeight) {
    this.tileLayers = tileLayers;
    this.objectLayers = objectLayers;
    this.mapTileWidth = mapTileWidth;
    this.mapTileHeight = mapTileHeight;
  }
}
