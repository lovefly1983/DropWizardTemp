package hijack.dockerservice.resources;


import hijack.dockerservice.ActionHandler.IActionHandler;
import hijack.dockerservice.model.ComponentDoActionResponse;
import hijack.dockerservice.model.ComponentRequest;
import hijack.dockerservice.model.ComponentStatusResponse;
import hijack.dockerservice.model.OperationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/comp/{instance}/{component}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComponentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentResource.class);
    @GET
    @Path("/status")
    public ComponentStatusResponse getStatus(@PathParam("instance") String instance,
                                             @PathParam("component") String component) {
        LOGGER.info("instance : " + instance + " component: " + component);
        return new ComponentStatusResponse("Success");
    }

    @POST
    public ComponentDoActionResponse performOperation(@PathParam("instance") String instance,
                                                      @PathParam("component") String component,
                                                      @Valid ComponentRequest request) {
        LOGGER.info("instance : " + instance + " component: " + component);
        String operation = request.getOperation();
        if (request.isVaildOperation(operation)) {
            IActionHandler actionHandler = request.getHandlers().get(component);
            actionHandler.doAction(OperationEnum.valueOf(operation));
        }

        return new ComponentDoActionResponse(request.getOperation(), "Submitted");
    }
}
