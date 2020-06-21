package Controller;

import Model.Main;
import Model.Message;
import Model.PageLoader;
import Model.Request.NewConversationRequest;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class ComposeController {

    public TextField receiver_tf;

    public TextField title_tf;
    public TextArea text_ta;
    public Label fileName_lbl;
    private File attachedFile;

    public void attach(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        attachedFile = fileChooser.showOpenDialog(PageLoader.getPrimaryStage());
        if (attachedFile != null)
            fileName_lbl.setText(attachedFile.getName());
    }

    public void send(ActionEvent actionEvent) throws IOException {
//        System.out.println("sendin it");
//        new Thread(() -> {
//            Thread sendEmailThread = new Thread(() -> {
//
                // the message is now in draft
                try {
                    if (attachedFile != null)
                        Main.connection.request(new NewConversationRequest(new Message(Main.currentUser.getUsername(), receiver_tf.getText()
                                ,text_ta.getText(), Files.readAllBytes(Paths.get(attachedFile.toURI())),
                                LocalDate.now(), LocalTime.now())));
                    else {
                       Message newMessage = new Message(Main.currentUser.getUsername(), receiver_tf.getText()
                                ,text_ta.getText(), null,
                                LocalDate.now(), LocalTime.now());
                        System.out.println("sending the message " + newMessage.getText() + " to the server");
                        Main.connection.request(new NewConversationRequest(newMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            });
//            sendEmailThread.start();
//            try {
//                sendEmailThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//             the message is now out of draft
//        }
//        ).start();
        goBack();
    }

    public void goBack() throws IOException {
        System.out.println("yo what the fuck");
//        new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance);
//        new PageLoader().load("/View/mainPage.fxml");
        Main.mainControllerInstance = new MainPageController(Main.currentUser, true);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance));
    }
}
