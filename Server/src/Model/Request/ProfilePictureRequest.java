package Model.Request;

public class ProfilePictureRequest extends GeneralRequest {
    private final String user;

    public String getUser() {
        return user;
    }

    public ProfilePictureRequest(String user) {
        this.user = user;
    }


}
