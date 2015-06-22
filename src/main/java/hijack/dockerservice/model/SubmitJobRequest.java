package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    4:00 PM
 * Purpose:
 */
public class SubmitJobRequest {
    @JsonProperty
    private String branchName;

    @JsonProperty
    private String workspace;

    @JsonProperty
    public String getBranchName() {
        return branchName;
    }

    @JsonProperty
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @JsonProperty
    public String getWorkspace() {
        return workspace;
    }

    @JsonProperty
    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public SubmitJobRequest() {
        // no op
    }

    public SubmitJobRequest(String branchName, String workspace) {
        this.branchName = branchName;
        this.workspace = workspace;
    }
}
