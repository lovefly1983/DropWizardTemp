package hijack.dockerservice.resources;

import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataMultiPart;
import hijack.dockerservice.DAO.ImageDAO;
import hijack.dockerservice.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.UUID;

/**
 * Created by lovefly1983 on 5/8/15.
 */
@Path("/comp/file")
public class UploadFileResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileResource.class);
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FILE_NAME = "filename";
    private static final String NEWLINE = System.getProperty("line.separator");
    private ImageDAO imageDAO;

    public UploadFileResource(ImageDAO dao) {
        this.imageDAO = dao;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(FormDataMultiPart f) throws ParseException {

        LOGGER.info("upload file ... {}.", System.getProperty("user.dir"));

        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (BodyPart bp : f.getBodyParts()) {
                System.out.println(bp.getMediaType());
                System.out.println(bp.getHeaders().get(CONTENT_DISPOSITION));

                String fileName = bp.getParameterizedHeaders().getFirst(CONTENT_DISPOSITION).getParameters().get(FILE_NAME);
                BodyPartEntity bodyPartEntity = (BodyPartEntity) bp.getEntity();

                String uploadedFileLocation = "../images/" + fileName;
                // Write to the file system
                FileUtils.writeToFile(bodyPartEntity.getInputStream(), uploadedFileLocation);

                // TODO: write to DB for the user, use uuid for now...
                imageDAO.insert(4, uploadedFileLocation);

                stringBuffer.append("File uploaded to : ").append(uploadedFileLocation).append(NEWLINE);
                bp.cleanup();
            }
        } finally {
            f.cleanup();
        }
        return Response.status(200).entity(stringBuffer.toString()).build();
    }
}
