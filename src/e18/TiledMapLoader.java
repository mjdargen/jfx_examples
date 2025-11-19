import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * TiledMapLoader
 * Usage:
 * TiledMap map = TiledMapLoader.load("maps/level.tmx", 1.0);
 */
public class TiledMapLoader {

  public static TiledMap load(String tmxPath, double globalScale) throws Exception {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(tmxPath);
    Element root = doc.getDocumentElement();

    int mapTileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
    int mapTileHeight = Integer.parseInt(root.getAttribute("tileheight"));

    // Load tilesets. Use TreeMap so we can find the tileset with floorKey easily.
    TreeMap<Integer, TilesetInfo> tilesets = new TreeMap<>();

    NodeList tilesetNodes = root.getElementsByTagName("tileset");
    for (int i = 0; i < tilesetNodes.getLength(); i++) {
      Element ts = (Element) tilesetNodes.item(i);
      int firstgid = Integer.parseInt(ts.getAttribute("firstgid"));
      String source = ts.getAttribute("source"); // relative path to .tsx
      // parse tsx
      String tsxFullPath = resolveRelativePath(tmxPath, source);
      TilesetInfo info = loadTSX(tsxFullPath);
      tilesets.put(firstgid, info);
    }

    Map<String, List<TileSprite>> layers = new LinkedHashMap<>();
    Map<String, List<Shape>> objectLayers = new LinkedHashMap<>();

    NodeList layerNodes = root.getElementsByTagName("layer");
    for (int i = 0; i < layerNodes.getLength(); i++) {
      Element layer = (Element) layerNodes.item(i);
      String name = layer.getAttribute("name");
      int width = Integer.parseInt(layer.getAttribute("width"));
      int height = Integer.parseInt(layer.getAttribute("height"));

      Element dataEl = (Element) layer.getElementsByTagName("data").item(0);
      String csv = dataEl.getTextContent().trim();
      String[] rows = csv.split("\\r?\\n");

      List<TileSprite> sprites = new ArrayList<>();

      for (int r = 0; r < height; r++) {
        String row = rows[r].trim();
        String[] cols = row.split(",");
        for (int c = 0; c < width; c++) {
          String cell = cols[c].trim();
          if (cell.isEmpty())
            continue;
          long gidLong = Long.parseLong(cell);

          if (gidLong == 0)
            continue;

          boolean flipH = (gidLong & 0x80000000L) != 0;
          boolean flipV = (gidLong & 0x40000000L) != 0;
          boolean flipD = (gidLong & 0x20000000L) != 0;
          long gid = gidLong & 0x0FFFFFFFL;

          // find tileset
          Integer tsKey = tilesets.floorKey((int) gid);
          if (tsKey == null)
            continue; // should not happen
          TilesetInfo tsInfo = tilesets.get(tsKey);
          int localId = (int) (gid - tsKey);

          int tx = tsInfo.margin + (localId % tsInfo.sheetCols) * (tsInfo.tileWidth + tsInfo.spacing);
          int ty = tsInfo.margin + (localId / tsInfo.sheetCols) * (tsInfo.tileHeight + tsInfo.spacing);

          WritableImage tileImg = new WritableImage(
              tsInfo.image.getPixelReader(),
              tx, ty, tsInfo.tileWidth, tsInfo.tileHeight);

          // compute scale to match map tile size
          double tileScaleX = (double) mapTileWidth / tsInfo.tileWidth;
          double tileScaleY = (double) mapTileHeight / tsInfo.tileHeight;

          double finalWidth = tsInfo.tileWidth * tileScaleX * globalScale;
          double finalHeight = tsInfo.tileHeight * tileScaleY * globalScale;

          double drawX = c * mapTileWidth * globalScale;
          double drawY = r * mapTileHeight * globalScale;

          TileSprite sprite = new TileSprite(tileImg, drawX, drawY, finalWidth, finalHeight, flipH, flipV, flipD);
          sprites.add(sprite);
        }
      }
      layers.put(name, sprites);
    }

    // Parse objectgroup layers
    NodeList objectGroupNodes = root.getElementsByTagName("objectgroup");
    for (int i = 0; i < objectGroupNodes.getLength(); i++) {
      Element og = (Element) objectGroupNodes.item(i);
      String ogName = og.getAttribute("name");
      List<Shape> shapes = new ArrayList<>();

      NodeList objectNodes = og.getElementsByTagName("object");
      for (int j = 0; j < objectNodes.getLength(); j++) {
        Element obj = (Element) objectNodes.item(j);
        double x = Double.parseDouble(obj.getAttribute("x")) * globalScale;
        double y = Double.parseDouble(obj.getAttribute("y")) * globalScale;

        // width and height if present
        String widthAttr = obj.getAttribute("width");
        String heightAttr = obj.getAttribute("height");

        // tile object (has gid)
        if (obj.hasAttribute("gid")) {
          long gidLong = Long.parseLong(obj.getAttribute("gid"));
          long gid = gidLong & 0x0FFFFFFFL;
          Integer tsKey = tilesets.floorKey((int) gid);
          if (tsKey != null) {
            TilesetInfo tsInfo = tilesets.get(tsKey);
            // size scaled to map tile size
            double tileScaleX = (double) mapTileWidth / tsInfo.tileWidth;
            double tileScaleY = (double) mapTileHeight / tsInfo.tileHeight;
            double w = tsInfo.tileWidth * tileScaleX * globalScale;
            double h = tsInfo.tileHeight * tileScaleY * globalScale;
            Rectangle rct = new Rectangle(x, y - h, w, h); // Tiled y is bottom for tile objects
            shapes.add(rct);
          }
          continue;
        }

        if (!widthAttr.isEmpty() && !heightAttr.isEmpty()) {
          double w = Double.parseDouble(widthAttr) * globalScale;
          double h = Double.parseDouble(heightAttr) * globalScale;
          // Tiled object y is top-left for rectangles; adjust as needed
          Rectangle rct = new Rectangle(x, y, w, h);
          shapes.add(rct);
          continue;
        }

        // polygon object
        NodeList polygonNodes = obj.getElementsByTagName("polygon");
        if (polygonNodes.getLength() > 0) {
          Element polyEl = (Element) polygonNodes.item(0);
          String pts = polyEl.getAttribute("points").trim();
          String[] pairs = pts.split(" ");
          List<Double> coords = new ArrayList<>();
          for (String p : pairs) {
            if (p.isEmpty())
              continue;
            String[] xy = p.split(",");
            double px = Double.parseDouble(xy[0]) * globalScale + x;
            double py = Double.parseDouble(xy[1]) * globalScale + y;
            coords.add(px);
            coords.add(py);
          }
          double[] arr = coords.stream().mapToDouble(Double::doubleValue).toArray();
          Polygon polygon = new Polygon(arr);
          shapes.add(polygon);
          continue;
        }
      }

      objectLayers.put(ogName, shapes);
    }

    return new TiledMap(layers, objectLayers, mapTileWidth, mapTileHeight);
  }

