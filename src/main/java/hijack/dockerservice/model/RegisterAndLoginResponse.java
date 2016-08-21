package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lovefly1983.
 */
public class RegisterAndLoginResponse {
    @JsonProperty
    private String message;

    @JsonProperty
    private int userId;

    @JsonProperty
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RegisterAndLoginResponse(String message, int userId, String userName) {
        this.message = message;
        this.userId = userId;
        this.userName = userName;
    }

    public RegisterAndLoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
