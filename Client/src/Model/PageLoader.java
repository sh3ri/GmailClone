package Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PageLoader {
    private static Stage primaryStage;
    private static final int WINDOW_WIDTH = 550;
    private static final int WINDOW_HEIGHT = 800;
    public static Parent mainPageRoot;

    public static void initStage(Stage primaryStage) {
        PageLoader.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setWidth(450.0);
        primaryStage.setHeight(250.0);
        primaryStage.setTitle("Gmail");
        primaryStage.show();
    }

    public static void resizeStage(){
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
    }


    public void load(String url) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(url));
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Parent load(String url, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        fxmlLoader.setController(controller);
        if(url.equals("/View/mainPage.fxml"))
            return mainPageRoot = fxmlLoader.load();
        return fxmlLoader.load();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
