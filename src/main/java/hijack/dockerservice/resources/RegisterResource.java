package hijack.dockerservice.resources;

import hijack.dockerservice.DAO.UserDAO;
import hijack.dockerservice.DAO.model.User;
import hijack.dockerservice.model.RegisterAndLoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Path("/signup")
    public Response register(@FormParam("name") String name,
                             @FormParam("email") String email,
                             @FormParam("password") String password ) {
        LOGGER.info("User name, email & password: {} {}, {}", name, email, password);
        /*
        NewCookie cookie = null;
        CacheControl cc = new CacheControl();
        cookie = new NewCookie("userId", "55", "/comp/user/login", "testapp", null, 365*24*60*60, false);
        cc.setNoCache(true);
        if(cookie.getValue() != null)
            return Response.ok("welcome "+cookie.getValue()).cookie(cookie).cacheControl(cc).build();
        */

        userDAO.insert(name, email, password);
        return Response.status(200).entity("Register succeed!!!").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public RegisterAndLoginResponse login(@FormParam("email") String email, @FormParam("password") String password) {
        LOGGER.info("User name & password: {}, {}", email, password);
        // TODO: use dropwizard auth service... and cookie settings...
        User user = userDAO.findUserByEmailAndPassword(email, password);
        RegisterAndLoginResponse registerAndLoginResponse = null;
        if (user != null) {
            registerAndLoginResponse = new RegisterAndLoginResponse("login succeed", user.getId(), user.getName());
            return registerAndLoginResponse;
        }
        return new RegisterAndLoginResponse("login fails", -1, "Fail");
    }
}
