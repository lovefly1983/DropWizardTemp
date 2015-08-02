package hijack.dockerservice.resources;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by chunjiewang on 1/8/15.
 */
public class SolrResourceTest {
    private static final String ZOOKEEPER_HOST = "localhost:9983";

    @Test
    public void testCloudClient() {
        CloudSolrClient cloudSolrServer = new CloudSolrClient(ZOOKEEPER_HOST);
        cloudSolrServer.setDefaultCollection("gettingstarted");
        cloudSolrServer.connect();
        search(cloudSolrServer, "analysis");
    }

    private void search(SolrClient solrServer, String String) {
        SolrQuery query = new SolrQuery();
        query.setRows(100);
        query.setQuery(String);

        try {
            QueryResponse response = solrServer.query(query);
            SolrDocumentList docs = response.getResults();

            System.out.println("document num：" + docs.getNumFound());
            System.out.println("query time：" + response.getQTime());

            for (SolrDocument doc : docs) {
                String id = (String) doc.getFieldValue("id");
                System.out.println("id: " + id);
                System.out.println();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("Unknowned Exception!!!!");
            e.printStackTrace();
        }
    }

    @Test
    public void testHttpSolrClient() throws SolrServerException, IOException {
        final HttpSolrClient httpSolrClient  = SolrResource.SolrClientFactory.getInstance().getSolrClient(null);
        search(httpSolrClient, "", "", "", "", 0, 44);

    }

    public static void search(SolrClient solrClient, String name, String keywords,
                              String description, String sn, int start, int limit) throws SolrServerException, IOException {

        String searchParam = "";

        if (StringUtils.isNotEmpty(name)) {
            if (StringUtils.isNotEmpty(searchParam)) {
                searchParam += " AND name:" + name;
            } else {
                searchParam += "  name:" + name;
            }
        }

        if (StringUtils.isNotEmpty(keywords)) {
            if (StringUtils.isNotEmpty(searchParam)) {
                searchParam += " AND keywords:" + keywords;
            } else {
                searchParam += "  keywords:" + keywords;
            }
        }

        if (StringUtils.isNotEmpty(description)) {
            if (StringUtils.isNotEmpty(searchParam)) {
                searchParam += " AND description:" + description;
            } else {
                searchParam += "  description:" + description;
            }
        }

        if (StringUtils.isNotEmpty(sn)) {
            if (StringUtils.isNotEmpty(searchParam)) {
                searchParam += " AND sn:" + sn;
            } else {
                searchParam += "  sn:" + sn;
            }
        }

        if (!StringUtils.isNotEmpty(searchParam)) {
            searchParam = "*:*";
        }

        SolrQuery sQuery = new SolrQuery();

        sQuery.setQuery(searchParam);
        sQuery.setStart(start);
        sQuery.setRows(limit);


        QueryResponse response = solrClient.query(sQuery);
        System.out.println(response.getResults().getNumFound());
        for (SolrDocument document : response.getResults()) {
            System.out.println(document.getFieldNames());
        }

    }
}
