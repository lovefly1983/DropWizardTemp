/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    4:03 PM
 * Purpose:
 */
package hijack.dockerservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import hijack.dockerservice.model.JobStatusResponse;
import hijack.dockerservice.model.SubmitJobRequest;
import hijack.dockerservice.model.SubmitJobResponse;
import hijack.dockerservice.util.RemoteDockerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/comp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobResource.class);

    @GET
    @Path("/status")
    public JobStatusResponse getStatus(@QueryParam("instance") Optional<String> instance) {
        LOGGER.info("instance : {} status query", instance.orNull());
        return new JobStatusResponse("Success");
    }

    @POST
    public SubmitJobResponse submit(@Valid SubmitJobRequest request) {
        LOGGER.info("Received a submit Job Request: {}", request);
        // 1. Build the image
        String imageId = RemoteDockerService.buildImage(request.getBranchName(), request.getWorkspace());
        LOGGER.info("Response from Remote API for build image: {}", imageId);

        // 2. Create the container based on the image just created
        String containerId = RemoteDockerService.createContainer(imageId);
        LOGGER.info("Response from Remote API for create container: {}", containerId);

        // 3. Start the container
        boolean result = RemoteDockerService.startContainer(containerId);
        String status = "Error";
        if (result) {
            status = "Submitted";
        }

        return new SubmitJobResponse("http://test.com", containerId, status);
    }
}