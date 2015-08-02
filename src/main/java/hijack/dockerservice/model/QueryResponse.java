package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by chunjiewang on 2/8/15.
 */
public class QueryResponse {
    @JsonProperty
    private long docNum;
    @JsonProperty
    private List docs;

    public void setDocNum(long docNum) {
        this.docNum = docNum;
    }

    public void setDocs(List docs) {
        this.docs = docs;
    }
}
