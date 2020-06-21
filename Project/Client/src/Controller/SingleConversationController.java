package Controller;

import Model.Conversation;
import Model.Main;
import Model.PageLoader;
import Model.Request.DeleteConvoForUserRequest;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SingleConversationController {
    public AnchorPane root;
    public Label sender_lbl;
    public Label text_lbl;
    public Label dateTime_lbl;
    private Conversation conversation;
    public SingleConversationController(Conversation item) throws IOException {
        this.conversation = item;
        new PageLoader().load("/View/singleConversation.fxml", this);
    }

    public AnchorPane init(){
//        sender_lbl.setText(conversation.getMessages().get(conversation.getMessages().size() - 1).getSender());
        if(conversation.getFirstMessage().getSender().equals(Main.currentUser))
            sender_lbl.setText(conversation.getFirstMessage().getReceiver());
        else
            sender_lbl.setText(conversation.getFirstMessage().getSender());
        text_lbl.setText(conversation.toString());
        String time = conversation.getMessages().get(conversation.getMessages().size() - 1).getTime().toString();
        if(time.contains("."))
            time = time.substring(0, time.indexOf("."));
        dateTime_lbl.setText("at " + time + " " +
                conversation.getMessages().get(conversation.getMessages().size() -1).getDate().toString());
        return root;
    }

    public void deleteConvo(ActionEvent actionEvent) throws IOException {
//        System.out.println("sum1: " + Main.currentUser.getInbox().size() + " + " + Main.currentUser.getOutbox().size());
        Main.connection.request(new DeleteConvoForUserRequest(Main.currentUser, conversation));
//        System.out.println("sum2: " + Main.currentUser.getInbox().size() + " + " + Main.currentUser.getOutbox().size());
        MainPageController controller = new MainPageController(Main.currentUser, true);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/mainPage.fxml", controller));
    }

    public void open(ActionEvent actionEvent) throws IOException {
        conversation.setRead(true);
        ConversationPageController controller = new ConversationPageController(conversation);
        PageLoader.getPrimaryStage().getScene().setRoot(new PageLoader().load("/View/ConversationPage.fxml", controller));
    }
}
