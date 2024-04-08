package MainFiles;

public enum SceneConstants {
    SignIn("Authorization", "/FXML_Files/SignIn_SignUp/SignIn.fxml"),
    SignUp("Registration", "/FXML_Files/SignIn_SignUp/SignUp.fxml"),
    SelectionMenu("Selection Menu", "/FXML_Files/SelectionMenu.fxml"),
    SnakeGame("Snake", "/FXML_Files/SnakeGame.fxml"),
    Minesweeper("Minesweeper", "/FXML_Files/Minesweeper.fxml");

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
