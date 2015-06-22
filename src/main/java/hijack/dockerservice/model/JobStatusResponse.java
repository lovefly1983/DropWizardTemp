package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/22/15
 * Time:    9:18 AM
 * Purpose:
 */
public class JobStatusResponse {
    @JsonProperty
    private String status;

    public JobStatusResponse(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

