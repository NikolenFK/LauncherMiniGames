package Games.SnakeGame;

import MainFiles.StageController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the Snake game logic
 */
public class Snake {

    private final double HEIGHT;
    private final double WIDTH;
    private final int ROWS;
    private final int COLUMNS;
    private final double SQUARE_SIZE;
    private static final String[] FOODS_IMAGE = {"Img/apple.png", "Img/banana.png", "Img/orange.png"};
    private static final int INITIAL_SPEED = 130;
    private static final int SPEED_INCREASE_AMOUNT = 5;

    private int currentSpeed = INITIAL_SPEED;
    private Timeline timeline;
    private final GraphicsContext graphicsContext;
    private final Label scoreLabel;
    private Direction direction = Direction.DOWN;
    private Direction pendingDirection = direction;
    private final ArrayList<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;
    private int foodX;
    private int foodY;
    private Image foodImage;
    private int score = 0;
    private boolean isGameOver = false;
    private final Random random = new Random();

    /**
     * Initializes a new instance of the Snake class
     *
     * @param height          The height of the game window
     * @param width           The width of the game window
     * @param rows            The number of rows in the game grid
     * @param columns         The number of columns in the game grid
     * @param graphicsContext The GraphicsContext for drawing on the game canvas
     * @param scoreLabel      The label to display the game score
     */
    public Snake(double height, double width, int rows, int columns, GraphicsContext graphicsContext, Label scoreLabel) {
        this.HEIGHT = height;
        this.WIDTH = width;
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.graphicsContext = graphicsContext;
        this.scoreLabel = scoreLabel;
        this.SQUARE_SIZE = width / rows;

        initializeKeyPressHandler();
    }

    /**
     * Starts the Snake game
     */
    public void start() {
        initializeSnake();
        spawnFood();
        startTimeline();
    }

    /**
     * Initializes the snake with a default size and position
     */
    private void initializeSnake() {
        for (int i = 0; i < 3; i++)
            snakeBody.add(new Point(ROWS / 2, COLUMNS / 2));

        snakeHead = snakeBody.get(0);
    }

    /**
     * Starts the game timeline for continuous updates
     */
    private void startTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(currentSpeed), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Initializes the key press handler for controlling the snake
     */
    private void initializeKeyPressHandler() {
        Platform.runLater(() -> StageController.getInstance().getScene().setOnKeyPressed(this::handleKeyPress));
    }

