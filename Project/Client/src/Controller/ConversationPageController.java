package Controller;

import Model.*;
import Model.Request.BlockUserRequest;
import Model.Request.ReplyRequest;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import jdk.nashorn.internal.ir.Block;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConversationPageController {
    public ListView<Message> convo_lv;
    public Label convo_lbl;
    public TextArea replyText_ta;
    public Button reply_btn;
    private Conversation convo;
    public Label fileName_lbl;
    private File attachedFile;

    public ConversationPageController(Conversation convo) {
        this.convo = convo;
    }

    public void initialize() {
        if (Main.currentUser.getUsername().equals(convo.getLastMessage().getSender())) {
            reply_btn.setDisable(true);
            replyText_ta.setDisable(true);
        }
        convo_lv.setItems(FXCollections.observableArrayList(convo.getMessages()));
        convo_lv.setCellFactory(convo_lv -> new MessageCell());
        if (convo.getFirstMessage().getSender().equals(Main.currentUser.getUsername()))
            convo_lbl.setText(convo.getFirstMessage().getReceiver());
        else
            convo_lbl.setText(convo.getFirstMessage().getSender());
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Main.mainControllerInstance = new MainPageController(Main.currentUser, true);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", Main.mainControllerInstance));
    }

    public void block(ActionEvent actionEvent) {
        User otherUser = null;
        if(convo.getFirstMessage().getSender().equals(Main.currentUser.getUsername()))
            otherUser = new User(convo.getFirstMessage().getReceiver(), null, null, null, null, null);
        else
            otherUser = new User(convo.getFirstMessage().getSender(), null, null, null, null, null);
        Main.connection.request(new BlockUserRequest(Main.currentUser, otherUser));
        System.out.println(Main.currentUser.getUsername() +  " blocked " + otherUser.getUsername());
    }

    public void attachFile(){
        attachedFile = new FileChooser().showOpenDialog(PageLoader.getPrimaryStage());
        if(attachedFile != null)
            fileName_lbl.setText(attachedFile.getName());
    }

    public void reply(ActionEvent actionEvent) throws IOException {
        if (attachedFile == null)
            Main.connection.request(new ReplyRequest(convo, new Message(Main.currentUser.getUsername(),
                    convo.getLastMessage().getSender(), replyText_ta.getText(), null, LocalDate.now(), LocalTime.now())));
        else
            new Thread(() ->
            {
                try {
                    Main.connection.request(new ReplyRequest(convo, new Message(Main.currentUser.getUsername(),
                            convo.getLastMessage().getSender(), replyText_ta.getText(), Files.readAllBytes(Paths.get(attachedFile.toURI())), LocalDate.now(), LocalTime.now())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        goBack(actionEvent);
    }
}
