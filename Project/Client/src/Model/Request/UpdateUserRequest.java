package Model.Request;

import Model.User;

public class UpdateUserRequest extends GeneralRequest{
    private static final long serialVersionUID = 8L;
    User user;

    public User getUser() {
        return user;
    }

    public UpdateUserRequest(User user) {
        this.user = user;
    }
}
