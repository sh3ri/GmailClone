package Server;

import Model.Conversation;
import Model.Message;
import Model.Request.*;
import Model.Response.Response;
import Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ServerHandler {
    public static ArrayList<User> users = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    ServerHandler(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }


    void handle(GeneralRequest request) throws IOException {
        if (request instanceof SignUpRequest) {
            System.out.println("received sign up request at " + LocalDate.now() + " " + LocalTime.now());
            boolean duplicate = false;
            for (User u : users)
                if (u.getUsername().equals(((SignUpRequest) request).getUser().getUsername())) {
                    duplicate = true;
                    break;
                }
            if (!duplicate) {
                users.add(((SignUpRequest) request).getUser());
                outputStream.writeObject(Response.SignupSuccess);
                Server.saveFiles();
            } else {
                System.out.println("duplicate user was not created");
                outputStream.writeObject(Response.SignupFailure);
            }
        } else if (request instanceof SignInRequest) {
            System.out.println("received sign in request for: " + ((SignInRequest) request).getUser().getUsername() + " " +
                    ((SignInRequest) request).getUser().getPassword());
            boolean userExists = false;
            User foundUser = null;
            for (User u : users)
                if (u.getUsername().equals(((SignInRequest) request).getUser().getUsername())
                        && u.getPassword().equals(((SignInRequest) request).getUser().getPassword())) {
                    userExists = true;
                    foundUser = u;
                }
            if (userExists) {
                outputStream.writeObject(Response.SigninSuccsess);
                System.out.println("sign in success at " + LocalDate.now() + " "  + LocalTime.now());
            } else {
                outputStream.writeObject(Response.SigninFailure);
                System.out.println("sign in failure at " + LocalDate.now() + " "  + LocalTime.now());
            }
        } else if (request instanceof ClientClosed) {
            //TODO
        } else if (request instanceof UpdateUserRequest) {
            for (User u : users)
                if (((UpdateUserRequest) request).getUser().getUsername().equals(u.getUsername())) {
                    u.setInfo(((UpdateUserRequest) request).getUser());
                    Server.saveFiles();
                }
        } else if (request instanceof GetUserInformationRequest) {
//            System.out.println("trying to find the full info of " + ((GetUserInformationRequest) request).getUser().getUsername());
            for (User u : users)
                if (u.getUsername().equals(((GetUserInformationRequest) request).getUser().getUsername())) {
                    outputStream.reset();
                    outputStream.writeObject(u);
                }
        } else if (request instanceof NewConversationRequest) {
            System.out.println("Received new conversation request at " + LocalDate.now() + " " + LocalTime.now());
            System.out.println(((NewConversationRequest) request).getMessage().getSender() + " sending new email to " +
                    ((NewConversationRequest) request).getMessage().getReceiver());
            User receiver = getUserByName(((NewConversationRequest) request).getMessage().getReceiver());
            User sender = getUserByName(((NewConversationRequest) request).getMessage().getSender());
            if (receiver != null) {
                for(User u: receiver.getBlockedUsers())
                    if(u.getUsername().equals(sender.getUsername()))
                        return ;
                //add to receiver's convos
                receiver.getInbox().add(0, new Conversation(((NewConversationRequest) request).getMessage(), false));
                //add to sender's convos
                sender.getOutbox().add(0, new Conversation(((NewConversationRequest) request).getMessage(), true));
            } else {
                Conversation convo = new Conversation(((NewConversationRequest) request).getMessage(), false);
                convo.getMessages().add(new Message("maildeliverydaemon@googlemail.com", sender.getUsername(),
                        "We couldn't find the user you were trying to send an email to.\n\t-Gmail Team",
                        null, LocalDate.now(), LocalTime.now()));
                sender.getOutbox().add(0, convo);
                sender.getInbox().add(0, convo);
            }
        } else if (request instanceof ProfilePictureRequest) {
            User u = getUserByName(((ProfilePictureRequest) request).getUser());
            if (u != null)
                outputStream.writeObject(u.getProfilePicture());
            else
                outputStream.writeObject(null);
        } else if (request instanceof ReplyRequest) {
            System.out.println(((ReplyRequest) request).getMessage().getSender() + " replying to "
                    + ((ReplyRequest) request).getMessage().getReceiver() + " at " + LocalDate.now() + " " + LocalTime.now());
            User sender = getUserByName(((ReplyRequest) request).getMessage().getSender());
            User receiver = getUserByName(((ReplyRequest) request).getMessage().getReceiver());
            if(sender == null || receiver == null)
                return ;
            for(User u: receiver.getBlockedUsers())
                if(u.getUsername().equals(sender.getUsername()))
                    return ;
            //put the convo in the beginning of receiver's inbox and add the message
            sendMessage(receiver.getInbox(), receiver, false, request, true);
            sendMessage(receiver.getOutbox(), receiver, false, request, false);
            //put the convo in the receiver's sent box
            sendMessage(sender.getInbox(), sender, true, request, false);
            sendMessage(sender.getOutbox(), sender, true, request, true);
        } else if (request instanceof DeleteConvoForUserRequest) {
            System.out.println("removing conversation between " +
                    ((DeleteConvoForUserRequest) request).getConversation().getFirstMessage().getSender() +
                    " and " + ((DeleteConvoForUserRequest) request).getConversation().getFirstMessage().getReceiver() +
                    " at " + LocalDate.now() + " " + LocalTime.now());
            User sender = getUserByName(((DeleteConvoForUserRequest) request).getConversation().getFirstMessage().getSender());
            User receiver = getUserByName(((DeleteConvoForUserRequest) request).getConversation().getFirstMessage().getReceiver());
            if(sender != null){
                sender.getOutbox().remove(((DeleteConvoForUserRequest) request).getConversation());
                sender.getInbox().remove(((DeleteConvoForUserRequest) request).getConversation());
            }
            if(receiver != null){
                receiver.getOutbox().remove(((DeleteConvoForUserRequest) request).getConversation());
                receiver.getInbox().remove(((DeleteConvoForUserRequest) request).getConversation());
            }
        }
        else if(request instanceof BlockUserRequest){
            System.out.println(((BlockUserRequest) request).getBlocker().getUsername() + " blocked " +
                    ((BlockUserRequest) request).getBlocked().getUsername() + " at " + LocalDate.now() + " " + LocalTime.now());
            User blocker = getUserByName(((BlockUserRequest) request).getBlocker().getUsername());
            User blocked = getUserByName(((BlockUserRequest) request).getBlocked().getUsername());
            if(blocker != null && blocked != null)
                blocker.getBlockedUsers().add(blocked);
        }
    }

    private static void sendMessage(ArrayList<Conversation> box, User person, boolean read, GeneralRequest request, boolean add) {
//        assertFalse(person == null);
        for (int i = 0; i < box.size(); i++) {
            Conversation convo = box.get(i);
            if (convo.getFirstMessage().getDate().equals(((ReplyRequest) request).getConvo().getFirstMessage().getDate())
                    && convo.getFirstMessage().getTime().equals(((ReplyRequest) request).getConvo().getFirstMessage().getTime())) {
                add = false;
                box.remove(convo);
                convo.setRead(read);
                convo = convo.getSameConvo();
                Message newMessage = ((ReplyRequest) request).getMessage().getSameMessage();
                newMessage.setRead(read);
                convo.getMessages().add(newMessage);
                box.add(0, convo);
            }
        }
        if (add) {
            Conversation newConvo = ((ReplyRequest) request).getConvo().getSameConvo();
            newConvo.setRead(read);
            Message newMessage = ((ReplyRequest) request).getMessage().getSameMessage();
            newMessage.setRead(read);
            newConvo.getMessages().add(newMessage);
            box.add(0, newConvo);
        }
    }

    private User getUserByName(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

}
