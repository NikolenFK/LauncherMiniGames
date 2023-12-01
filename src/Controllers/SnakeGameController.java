package Controllers;

import Games.SnakeGame.Snake;
import MainFiles.StageController;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the Snake Game
 */
public class SnakeGameController {

    @FXML
    private Button backToSelectionMenuButton;

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Button restartButton;

    @FXML
    private Label scoreLabel;

    private GraphicsContext graphicsContext;

    private final int ROWS = 20;

    private final int COLUMNS = 20;

    /**
     * Initializes the SnakeGameController
     */
    @FXML
    private void initialize() {
        backToSelectionMenuButton.setOnAction(actionEvent -> returnToSelectionMenu());
        graphicsContext = gameCanvas.getGraphicsContext2D();
        startGame();
    }

    /**
     * Handles the action event to return to the selection menu
     */
    private void returnToSelectionMenu() {
        StageController.getInstance().openNewScene("/FXML_Files/SelectionMenu.fxml", "Selection Menu");
    }

    /**
     * Initializes and starts the Snake game
     */
    private void startGame() {
        Snake snake = new Snake(gameCanvas.getHeight(), gameCanvas.getWidth(), ROWS, COLUMNS, graphicsContext, scoreLabel);
        restartButton.setOnAction(actionEvent -> snake.restart());
        snake.start();
    }
}
