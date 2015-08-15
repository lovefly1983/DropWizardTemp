package hijack.dockerservice.DAO;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Created by lovefly1983.
 */
public interface UserDAO {
    @SqlUpdate("insert into users (name, email, password) values (:name, :email, :password)")
    void insert(@Bind("name") String name, @Bind("email") String email, @Bind("password") String password);

    @SqlQuery("select name from users where email = :email and password = :password")
    String findUserByEmailAndPassword(@Bind("email") String email, @Bind("password") String password);
}