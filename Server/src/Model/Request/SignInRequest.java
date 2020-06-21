package Model.Request;

import Model.User;

public class SignInRequest extends SignInUpRequest {
    private static final long serialVersionUID = 3L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SignInRequest(User user) {
        super(user);
    }
}
