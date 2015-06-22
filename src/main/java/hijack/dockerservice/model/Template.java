package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

import static java.lang.String.format;

public class Template {
    @JsonProperty
    private final String content;
    @JsonProperty
    private final String defaultName;


    public Template(String content, String defaultName) {
        this.content = content;
        this.defaultName = defaultName;
    }
    
    public String render(Optional<String> name) {
        return format(content, name.or(defaultName));
    }
}
