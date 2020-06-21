package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String gender;
    private String number;
    private byte[] profilePicture;
    private LocalDate birthdate;
    private ArrayList<Conversation> inbox, outbox;
    private ArrayList<User> blockedUsers = new ArrayList<>();

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public User(String username, String password, String gender, String number, byte[] profilePicture, LocalDate birthdate) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.number = number;
        this.profilePicture = profilePicture;
        this.birthdate = birthdate;
        inbox = new ArrayList<>();
        outbox = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "user[username: " + username + " pass: " + password + " " + gender + " " + number + " " + birthdate +
                "]";
    }

    public User(String username, String password, LocalDate birthdate) {
        this(username, password);
        this.birthdate = birthdate;
        inbox = new ArrayList<>();
        outbox = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    public ArrayList<Conversation> getInbox() {
        if (inbox == null)
            System.out.println("inbox is null");
        return inbox;
    }

    public void setInbox(ArrayList<Conversation> inbox) {
        this.inbox = inbox;
    }

    public ArrayList<Conversation> getOutbox() {
        return outbox;
    }

    public void setOutbox(ArrayList<Conversation> outbox) {
        this.outbox = outbox;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAdditionalInfo(String number, String gender, byte[] profilePicture) {
        this.number = number;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        inbox = new ArrayList<>();
        outbox = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setInfo(User user) {
        if (user.birthdate != null)
            this.birthdate = user.birthdate;
        if (user.password != null)
            this.password = user.password;
        if (user.number != null)
            this.number = user.number;
        if (user.gender != null)
            this.gender = user.gender;
        if (user.profilePicture != null)
            this.profilePicture = user.profilePicture;
    }
}