    /**
     * Handles the key press events for changing the snake direction
     *
     * @param keyEvent The KeyEvent representing the key press
     */
    private void handleKeyPress(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.RIGHT || code == KeyCode.D) pendingDirection = Direction.RIGHT;
        else if (code == KeyCode.LEFT || code == KeyCode.A) pendingDirection = Direction.LEFT;
        else if (code == KeyCode.UP || code == KeyCode.W) pendingDirection = Direction.UP;
        else if (code == KeyCode.DOWN || code == KeyCode.S) pendingDirection = Direction.DOWN;
        else if (code == KeyCode.R) restart();
    }

    /**
     * Sets the new direction for the snake, avoiding opposite directions
     *
     * @param newDirection The new direction to set
     */
    private void setDirection(Direction newDirection) {
        if (direction != newDirection.opposite())
            direction = newDirection;
    }

    /**
     * MainFiles.Main game loop where the snake moves, checks for collisions, and updates the game state
     */
    private void run() {
        if (isGameOver) {
            displayGameOver();
            return;
        }

        setDirection(pendingDirection);

        drawBackground();
        drawFood();
        drawSnake();

        updateSnakeBody();
        moveSnake();

        checkIsGameOver();
        eatFood();
    }

    /**
     * Updates the snake body by shifting its segments
     */
    private void updateSnakeBody() {
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
    }

    /**
     * Moves the snake head based on the current direction
     */
    private void moveSnake() {
        switch (direction) {
            case RIGHT -> snakeHead.x++;
            case LEFT -> snakeHead.x--;
            case UP -> snakeHead.y--;
            case DOWN -> snakeHead.y++;
        }
    }

    /**
     * Draws the background of the game grid
     */
    private void drawBackground() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Color color = (i + j) % 2 == 0 ? Color.web("#AAD751") : Color.web("#A2D149");
                graphicsContext.setFill(color);
                graphicsContext.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    /**
     * Draws the snake on the game canvas
     */
    private void drawSnake() {
        drawSnakeSegment(snakeHead, 30);
        for (int i = 1; i < snakeBody.size(); i++) {
            drawSnakeSegment(snakeBody.get(i), 15);
        }
    }

    /**
     * Draws a snake segment on the game canvas with rounded corners
     *
     * @param point The position of the snake segment
     * @param size  The size of the snake segment
     */
    private void drawSnakeSegment(Point point, double size) {
        graphicsContext.setFill(Color.web("#558B2F"));
        graphicsContext.fillRoundRect(point.getX() * SQUARE_SIZE, point.getY() * SQUARE_SIZE,
                SQUARE_SIZE - 1, SQUARE_SIZE - 1, size, size);
    }

    /**
     * Draws the food on the game canvas
     */
    private void drawFood() {
        graphicsContext.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Handles the logic when the snake eats the food
     */
    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            score++;
            increaseSpeed();
            spawnFood();
            updateLabel();
        }
    }

    /**
     * Increases the game speed when the snake eats food
     */
    private void increaseSpeed() {
        if (score % 3 == 0) {
            currentSpeed -= SPEED_INCREASE_AMOUNT;
            timeline.stop();
            startTimeline();
        }
    }

    /**
     * Checks if the game is over due to collisions or reaching the boundaries
     */
    private void checkIsGameOver() {
        if (isSnakeOutOfBounds() || isSnakeColliding()) {
            isGameOver = true;
        }
    }

    /**
     * Checks if the snake is out of bounds
     *
     * @return True if the snake is out of bounds, false otherwise
     */
    private boolean isSnakeOutOfBounds() {
        return snakeHead.getX() < 0 || snakeHead.getY() < 0 ||
                snakeHead.getX() * SQUARE_SIZE >= WIDTH || snakeHead.getY() * SQUARE_SIZE >= HEIGHT;
    }

    /**
     * Checks if the snake is colliding with itself
     *
     * @return True if the snake is colliding with itself, false otherwise
     */
    private boolean isSnakeColliding() {
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.getX() == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Spawns the food at a random location on the game grid
     */
    private void spawnFood() {
        do {
            foodX = random.nextInt(ROWS);
            foodY = random.nextInt(COLUMNS);
        } while (!isFoodLocationValid());

        String randomFoodImage = FOODS_IMAGE[random.nextInt(FOODS_IMAGE.length)];
        foodImage = new Image(Objects.requireNonNull(getClass().getResource(randomFoodImage)).toExternalForm());
    }

    /**
     * Checks if the food location is valid and not on the snake
     *
     * @return True if the food location is valid, false otherwise
     */
    private boolean isFoodLocationValid() {
        for (Point point : snakeBody) {
            if (point.getX() == foodX && point.getY() == foodY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the score label with the current score
     */
    private void updateLabel() {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Displays the game over message and score on the canvas
     */
    private void displayGameOver() {
        graphicsContext.setFill(Color.RED);
        graphicsContext.setFont(new Font("Consolas", 70));
        graphicsContext.fillText("Game Over", WIDTH / 4, HEIGHT / 2);
        graphicsContext.setFill(Color.web("#f7f7f7"));
        graphicsContext.setFont(new Font("Consolas", 40));
        graphicsContext.fillText("Score: " + score, WIDTH / 2.6, HEIGHT / 1.6);
    }

    /**
     * Restarts the Snake game by stopping the timeline, resetting the score, and starting a new game
     */
    public void restart() {
        timeline.stop();
        score = 0;
        updateLabel();
        snakeBody.clear();
        isGameOver = false;
        start();
    }
}
