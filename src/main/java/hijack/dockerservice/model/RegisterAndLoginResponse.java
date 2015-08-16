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

    public RegisterAndLoginResponse(String message, int userId) {
        this.message = message;
        this.userId = userId;
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
