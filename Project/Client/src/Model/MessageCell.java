package Model;

import Controller.SingleMessageController;

import java.io.IOException;

public class MessageCell extends javafx.scene.control.ListCell<Message> {

    @Override
    public void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null)
            return;
        setStyle("-fx-background-color:#ffffff");
        try {
            setGraphic(new SingleMessageController(item).init());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
