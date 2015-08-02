package hijack.dockerservice.resources;

import com.google.common.base.Optional;
import hijack.dockerservice.DockerServiceMainConfiguration;
import hijack.dockerservice.model.SolrDocumentRequest;
import hijack.dockerservice.model.SubmitJobRequest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Path("/comp/solr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SolrResource {
    final Logger logger = LoggerFactory.getLogger(SolrResource.class);

    private SolrClient sorlClient;

    public SolrResource() {
        this(null);
    }

    public SolrResource(DockerServiceMainConfiguration configuration) {
        if (configuration != null) {
            sorlClient = SolrClientFactory.getInstance().getCloudClient(configuration);
        }
    }

    @GET
    @Path("/query")
    public hijack.dockerservice.model.QueryResponse query(@QueryParam("qstr") Optional<String> qstr,
                      @QueryParam("maxrows") Optional<String> maxrows) {
        SolrQuery query = new SolrQuery();
        query.setRows(maxrows.orNull() != null ? Integer.valueOf(maxrows.orNull()) : 10);
        query.setQuery(qstr.orNull());
        return search(sorlClient,query);
    }

    @POST
    @Path("/add")
    public void addDoc(@Valid SolrDocumentRequest request) throws IOException, SolrServerException {
        Collection<SolrInputDocument> docs = new ArrayList<>();
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", request.getId());
        doc.addField("title", request.getTitle());
        docs.add(doc);
        sorlClient.add(docs);
        sorlClient.optimize();
        sorlClient.commit();
    }

    /**
     * Search against solr server.
     * @param solrClient
     * @param query
     */
    private hijack.dockerservice.model.QueryResponse search(SolrClient solrClient, SolrQuery query) {
        hijack.dockerservice.model.QueryResponse solrResponse = new hijack.dockerservice.model.QueryResponse();
        try {
            QueryResponse response = solrClient.query(query);
            SolrDocumentList docs = response.getResults();

            logger.info("document num：{}", docs.getNumFound());
            logger.info("query time：{}", response.getQTime());

            solrResponse.setDocNum(docs.getNumFound());
            solrResponse.setDocs(docs);
            return solrResponse;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch(Exception e) {
            logger.debug("Unknowned Exception!!!!");
            e.printStackTrace();
        }
        return solrResponse;
    }

    /**
     * Solr Client Factory.
     */
    static class SolrClientFactory {
        private static HttpSolrClient solrClient = null;
        private static SolrClientFactory solrClientFactory = null;
        private static final String solrHost = "http://localhost:8983/solr/gettingstarted_shard1_replica1/";
        private static int solrClientTimeout = 20000;
        private static int solrConnectTimeout = 5000;
        private static int maxConnectionsPerHost = 100;
        private static int maxTotalConnection = 100;

        private SolrClientFactory() {
        }

        public synchronized CloudSolrClient getCloudClient(DockerServiceMainConfiguration configuration) {
            CloudSolrClient cloudSolrServer = new CloudSolrClient(configuration.getSolrFactory().getZookeeper());
            cloudSolrServer.setDefaultCollection("gettingstarted");
            cloudSolrServer.connect();
            return cloudSolrServer;
        }

        public synchronized HttpSolrClient getSolrClient(DockerServiceMainConfiguration configuration) {
            if (solrClient == null) {
                solrClient = new HttpSolrClient(solrHost);

                solrClient.setSoTimeout(solrClientTimeout);  // socket read timeout
                solrClient.setConnectionTimeout(solrConnectTimeout);
                solrClient.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
                solrClient.setMaxTotalConnections(maxTotalConnection);
                solrClient.setFollowRedirects(false);  // defaults to false
                //allowCompression defaults to false.
                //Server side must support gzip or deflate for this to have any effect.
                solrClient.setAllowCompression(true);
            }
            return solrClient;
        }

        public static synchronized SolrClientFactory getInstance() {
            if (solrClientFactory == null) {
                solrClientFactory = new SolrClientFactory();
            }
            return solrClientFactory;
        }
    }
}
