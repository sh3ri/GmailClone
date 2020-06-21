package Model.Request;

import Model.Conversation;
import Model.User;

public class DeleteConvoForUserRequest extends GeneralRequest {
    private static final long serialVersionUID = 13L;
    private User user;
    private Conversation conversation;

    public User getUser() {
        return user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public DeleteConvoForUserRequest(User user, Conversation conversation) {
        this.user = user;
        this.conversation = conversation;
    }
}
