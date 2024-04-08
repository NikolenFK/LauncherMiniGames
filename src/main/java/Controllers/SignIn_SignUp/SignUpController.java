package Controllers.SignIn_SignUp;

import MainFiles.SceneConstants;
import MainFiles.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField emailTextField;

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
        signInButton.setOnAction(actionEvent -> goToSignIn());
    }

    private void goToSignIn(){
        StageController.getInstance().openNewScene(SceneConstants.SignIn);
    }

    private void tryToSignUp(){

    }

}
