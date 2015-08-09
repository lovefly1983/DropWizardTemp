package hijack.dockerservice.DAO;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

/**
 * Created by lovefly1983.
 */
public interface ImageDAO extends BaseDAO {
    @SqlUpdate("insert into images (user_id, path) values (:userId, :path)")
    void insert(@Bind("userId") int userId, @Bind("path") String path);

    @SqlQuery("select path from images")
    List<String> listImages();
}
