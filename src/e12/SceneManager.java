import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneManager {

  private Scene scene;

  // Called once after the Scene is created
  public void setScene(Scene scene) {
    this.scene = scene;
  }

  // Screen factory methods (create new screens as needed)
  public Parent createMenuView() {
    return new MenuView(this);
  }

  public Parent createGameView() {
    return new GameView(this);
  }

  // Public navigation methods:

  public void showMainMenu() {
    ensureSceneInitialized();
    scene.setRoot(createMenuView());
  }

  public void showGame() {
    ensureSceneInitialized();
    scene.setRoot(createGameView());
  }

  private void ensureSceneInitialized() {
    if (scene == null) {
      throw new IllegalStateException("Scene has not been set on SceneManager.");
    }
  }
}
