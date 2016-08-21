package hijack.dockerservice.DAO;

/**
 * Created by lovefly1983.
 */
public interface BaseDAO {
    /**
     * close with no args is used to close the connection
     */
    void close();

    public static final String URL = "%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8";
}
