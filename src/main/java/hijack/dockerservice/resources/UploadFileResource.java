package hijack.dockerservice.resources;

import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by chunjiewang on 5/8/15.
 */

public class UploadFileResource {
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public void upload(@FormDataParam("file") String v1) {
        return;
    }
}
