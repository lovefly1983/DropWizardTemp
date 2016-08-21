package hijack.dockerservice.thirdparty;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lovefly1983.
 */
public class MemcachedTest {
    @Test
    public void testMemcached() throws IOException {

        MemcachedClient cache = new MemcachedClient(AddrUtil.getAddresses("127.0.0.1:11211"));
        String key = "testKey";
        String value = "testValue";

        System.out.println(cache.get(key));

        cache.set(key, 0, value);
        System.out.println(cache.get(key));
    }
}
