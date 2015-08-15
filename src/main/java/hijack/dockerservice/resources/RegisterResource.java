package hijack.dockerservice.resources;

import hijack.dockerservice.DAO.UserDAO;
import hijack.dockerservice.model.RegisterAndLoginRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Created by lovefly1983.
 */
@Path("/comp/user")
public class RegisterResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterResource.class);
    private UserDAO userDAO;
    public RegisterResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(@Valid RegisterAndLoginRequest request) {
        LOGGER.info("Received a submit Job Request: {}", request);
        LOGGER.info("User name & password: {}, {}", request.getEmail(), request.getPassword());
        // Save to db
        return Response.status(200).entity("Register succeed!!!").build();
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        LOGGER.info("User name & password: {}, {}", email, password);
        // TODO: use dropwizard auth service... and cookie settings...
        String name = userDAO.findUserByEmailAndPassword(email, password);
        if (StringUtils.isNotEmpty(name)) {
            NewCookie cookie = null;
            CacheControl cc = new CacheControl();
            cookie = new NewCookie("userName", name);
            cc.setNoCache(true);
            if(cookie.getValue() != null)
                return Response.ok("welcome "+cookie.getValue()).cookie(cookie).cacheControl(cc).build();
            return Response.status(200).entity("login succeed!!!").build();
        }
        return Response.status(400).entity("login fail!!!").build();
    }
}
