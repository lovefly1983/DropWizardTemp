package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    4:08 PM
 * Purpose:
 */
public class SubmitJobResponse {
    @JsonProperty
    private String link;

    @JsonProperty
    private String status;

    @JsonProperty
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubmitJobResponse(String link, String instanceId, String status) {
        this.link = link;
        this.instanceId = instanceId;
        this.status = status;
    }
}
