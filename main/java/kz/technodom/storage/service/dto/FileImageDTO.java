package kz.technodom.storage.service.dto;

import kz.technodom.storage.domain.FileImage;
import kz.technodom.storage.service.util.PathToBase64;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class FileImageDTO {

    public Long id;
    public String base64;
    public String expiryDate;
    public String mimeType;


    public static FileImageDTO fromFileImage(FileImage fileImage, String path, Boolean isOriginalImage){
        FileImageDTO fileImageDto = new FileImageDTO();
        fileImageDto.id = fileImage.getId();
        fileImageDto.expiryDate = fileImage.getExpiryDate().toString();
        fileImageDto.mimeType = fileImage.getMimeType();
        try {
            if(isOriginalImage) {
                path = path + "/" + fileImage.getUuid() + "." + fileImage.getMimeType();
            } else {
                path = path + "/thumbnails/thumbnail." + fileImage.getUuid() + "." + fileImage.getMimeType();
            }
                fileImageDto.base64 = PathToBase64.convertImage(path);
        }
        catch (IOException ex){
            fileImageDto.base64 = "Image not available";
        }
        return fileImageDto;
    }

    public static FileImage toFileImage(FileImageDTO fileImageDTO, String path){
        if(fileImageDTO==null){
            return null;
        }
        if(fileImageDTO.base64==null){
            return null;
        }
        if(fileImageDTO.base64.trim().isEmpty()){
            return null;
        }
        else {
            FileImage fileImage = new FileImage();
            try {
                UUID uuid = UUID.randomUUID();
                PathToBase64.revert(fileImageDTO.base64, path, uuid.toString(), fileImageDTO.mimeType);
                fileImage.setUuid(uuid.toString());
                fileImage.setMimeType(fileImageDTO.mimeType);
                fileImage.setExpiryDate(LocalDate.now().plusDays(180));
                return fileImage;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
