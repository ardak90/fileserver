package kz.technodom.storage.service.util;

public class FileUploadUtil {

    public static boolean isValidImageFile(String contentType) {
        if (!(contentType.equals("image/pjpeg") || (contentType.equals("image/jpg") ||contentType.equals("image/jpeg") || contentType.equals("image/png")
            || contentType.equals("image/gif") || contentType.equals("image/bmp")
            || contentType.equals("image/x-png") || contentType.equals("image/x-icon")))) {
            return false;
        }
        return true;
    }

}
