package hijack.dockerservice.factory;

/**
 * Created by chunjiewang on 1/8/15.
 */
public class SolrFactory {
    public void SolrFactory() {}

    private String host;
    private String zookeeper;
    private int solrClientTimeout = 20000;
    private int solrConnectTimeout = 5000;
    private int maxConnectionsPerHost = 100;
    private int maxTotalConnection = 100;

    public String getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String solrHost) {
        this.host = solrHost;
    }

    public int getSolrClientTimeout() {
        return solrClientTimeout;
    }

    public void setSolrClientTimeout(int solrClientTimeout) {
        this.solrClientTimeout = solrClientTimeout;
    }

    public int getSolrConnectTimeout() {
        return solrConnectTimeout;
    }

    public void setSolrConnectTimeout(int solrConnectTimeout) {
        this.solrConnectTimeout = solrConnectTimeout;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getMaxTotalConnection() {
        return maxTotalConnection;
    }

    public void setMaxTotalConnection(int maxTotalConnection) {
        this.maxTotalConnection = maxTotalConnection;
    }

}
