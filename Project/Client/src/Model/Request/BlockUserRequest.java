package Model.Request;

import Model.User;

public class BlockUserRequest extends GeneralRequest {
    private static final long serialVerisonUID = 199L;
    private final User blocker, blocked;

    public User getBlocker() {
        return blocker;
    }

    public User getBlocked() {
        return blocked;
    }

    public BlockUserRequest(User blocker, User blocked) {
        this.blocker = blocker;
        this.blocked = blocked;
    }
}
