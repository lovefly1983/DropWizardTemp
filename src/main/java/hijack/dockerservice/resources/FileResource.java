package hijack.dockerservice.resources;

import com.google.common.base.Throwables;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataMultiPart;
import hijack.dockerservice.DAO.ImageDAO;
import hijack.dockerservice.DockerServiceMainConfiguration;
import hijack.dockerservice.model.FileQueryResponse;
import hijack.dockerservice.util.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by lovefly1983 on 5/8/15.
 */
@Path("/comp/file")
public class FileResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FILE_NAME = "filename";
    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    private ImageDAO imageDAO;
    private DockerServiceMainConfiguration configuration;

    public FileResource(DockerServiceMainConfiguration configuration, ImageDAO dao) {
        this.configuration = configuration;
        this.imageDAO = dao;
    }

    /**
     * Upload a file and save them into disk with path stored into db.
     *
     * @param f
     * @param userId
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(FormDataMultiPart f, @CookieParam("userId") String userId) {
        LOGGER.info("upload file ... {} with user id {}", configuration.getImagesFolder(), userId);

        try {
            if (isUserLogined(userId)) {
                for (BodyPart bp : f.getBodyParts()) {
                    String str = bp.getParameterizedHeaders().getFirst(CONTENT_DISPOSITION).getParameters().get(FILE_NAME);

                    // Make sure we can get the file name correctly
                    final String fileName = new String(str.getBytes("iso8859-1"), "utf-8");

                    LOGGER.debug("file name {}", fileName);

                    BodyPartEntity bodyPartEntity = (BodyPartEntity) bp.getEntity();

                    if (StringUtils.isNotEmpty(fileName)) {
                        InputStream inputStream = bodyPartEntity.getInputStream();

                        // Save raw image into disk
                        String filePath = getFilePath(userId, null, fileName);
                        final String fileFullPath = configuration.getImagesFolder() + File.separator+ filePath;
                        FileUtils.writeToFile(inputStream, configuration.getImagesFolder() + File.separator+ filePath);

                        // Save preview image
                        String previewPath = getFilePath(userId, "preview", fileName);

                        // Disable writing preview right now.
                        //FileUtils.writePreviewImage(fileFullPath, 300, 300, configuration.getImagesFolder() + File.separator + previewPath);
                        // Save path into db.
                        String prefix = configuration.getImagesVirtualFolder();
                        final String fullPath = prefix + File.separator + filePath;
                        String previewFullPath = prefix + File.separator + previewPath;

                        // Save path into db
                        getImageDAO().insert(Integer.valueOf(userId), fullPath, fullPath);

                        // Save into solr index
                        LOGGER.info("Save the file into solr index");
                        if (!configuration.isAsyncToSolr()) {
                            SolrResource.addFileIntoSolr(new HashMap<String, String>() {{
                                put("id", fullPath);
                                put("title", fileName);
                            }});
                        } else {
                            // TODO: async
                        }

                        // Cleanup
                        bp.cleanup();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
        } catch (ParseException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
        } catch (SolrServerException e) {
            e.printStackTrace();
        } finally {
            f.cleanup();
        }
    }

    /**
     * Return the list of files by user id.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FileQueryResponse listFiles(@QueryParam("userId") String userId) {
        LOGGER.info("user Id : {}", userId);
        FileQueryResponse response = new FileQueryResponse();
        response.setImageList(getImageDAO().listImagesByUser(Integer.valueOf(userId)));
        return response;
    }


    private ImageDAO getImageDAO() {
        return this.imageDAO;
    }

    private boolean isUserLogined(String userId) {
        return userId != null && !"-1".equals(userId);
    }

    /**
     *
     * @param userId
     * @param previewFolder
     * @param fileName
     * @return
     */
    private String getFilePath(String userId, String previewFolder, String fileName) {
        StringBuffer sb = new StringBuffer();
        sb = sb.append(userId).append(File.separator).append(getFormatNowDate());
        if (previewFolder != null) {
            sb = sb.append(File.separator).append(previewFolder);
        }
        sb.append(File.separator).append(fileName);
        return sb.toString();
    }

    /**
     * Current date in "yyyy-MM-dd" format.
     *
     * @return
     */
    public String getFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }
}
