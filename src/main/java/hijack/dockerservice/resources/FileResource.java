package hijack.dockerservice.resources;

import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataMultiPart;
import hijack.dockerservice.DAO.ImageDAO;
import hijack.dockerservice.DockerServiceMainConfiguration;
import hijack.dockerservice.model.FileQueryResponse;
import hijack.dockerservice.util.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 * Created by lovefly1983 on 5/8/15.
 */
@Path("/comp/file")
public class FileResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FILE_NAME = "filename";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private ImageDAO imageDAO;
    private DockerServiceMainConfiguration configuration;

    public FileResource(DockerServiceMainConfiguration configuration, ImageDAO dao) {
        this.configuration = configuration;
        this.imageDAO = dao;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(FormDataMultiPart f, @CookieParam("userId") String userId) throws ParseException, UnsupportedEncodingException {
        LOGGER.info("upload file ... {}. with user id {}", configuration.getImagesFolder(), userId);

        try {
            if (isUserLogined(userId)) {
                for (BodyPart bp : f.getBodyParts()) {
                    String str = bp.getParameterizedHeaders().getFirst(CONTENT_DISPOSITION).getParameters().get(FILE_NAME);

                    // Make sure we can get the file name correctly
                    String fileName = new String(str.getBytes("iso8859-1"), "utf-8");

                    LOGGER.info("file name {}", fileName);

                    BodyPartEntity bodyPartEntity = (BodyPartEntity) bp.getEntity();

                    if (StringUtils.isNotEmpty(fileName)) {
                        // Write to the file system
                        FileUtils.writeToFile(bodyPartEntity.getInputStream(), configuration.getImagesFolder() + File.separator+ fileName);

                        imageDAO.insert(Integer.valueOf(userId), configuration.getImagesVirtualFolder() + File.separator + fileName);
                        bp.cleanup();
                    }
                }
            }
        } finally {
            f.cleanup();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FileQueryResponse listFiles() {
        FileQueryResponse response = new FileQueryResponse();
        response.setImageList(imageDAO.listImages());
        return response;
    }

    private boolean isUserLogined(String userId) {
        return userId != null && !"-1".equals(userId);
    }
}
