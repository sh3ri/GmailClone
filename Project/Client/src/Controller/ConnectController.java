package Controller;

import Model.Connection;
import Model.Main;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ConnectController {

    public Label error_lbl;
    public TextField ip_tf;

    public void connect(ActionEvent actionEvent) {
        try{
            Main.connection = new Connection(ip_tf.getText());
            PageLoader.resizeStage();
            new PageLoader().load("/View/SignIn.fxml");
        }catch(IOException e){
            error_lbl.setText("Couldn't connect to the server");
        }
    }
}
