package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    4:56 PM
 * Purpose:
 */
public class ComponentDoActionResponse {
    @JsonProperty
    private String operation;

    @JsonProperty
    private String status;

    public ComponentDoActionResponse(String operation, String status) {
        this.operation = operation;
        this.status = status;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
