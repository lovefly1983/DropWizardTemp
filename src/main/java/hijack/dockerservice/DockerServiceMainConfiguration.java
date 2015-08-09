package hijack.dockerservice;

import hijack.dockerservice.factory.SolrFactory;
import hijack.dockerservice.model.Template;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DockerServiceMainConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotEmpty
    private String svnUser;

    @NotEmpty
    private String svnPwd;

    @NotEmpty
    private String svnUrl;

    @NotEmpty
    private String dockerServerUrl;

    @NotEmpty
    private String imagesFolder;

    @NotEmpty
    private String imagesVirtualFolder;

    public String getImagesVirtualFolder() {
        return imagesVirtualFolder;
    }

    public void setImagesVirtualFolder(String imagesVirtualFolder) {
        this.imagesVirtualFolder = imagesVirtualFolder;
    }

    public String getImagesFolder() {
        return imagesFolder;
    }

    public void setImagesFolder(String imagesFolder) {
        this.imagesFolder = imagesFolder;
    }

    public String getDockerServerUrl() {
        return dockerServerUrl;
    }

    public void setDockerServerUrl(String dockerServerUrl) {
        this.dockerServerUrl = dockerServerUrl;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }

    @JsonProperty
    public String getSvnUser() {
        return svnUser;
    }

    @JsonProperty
    public void setSvnUser(String svnUser) {
        this.svnUser = svnUser;
    }

    @JsonProperty
    public String getSvnPwd() {
        return svnPwd;
    }

    @JsonProperty
    public void setSvnPwd(String svnPwd) {
        this.svnPwd = svnPwd;
    }

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    private SolrFactory solr = new SolrFactory();

    @JsonProperty("solr")
    public SolrFactory getSolrFactory() { return solr;}

    @JsonProperty("solr")
    public void setSolrFactory(SolrFactory solr) { this.solr = solr;}
}
