package Model.Request;

import Model.User;

public class SignInUpRequest extends GeneralRequest {
    private User user;
    private static final long serialVersionUID = 6L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SignInUpRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
