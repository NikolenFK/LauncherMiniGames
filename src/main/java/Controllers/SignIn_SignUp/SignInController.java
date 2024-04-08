package Controllers.SignIn_SignUp;

import MainFiles.SceneConstants;
import MainFiles.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;

public class SignInController {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/MainFiles/Users.db");

            if (connection != null)
                System.out.println("Connected");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        signUpButton.setOnAction(actionEvent -> goToSignUp());
    }

    private void goToSignUp() {
        StageController.getInstance().openNewScene(SceneConstants.SignUp);
    }

    private void trySignIn() {

    }

}
