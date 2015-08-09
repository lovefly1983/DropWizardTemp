package hijack.dockerservice.DAO;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by lovefly1983.
 */
public interface UserDAO {
    @SqlUpdate("insert into users (name, email) values (:name, :email)")
    void insert(@Bind("name") String name, @Bind("email") String email);

    @SqlQuery("select name from images where id = :id")
    String findNameById(@Bind("id") int id);
}