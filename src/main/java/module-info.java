module your.unique.module.name {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens MainFiles to javafx.fxml;
    opens Controllers to javafx.fxml;
    opens Controllers.SignIn_SignUp to javafx.fxml;

    exports MainFiles;
    exports Controllers;
    exports Controllers.SignIn_SignUp;
}
