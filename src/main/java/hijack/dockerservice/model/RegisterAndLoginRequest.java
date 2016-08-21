package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lovefly1983.
 */
public class RegisterAndLoginRequest {
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
