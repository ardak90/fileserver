package kz.technodom.storage.service.dto;

import kz.technodom.storage.domain.DeffectiveProduct;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeffectiveProductDTO {

    public Long id;
    public String serialNumber;
    public String description;
    public String user;
    public Set<FileImageDTO> fileImageDTOS;

    public static DeffectiveProductDTO fromDeffectiveProduct(DeffectiveProduct deffectiveProduct,  String dir, Boolean isOriginalImage){
        DeffectiveProductDTO deffectiveProductDTO = new DeffectiveProductDTO();
        deffectiveProductDTO.id = deffectiveProduct.getId();
        deffectiveProductDTO.description = deffectiveProduct.getDescription();
        deffectiveProductDTO.serialNumber = deffectiveProduct.getSerialNumber();
        if(!deffectiveProduct.getFileImages().isEmpty()) {
            deffectiveProductDTO.fileImageDTOS = new HashSet<>();
            deffectiveProduct.getFileImages().forEach(fileImage -> deffectiveProductDTO.fileImageDTOS.add(FileImageDTO.fromFileImage(fileImage, dir, isOriginalImage)));
        }
        deffectiveProductDTO.user = deffectiveProduct.getUser();
        return deffectiveProductDTO;
    }

    public static List<DeffectiveProductDTO> fromDeffectiveProducts(List<DeffectiveProduct> deffectiveProducts, String dir, Boolean isOriginalImage) {
        return deffectiveProducts.stream().map(u -> fromDeffectiveProduct(u, dir, isOriginalImage)).collect(Collectors.toList());
    }
}
