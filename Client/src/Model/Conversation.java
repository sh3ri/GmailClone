package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Conversation implements java.io.Serializable{
    private static final long serialVersionUID = 12L;
    private ArrayList<Message> messages;
    private boolean read;
    public Conversation(Message startingMessage){
        messages = new ArrayList<>(Collections.singletonList(startingMessage));
    }

    public Conversation(Message startingMessage, boolean read){
        messages = new ArrayList<>(Collections.singletonList(startingMessage));
        this.read = read;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString(){
        String lastMessage = messages.get(messages.size() - 1).getText();
        if(lastMessage.length() < 50)
            return lastMessage;
        else
            return lastMessage.substring(0, 47) + "...";
    }

    public Message getFirstMessage(){
        return messages.get(0);
    }

    public Message getLastMessage(){
        return messages.get(messages.size() - 1);
    }

}
