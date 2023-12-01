package MainFiles;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * A singleton controller for managing JavaFX stages and scene transitions
 */
public class StageController {

    private static StageController instance;
    private final Stage stage;
    private Scene scene;

    /**
     * Private constructor to enforce singleton pattern
     */
    private StageController() {
        stage = new Stage();
    }

    /**
     * Retrieves the instance of the StageController, creating a new one if it doesn't exist
     *
     * @return The instance of StageController
     */
    public static synchronized StageController getInstance() {
        if (instance == null)
            instance = new StageController();
        return instance;
    }

    /**
     * Gets the current scene of the stage
     *
     * @return The current Scene object
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Opens a new scene with a fade-in animation
     *
     * @param pathToNewScene The path to the FXML file of the new scene
     * @param sceneTitle     The title of the new scene
     */
    public void openNewScene(String pathToNewScene, String sceneTitle) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(pathToNewScene)));
            root.setOpacity(0);

            scene = new Scene(root);

            stage.setTitle(sceneTitle);
            stage.setScene(scene);

            animationBeforeNewScene(root);
        } catch (Exception e) {
            System.out.println("Oops! Something's wrong)");
        }
    }

    /**
     * Performs a fade-in animation on the given root before showing the stage
     *
     * @param root The root Parent of the scene
     */
    private void animationBeforeNewScene(Parent root) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        stage.show();
    }
}
