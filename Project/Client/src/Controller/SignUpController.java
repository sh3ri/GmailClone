package Controller;

import Model.Main;
import Model.PageLoader;
import Model.Request.SignUpRequest;
import Model.Response.Response;
import Model.User;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {
    public TextField username_tf;
    public PasswordField password_pf;
    public PasswordField confirm_pf;
    public DatePicker datePicker_dp;
    public Label result_lbl;


    public void signup() {
        String validation = validateFields();
        if(!validation.equals("Success")){
            result_lbl.setText(validation);
            return;
        }
        Main.currentUser = new User(username_tf.getText(), password_pf.getText(), datePicker_dp.getValue());
        Main.connection.request(new SignUpRequest(Main.currentUser));
        Response response = (Response) Main.connection.getResponse();
        if (response.equals(Response.SignupSuccess)) {
            new PageLoader().load("/View/additionalInformation.fxml");
        } else
            result_lbl.setText("Choose Another username that is unique");
    }

    private String validateFields() {
        if(!(password_pf.getText().equals(confirm_pf.getText())))
            return "Passwords do not match!";
        if(!password_pf.getText().matches("^[A-Za-z0-9]{8,}$"))
            return "Password is not valid";
        if(datePicker_dp.getValue().getYear() > 2006)
            return "You're too young to be here!";
        if(!username_tf.getText().matches("^[a-zA-Z0-9.]*$"))
            return "Username is not valid";
        return "Success";
    }

    public void back(ActionEvent actionEvent) {
        new PageLoader().load("/View/SignIn.fxml");
    }
}
