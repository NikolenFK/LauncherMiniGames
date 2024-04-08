package MainFiles;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * A singleton controller for managing JavaFX stages and scene transitions.
 */
public class StageController {

    private static StageController instance;
    private final Stage stage;
    private Scene scene;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private StageController() {
        stage = new Stage();
        stage.setResizable(false);
    }

    /**
     * Retrieves the instance of the StageController, creating a new one if it doesn't exist.
     *
     * @return The instance of StageController.
     */
    public static synchronized StageController getInstance() {
        if (instance == null)
            instance = new StageController();
        return instance;
    }

    /**
     * Gets the current scene of the stage.
     *
     * @return The current Scene object.
     */
    public Scene getScene() {
        return this.scene;
    }

    /***
     * Opens a new scene with a fade-in animation.
     * @param sceneConstants Defining the scene to open, including its title and FXML file path.
     */
    public void openNewScene(SceneConstants sceneConstants) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sceneConstants.getPath())));
            root.setOpacity(0);

            scene = new Scene(root);

            stage.setTitle(sceneConstants.getTitleName());
            stage.setScene(scene);

            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE)
                    Platform.exit();
            });

            Platform.runLater(this::centerStageOnScreen);

            scene.widthProperty().addListener((observable, oldValue, newValue) -> centerStageOnScreen());
            scene.heightProperty().addListener((observable, oldValue, newValue) -> centerStageOnScreen());

            animationBeforeNewScene(root);
        } catch (Exception e) {
            System.out.println("Oops! Something's wrong) " + e.getMessage());
        }
    }

    /**
     * Centers the stage on the screen.
     */
    private void centerStageOnScreen() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        if (stage.getWidth() <= 0 || stage.getHeight() <= 0) {
            stage.setWidth(screenBounds.getWidth() / 2);
            stage.setHeight(screenBounds.getHeight() / 2);
        }

        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * Performs a fade-in animation on the given root before showing the stage.
     *
     * @param root The root Parent of the scene.
     */
    private void animationBeforeNewScene(Parent root) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        stage.show();
        RequestFocus();
    }

    /**
     * Requests focus for the root node of the current scene.
     * This method ensures that the root node receives keyboard focus.
     */
    public void RequestFocus() {
        getScene().getRoot().requestFocus();
    }
}
