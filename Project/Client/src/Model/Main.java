package Model;

import Controller.MainPageController;
import Model.Request.ClientClosed;
import Model.Request.GetArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    public static Connection connection;
    public static User currentUser;
    public static MainPageController mainControllerInstance;
    public static Main mainInstance;
    @Override
    public void start(Stage primaryStage) throws Exception {
        PageLoader.initStage(primaryStage);
        mainInstance = this;
        new PageLoader().load("/View/Connect.fxml");
    }


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (connection != null)
                    connection.request(new ClientClosed());
            }
        }));
        launch(args);
    }
}
