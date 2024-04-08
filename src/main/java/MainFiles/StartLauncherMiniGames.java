package MainFiles;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartLauncherMiniGames extends Application {
    protected static void Start(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        StageController stageController = StageController.getInstance();
        stageController.openNewScene(SceneConstants.SelectionMenu);
    }
}