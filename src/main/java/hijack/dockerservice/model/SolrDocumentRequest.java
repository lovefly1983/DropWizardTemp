package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chunjiewang on 2/8/15.
 */
public class SolrDocumentRequest {
    @JsonProperty
    private String id;
    @JsonProperty
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
