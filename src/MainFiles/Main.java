package MainFiles;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        StageController stageController = StageController.getInstance();
        stageController.openNewScene("/FXML_Files/SelectionMenu.fxml", "Selection Menu");
    }
}