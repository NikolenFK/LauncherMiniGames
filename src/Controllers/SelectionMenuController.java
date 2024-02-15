package Controllers;

import MainFiles.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the Selection Menu.
 */
public class SelectionMenuController {

    @FXML
    public Button snakeGameButton;
    @FXML
    private Button minesweeperButton;

    private final StageController stageController = StageController.getInstance();

    /**
     * Initialization of buttons and their actions.
     */
    @FXML
    private void initialize() {
        setupButtonAction(snakeGameButton, "/FXML_Files/SnakeGame.fxml", "Snake");
        setupButtonAction(minesweeperButton, "/FXML_Files/Minesweeper.fxml", "Minesweeper");
    }

    /**
     * Sets up the action for the given button.
     *
     * @param button         The button for which the action is set.
     * @param pathToNewScene The path to the new scene's FXML file.
     * @param sceneTitle     Scene title.
     */
    private void setupButtonAction(Button button, String pathToNewScene, String sceneTitle) {
        button.setOnAction(actionEvent -> stageController.openNewScene(pathToNewScene, sceneTitle));
    }
}
