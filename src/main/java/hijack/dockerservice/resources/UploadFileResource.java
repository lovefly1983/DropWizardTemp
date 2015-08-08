package hijack.dockerservice.resources;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import hijack.dockerservice.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by lovefly1983 on 5/8/15.
 */
@Path("/comp/file")
public class UploadFileResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileResource.class);
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) {

        LOGGER.info("upload file ... {}.", System.getProperty("user.dir"));
        String uploadedFileLocation = "../images/" + fileDetail.getFileName();

        // save it
        FileUtils.writeToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation;
        return Response.status(200).entity(output).build();
    }
}