  private static TilesetInfo loadTSX(String tsxPath) throws Exception {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(tsxPath);
    Element root = doc.getDocumentElement();

    int tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
    int tileHeight = Integer.parseInt(root.getAttribute("tileheight"));
    int margin = root.hasAttribute("margin") ? Integer.parseInt(root.getAttribute("margin")) : 0;
    int spacing = root.hasAttribute("spacing") ? Integer.parseInt(root.getAttribute("spacing")) : 0;

    Element imageEl = (Element) root.getElementsByTagName("image").item(0);
    String imagePath = imageEl.getAttribute("source");
    String tsxFolder = tsxPath.substring(0, tsxPath.lastIndexOf('/') + 1);
    String fullImagePath = tsxFolder + imagePath;

    Image sheet = new Image(new FileInputStream(fullImagePath));
    int sheetCols = (int) ((sheet.getWidth() - 2 * margin + spacing) / (tileWidth + spacing));
    int sheetRows = (int) ((sheet.getHeight() - 2 * margin + spacing) / (tileHeight + spacing));

    return new TilesetInfo(sheet, tileWidth, tileHeight, margin, spacing, sheetCols, sheetRows);
  }

  // Resolve a relative path referenced by the tmx file. tmxPath is the full path
  // to the tmx file.
  private static String resolveRelativePath(String tmxPath, String relative) {
    int idx = tmxPath.lastIndexOf('/');
    if (idx < 0)
      idx = tmxPath.lastIndexOf('\\');
    if (idx < 0)
      return relative;
    String base = tmxPath.substring(0, idx + 1);
    return base + relative;
  }
}
