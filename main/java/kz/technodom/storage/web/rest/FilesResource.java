package kz.technodom.storage.web.rest;

import kz.technodom.storage.service.FilesService;
import kz.technodom.storage.service.dto.DeffectiveProductDTO;
import kz.technodom.storage.service.util.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FilesResource {
    private FilesService filesService;

    public FilesResource(FilesService filesService){
        this.filesService = filesService;
    }

    @PostMapping("/deffective-product/add")
    public ResponseEntity writeMultiple(@RequestBody DeffectiveProductDTO deffectiveProductDTO) {
        DeffectiveProductDTO dp = filesService.write(deffectiveProductDTO);
        if(dp!=null){
            return ResponseEntity.status(HttpStatus.OK).body(dp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Serial Number exist");
    }

    @PostMapping("/deffective-product/update")
    public ResponseEntity update(@RequestBody DeffectiveProductDTO deffectiveProductDTO) {
        DeffectiveProductDTO dp = filesService.update(deffectiveProductDTO);
        if(dp!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(dp);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Defective Product with that ID not found OR You don't have rights to edit someones posts");
    }

    //Method for multipart file upload
/*    @PostMapping(value = "/multiple", consumes = { "multipart/form-data" })
    ResponseEntity writeMultiple(@RequestParam("files") MultipartFile[] files, @RequestParam String serialNumber, @RequestParam String description) throws Exception
    {
        if(files.length>0){
            for (MultipartFile f: files)
            {
                if(!FileUploadUtil.isValidImageFile(f.getContentType())){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not valid image type: "+f.getContentType());
                }
            }
            DeffectiveProductDTO dp = filesService.write(files, serialNumber, description);
            return ResponseEntity.status(HttpStatus.OK).body(dp);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No image provided");
    }*/
}
