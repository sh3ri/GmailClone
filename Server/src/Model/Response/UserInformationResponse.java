package Model.Response;

import Model.User;

public class UserInformationResponse implements java.io.Serializable{
   private static final long serialVersionUID = 9L;
    private User user;

    public User getUser() {
        return user;
    }

    public UserInformationResponse(User user) {
        this.user = user;
    }
}
