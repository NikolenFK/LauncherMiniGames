package Controllers;

import Games.SnakeGame.Snake;
import MainFiles.SceneConstants;
import MainFiles.StageController;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * Controller for the Snake Game.
 */
public class SnakeGameController {

    @FXML
    private Button backToSelectionMenuButton;

    @FXML
    private ChoiceBox<String> fieldSizeChoiceBox;

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Button restartButton;

    @FXML
    private Label scoreLabel;

    private GraphicsContext graphicsContext;

    /**
     * Initializes the SnakeGameController.
     */
    @FXML
    private void initialize() {
        fieldSizeChoiceBox.getItems().addAll("10x10", "15x15", "20x20", "25x25");
        fieldSizeChoiceBox.setValue(fieldSizeChoiceBox.getItems().get(1));

        backToSelectionMenuButton.setOnAction(actionEvent -> returnToSelectionMenu());
        graphicsContext = gameCanvas.getGraphicsContext2D();
        startGame();
    }

    /**
     * Handles the action event to return to the selection menu.
     */
    private void returnToSelectionMenu() {
        StageController.getInstance().openNewScene(SceneConstants.SelectionMenu);
    }

    /**
     * Initializes and starts the Snake game.
     */
    private void startGame() {
        Snake snake = new Snake(gameCanvas.getHeight(), gameCanvas.getWidth(), graphicsContext, scoreLabel, fieldSizeChoiceBox);
        restartButton.setOnAction(actionEvent -> snake.restart());
        snake.start();
    }

}