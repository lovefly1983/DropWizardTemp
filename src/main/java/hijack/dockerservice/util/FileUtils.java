package hijack.dockerservice.util;

import java.io.*;

/**
 * Created by lovefly1983 on 8/8/15.
 */
public class FileUtils {
    // save uploaded file to new location
    public static void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            File f = new File(uploadedFileLocation);
            if(!f.getParentFile().exists()){
                f.getParentFile().mkdirs();
            }

            OutputStream out = new FileOutputStream(f);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
