package Model;

import Controller.SingleConversationController;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ConversationCell extends ListCell<Conversation> {
    @Override
    protected void updateItem(Conversation item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null){
            if(!item.isRead())
                setStyle("-fx-background-color:#fff");
            else
                setStyle("-fx-background-color:ddd");
            try {
                setGraphic(new SingleConversationController(item).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
