package hijack.dockerservice.resources;

import hijack.dockerservice.model.RegisterAndLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by lovefly1983.
 */
@Path("/comp/user")
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterResource.class);
    @POST
    @Path("/register")
    public Response register(@Valid RegisterAndLoginRequest request) {
        LOGGER.info("Received a submit Job Request: {}", request);
        LOGGER.info("User name & password: {}, {}", request.getEmail(), request.getPassword());
        // Save to db
        return Response.status(200).entity("Register succeed!!!").build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid RegisterAndLoginRequest request) {
        LOGGER.info("Received a submit Job Request: {}", request);
        LOGGER.info("User name & password: {}, {}", request.getEmail(), request.getPassword());
        // check against to db
        return Response.status(200).entity("Register succeed!!!").build();
    }
}
