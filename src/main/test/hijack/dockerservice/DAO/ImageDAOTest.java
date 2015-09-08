package hijack.dockerservice.DAO;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

/**
 * Created by lovefly1983.
 */
public class ImageDAOTest {
    @Test
    @Ignore
    public void testImageDAO() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        DBI dbi = new DBI("jdbc:mysql://localhost:3306/try", "root", "wcj2046test");

        ImageDAO dao = dbi.open(ImageDAO.class);

        dao.insert(4, "cwang@gmail.com", "");
        dao.close();
    }
}
