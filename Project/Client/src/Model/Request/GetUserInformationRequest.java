package Model.Request;

import Model.User;

public class GetUserInformationRequest extends GeneralRequest {
    private User user;

    public GetUserInformationRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
