package Model.Request;

import Model.Message;

public class NewConversationRequest extends GeneralRequest {
    private static final long serialVersionUID = 10L;
    private Message message;

    public Message getMessage() {
        return message;
    }

    public NewConversationRequest(Message message) {
        this.message = message;
    }
}
