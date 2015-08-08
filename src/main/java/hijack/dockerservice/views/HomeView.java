package hijack.dockerservice.views;

import io.dropwizard.views.View;

/**
 * Created by lovefly1983 on 26/7/15.
 */
public class HomeView extends View {
    private final String clusterName;

    public HomeView(String clusterName) {
        super("index.ftl");
        this.clusterName = clusterName;
    }

    public String getClusterName() {
        return clusterName;
    }
}
