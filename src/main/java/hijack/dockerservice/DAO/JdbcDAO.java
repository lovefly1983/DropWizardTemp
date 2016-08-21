package hijack.dockerservice.DAO;

import hijack.dockerservice.DockerServiceMainConfiguration;
import io.dropwizard.db.DataSourceFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lovefly1983.
 */

@Deprecated
public class JdbcDAO {
    private DockerServiceMainConfiguration configuration;

    private void insertToDB(int userId, String filePath) {
        Connection conn = null;
        String sql;
        DataSourceFactory dsf = configuration.getDataSourceFactory();
        String url = String.format(BaseDAO.URL, dsf.getUrl(), dsf.getUser(), dsf.getPassword());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            sql = "insert into images(user_id,path) values("+userId + ",'" + filePath + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
