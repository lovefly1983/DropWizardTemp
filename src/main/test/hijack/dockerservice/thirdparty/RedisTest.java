package hijack.dockerservice.thirdparty;

import hijack.dockerservice.DockerServiceMainConfiguration;
import hijack.dockerservice.factory.SolrFactory;
import hijack.dockerservice.resources.FileResource;
import hijack.dockerservice.resources.SolrResource;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lovefly1983.
 */
public class RedisTest {
    private static Jedis jedis = null;
    private static SolrResource solrResource = null;

    @BeforeClass
    public static void beforeClass() {
        jedis = new Jedis("localhost");
        DockerServiceMainConfiguration configuration = new DockerServiceMainConfiguration();
        SolrFactory solrFactory = new SolrFactory();
        solrFactory.setZookeeper("testapp:9983");
        configuration.setSolrFactory(solrFactory);
        solrResource = new SolrResource(configuration);
    }
    @Test
    public void testConnect() {
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        Assert.assertEquals("bar", value);
    }

    @Test
    public void testConsumer() throws IOException, SolrServerException {
        List<String> messages = null;
        while(true){
            System.out.println("Waiting for a message in the queue");
            messages = jedis.blpop(0, "solrQueue");
            System.out.println("Got the message");
            String payload = messages.get(1);
            //Do some processing with the payload
            FileResource fileResource = new FileResource();
            Map<String, String> mp = fileResource.jsonToMap(payload);
            for (Map.Entry<String, String> entry : mp.entrySet()) {
                System.out.println("KEY:" + entry.getKey() + " VALUE:" + entry.getValue());
            }
            solrResource.addFileIntoSolr(mp);
        }
    }

    @Test
    public void testConsumerForMap() {
        while(true){
            System.out.println("Waiting for a message in the queue");
            Map<String, String> messages = jedis.hgetAll("solrParams");
            System.out.println("Got the message");
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                System.out.println("KEY:" + entry.getKey() + " VALUE:" + entry.getValue());
            }
            try {
                // TODO el otro metodo podria hacer q no se consuman mensajes por un
                // tiempo si no llegan, de esta manera solo se esperan 500ms y se
                // controla que haya mensajes.
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}

