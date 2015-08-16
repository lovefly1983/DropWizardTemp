package hijack.dockerservice.DAO;

import hijack.dockerservice.DAO.model.User;
import hijack.dockerservice.DAO.model.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * Created by lovefly1983.
 */
public interface UserDAO {
    @SqlUpdate("insert into users (name, email, password) values (:name, :email, :password)")
    void insert(@Bind("name") String name, @Bind("email") String email, @Bind("password") String password);

    @SqlQuery("select id, name, email from users where email = :email and password = :password")
    @Mapper(UserMapper.class)
    User findUserByEmailAndPassword(@Bind("email") String email, @Bind("password") String password);
}