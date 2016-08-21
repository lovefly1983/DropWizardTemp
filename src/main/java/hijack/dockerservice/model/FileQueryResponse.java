package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lovefly1983.
 */
public class FileQueryResponse {
    @JsonProperty
    List<String> imageList;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
