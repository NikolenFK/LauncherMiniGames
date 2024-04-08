package Games.SnakeGame;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case RIGHT -> LEFT;
            case LEFT -> RIGHT;
        };
    }

}
