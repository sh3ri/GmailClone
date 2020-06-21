package Controller;

import Model.Main;
import Model.PageLoader;
import Model.Request.SignInRequest;
import Model.Response.Response;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {
    @FXML private TextField username_tf;
    @FXML private PasswordField password_pf;

    public void initialize() {
    }

    public void signup() {
        new PageLoader().load("/View/SignUp.fxml");
    }

    public void signin() throws IOException {
        Main.connection.request(new SignInRequest(new User(username_tf.getText(), password_pf.getText())));
        Response response = (Response) Main.connection.getResponse();
        if (response.equals(Response.SigninSuccsess)) {
            Main.currentUser = new User(username_tf.getText(), password_pf.getText());
            Main.mainControllerInstance = new MainPageController(Main.currentUser, true);
            PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance));
//            new PageLoader().load("/View/mainPage.fxml");
        }
        else
            System.out.println("signin unsuccessful");
    }
}
