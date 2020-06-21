package Model.Request;

import Model.Conversation;
import Model.Message;

public class ReplyRequest extends GeneralRequest {
    private static final long serialVersionUID = 12L;
    private final Conversation convo;
    private final Message message;
    public ReplyRequest(Conversation convo, Message message) {
        this.convo = convo;
        this.message = message;
    }

    public Conversation getConvo() {
        return convo;
    }

    public Message getMessage() {
        return message;
    }
}
