package hijack.dockerservice.util;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by lovefly1983 on 8/8/15.
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
    /**
     * Save uploaded file to new location
     *
     * @param uploadedInputStream
     * @param uploadedFileLocation
     */
    public static void writeToFile(InputStream uploadedInputStream,
                                   String uploadedFileLocation) {
        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            File f = new File(uploadedFileLocation);
            ensurePath(f);

            OutputStream out = new FileOutputStream(f);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
        } catch (IOException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * Write the preview image
     * @param inputStream
     * @param width
     * @param height
     * @param uploadedFileLocation
     */
    public static void writePreviewImage(InputStream inputStream, int width, int height, String uploadedFileLocation) {
        try {
            /*
            BufferedImage resizedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(ImageIO.read(inputStream).getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
            */

            BufferedImage bufferedImage = ImageIO.read(inputStream);
            BufferedImage resizedImage = scale(bufferedImage, 0.3);

            File f = new File(uploadedFileLocation);
            ensurePath(f);

            ImageIO.write(resizedImage, "jpg", new File(uploadedFileLocation));
        } catch (IOException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * Make sure the path exists.
     *
     * @param f
     */
    private static void ensurePath(File f) {
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
    }
    /**
     * Scale the image to certain ratio.
     *
     * @param source
     * @param ratio
     * @return
     */
    public static BufferedImage scale(BufferedImage source, double ratio) {
        int w = (int) (source.getWidth() * ratio);
        int h = (int) (source.getHeight() * ratio);
        BufferedImage bi = getCompatibleImage(w, h);
        Graphics2D g2d = bi.createGraphics();
        double xScale = (double) w / source.getWidth();
        double yScale = (double) h / source.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
        g2d.drawRenderedImage(source, at);
        g2d.dispose();
        return bi;
    }

    public static BufferedImage scaleImage(BufferedImage image, int maxSideLength) {
        assert (maxSideLength > 0);
        double originalWidth = image.getWidth();
        double originalHeight = image.getHeight();
        double scaleFactor = 0.0;
        if (originalWidth > originalHeight) {
            scaleFactor = ((double) maxSideLength / originalWidth);
        } else {
            scaleFactor = ((double) maxSideLength / originalHeight);
        }
        // create smaller image
        BufferedImage img = new BufferedImage((int) (originalWidth * scaleFactor), (int) (originalHeight * scaleFactor), BufferedImage.TYPE_INT_RGB);
        // fast scale (Java 1.4 & 1.5)
        Graphics g = img.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(image, 0, 0, img.getWidth(), img.getHeight(), null);
        g.dispose();
        return img;
    }

    /**
     *
     * @param w
     * @param h
     * @return
     */
    private static BufferedImage getCompatibleImage(int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
        return image;
    }
}