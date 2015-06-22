package hijack.dockerservice.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    10:11 PM
 * Purpose:
 */
public class RemoteDockerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteDockerService.class);

    // TODO: If run the main function locally for test purpose, please make sure this field is setup correctly.
    private static String dockerServerUrl;

    public static String getDockerServerUrl() {
        return dockerServerUrl;
    }

    public static void setDockerServerUrl(String dockerServerUrl) {
        RemoteDockerService.dockerServerUrl = dockerServerUrl;

    }

    // TODO: test only
    public static void main(String args[]) {
        String imageId = buildImage("", "");
        String containerId = createContainer(imageId);
        startContainer(containerId);
        listContainers();
    }

    public static String listImages() {
        return RESTfulClient.get("images/json");
    }

    public static String listContainers() {
        return RESTfulClient.get("containers/json");
    }

    /**
     * Build a image from a base image
     * input will be: POST /build?t=tag479&rm=true
     *
     * @param branchName
     * @param workspace
     * @return
     */
    public static String buildImage(String branchName, String workspace) {
        FileInputStream fis = null;
        ClientResponse response = null;
        String imageId = "";

        try {
            // TODO: add checkout branch and setup worksapce support...
            // 1. Build the image from base
            File templateFile = new File("template.bz2");
            fis = new FileInputStream(templateFile);

            Client client = Client.create();
            WebResource webResource = client.resource(getDockerServerUrl());
            webResource = webResource.path("/build");
            webResource = webResource.queryParam("t", "" + randInt(1, 1000));
            webResource = webResource.queryParam("rm", "true");

            response = webResource.type("application/tar").accept(MediaType.TEXT_PLAIN)
                    .post(ClientResponse.class, fis);

            if (response.getStatus() < 200 || response.getStatus() > 204) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);
            LOGGER.info("Output from Server .... \n");
            LOGGER.info(output);

            // Hack ...
            if (output.contains("Successfully")) {
                imageId = output.substring(output.lastIndexOf(" ") + 1, output.lastIndexOf("\\n"));
                LOGGER.info("**********" + imageId);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageId;
    }

    /**
     * Create a container based on a image id
     *
     * @param imageId
     * @return
     */
    public static String createContainer(String imageId) {

        ObjectMapper mapper = new ObjectMapper();
        CreateContainerRequest request = new CreateContainerRequest();
        String containerId = null;
        try {
            request.setImage(imageId);
            String input = mapper.writeValueAsString(request);

            String output = RESTfulClient.post("containers/create", input);
            LOGGER.info("Output for create container : {} ", output);

            CreateContainerResponse response = mapper.readValue(output, CreateContainerResponse.class);
            containerId = response.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Container Id : {}", containerId);
        return containerId;
    }


    /**
     * Start a container by a given id
     *
     * @param containerId
     * @return
     */
    public static boolean startContainer(String containerId) {
        RESTfulClient.post("containers/" + containerId + "/start", "");
        return true;
    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Wrapper for RESTful client of Docker Remote API
     */
    private static class RESTfulClient {
        private static final String APPLICATION_JSON = "application/json";

        /**
         * Issue a GET request to Docker RESTful API
         *
         * @param request
         * @return
         */
        public static String get(String request) {
            try {
                Client client = Client.create();

                WebResource webResource = client
                        .resource(getDockerServerUrl() + "/" + request);

                ClientResponse response = webResource.accept(APPLICATION_JSON)
                        .get(ClientResponse.class);

                if (response.getStatus() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatus());
                }

                String output = response.getEntity(String.class);

                System.out.println("Output from Server .... \n");
                System.out.println(output);
                return output;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * * Issue a POST request to Docker RESTful API
         *
         * @param requestUrl
         * @param input
         * @return
         */
        public static String post(String requestUrl, String input) {
            try {
                Client client = Client.create();

                WebResource webResource = client
                        .resource(getDockerServerUrl() + "/" + requestUrl);

                ClientResponse response = webResource.type(APPLICATION_JSON)
                        .post(ClientResponse.class, input);

                if (response.getStatus() < 200 || response.getStatus() > 204) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatus());
                }

                String output = response.getEntity(String.class);
                System.out.println("Output from Server .... \n");
                System.out.println(output);
                return output;
            } catch (Exception e) {
                //e.printStackTrace();
                LOGGER.warn("If the status code is 204, it means ok as Docker server side does not return anything.");
            }
            return "";

        }
    }

    private static class CreateContainerRequest {
        @JsonProperty
        private String Image;

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }
    }

    private static class CreateContainerResponse {
        @JsonProperty
        private String Id;
        @JsonProperty
        private String Warnings;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            this.Id = id;
        }

        public String getWarnings() {
            return Warnings;
        }

        public void setWarnings(String warnings) {
            Warnings = warnings;
        }
    }
}
