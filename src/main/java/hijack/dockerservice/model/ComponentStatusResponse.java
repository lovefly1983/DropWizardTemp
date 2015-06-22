package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    4:52 PM
 * Purpose:
 */
public class ComponentStatusResponse {
    @JsonProperty
    private String status;

    public ComponentStatusResponse(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
