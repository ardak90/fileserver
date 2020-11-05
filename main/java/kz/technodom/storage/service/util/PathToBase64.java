package kz.technodom.storage.service.util;

import liquibase.util.file.FilenameUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Utility to convert Image to Base64 and reverse.
 */

public class PathToBase64 {

    public static String convertImage(String path) throws IOException {
        File file = Paths.get(path).toFile();
        return Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
    }

    public static File revert(String base, String url, String uuid, String mimeType) throws IOException {
        byte[] data = Base64.getDecoder().decode(base);
        String newfile = url + "/" + uuid + "." + mimeType;
        try (OutputStream stream = new FileOutputStream(newfile)) {
            stream.write(data);
        }

        try {
            BufferedImage bimg = ImageIO.read(new File(newfile));
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            if (width > 2000 || height > 2000) {
                width = 1920;
            }
            Thumbnails.of(new File(newfile))
                .size(width, height)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(url + "/thumbnails/watermark/watermark.png")), 0.5f)
                .outputQuality(0.8)
                .toFile(new File(newfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thumbnails.of(new File(newfile))
            .size(320, 320)
            .toFiles(new File(url + "/thumbnails"), Rename.PREFIX_DOT_THUMBNAIL);
        return new File(newfile);
    }

    public static File compressImage(File file) throws IOException {
        if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("JPG")) {
            BufferedImage image = ImageIO.read(file);
            OutputStream out = new FileOutputStream(file);
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.8f);
            }
            writer.write(null, new IIOImage(image, null, null), param);
            out.close();
            ios.close();
            writer.dispose();
            return file;
        } else {
            return file;
        }
    }
}
