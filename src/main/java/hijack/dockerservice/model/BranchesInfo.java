package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    3:29 PM
 * Purpose:
 */
public class BranchesInfo {
    private String query;
    private Collection<String> matchBranches = new ArrayList<String>();

    public BranchesInfo() {
        // Jackson deserialization
    }

    public BranchesInfo(String query, Collection<String> matchBranches) {
        this.query = query;
        this.matchBranches = matchBranches;
    }

    @JsonProperty
    public String getQuery() {
        return query;
    }

    @JsonProperty
    public Collection<String>  matchBranches() {
        return matchBranches;
    }
}
