package MainFiles;

public enum SceneConstants {
    SignIn("Authorization", "/FXML/SignIn_SignUp/SignIn.fxml"),
    SignUp("Registration", "/FXML/SignIn_SignUp/SignUp.fxml"),
    SelectionMenu("Selection Menu", "/FXML/SelectionMenu.fxml"),
    SnakeGame("Snake", "/FXML/Games/SnakeGame.fxml"),
    Minesweeper("Minesweeper", "/FXML/Games/Minesweeper.fxml");

    private final String name;
    private final String path;

    SceneConstants(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getTitleName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
