package Controllers;

import MainFiles.SceneConstants;
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
        setupButtonAction(snakeGameButton, SceneConstants.SnakeGame);
        setupButtonAction(minesweeperButton, SceneConstants.Minesweeper);
    }

    /**
     * Sets up the action for the given button.
     *
     * @param button         The button for which the action is set.
     * @param sceneConstants Defining the scene to open, including its title and FXML file path.
     */
    private void setupButtonAction(Button button, SceneConstants sceneConstants) {
        button.setOnAction(actionEvent -> stageController.openNewScene(sceneConstants));
    }
}
