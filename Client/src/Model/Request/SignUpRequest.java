package Model.Request;

import Model.User;

public class SignUpRequest extends SignInUpRequest {
    private static final long serialVersionUID = 5L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SignUpRequest(User user) {
        super(user);
    }
}
