package hijack.dockerservice.DAO.model;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lovefly1983.
 */
public class UserMapper implements ResultSetMapper<User>
{
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        return new User(r.getInt("id"), r.getString("name"), r.getString("email"));
    }
}
