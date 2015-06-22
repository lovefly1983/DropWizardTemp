package hijack.dockerservice.resources;

import hijack.dockerservice.model.BranchesInfo;
import hijack.dockerservice.util.SvnInfo;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/branch")
@Produces(MediaType.APPLICATION_JSON)
public class SvnInfoResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SvnInfoResource.class);
    @GET
    public BranchesInfo getBranches(@QueryParam("query") Optional<String> query) {
        if (query != null) {
            Collection<String> branches = SvnInfo.getBranches(query.orNull());
            LOGGER.info("####Found branche ....");
            for (String branch : branches) {
                LOGGER.info(branch);
            }
            BranchesInfo result = new BranchesInfo(query.orNull(), branches);
            return result;
        }
        return new BranchesInfo();
    }
}
