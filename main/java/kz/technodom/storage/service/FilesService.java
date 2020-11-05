package kz.technodom.storage.service;

import kz.technodom.storage.config.ApplicationProperties;
import kz.technodom.storage.domain.DeffectiveProduct;
import kz.technodom.storage.domain.FileImage;
import kz.technodom.storage.security.SecurityUtils;
import kz.technodom.storage.service.dto.DeffectiveProductDTO;
import kz.technodom.storage.service.dto.FileImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilesService {
    private final Logger log = LoggerFactory.getLogger(FilesService.class);
    private final DeffectiveProductService deffectiveProductService;
    private final FileImageService fileImageService;

    @Autowired
    ApplicationProperties applicationProperties;

    private String getDir() {
        return applicationProperties.getImagesdir().getFilepath();
    }

    public FilesService(DeffectiveProductService deffectiveProductService, FileImageService fileImageService) {
        this.deffectiveProductService = deffectiveProductService;
        this.fileImageService = fileImageService;
    }

    @Transactional
    public DeffectiveProductDTO write(DeffectiveProductDTO deffectiveProductDTO) {
        DeffectiveProduct deffectiveProduct = new DeffectiveProduct();
        if (deffectiveProductService.findOneBySerialNumber(deffectiveProductDTO.serialNumber) == null) {
            Set <FileImage> fileImages = deffectiveProductDTO.fileImageDTOS.stream().map(fileImageDTO -> FileImageDTO.toFileImage(fileImageDTO, getDir())).collect(Collectors.toSet());
            deffectiveProduct.setFileImages(fileImages);
            deffectiveProduct.setDescription(deffectiveProductDTO.description);
            deffectiveProduct.setSerialNumber(deffectiveProductDTO.serialNumber);
            return DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProductService.save(deffectiveProduct), getDir(), false);
        }
        return null;
    }

    @Transactional
    public DeffectiveProductDTO update(DeffectiveProductDTO deffectiveProductDTO) {
        Optional <DeffectiveProduct> dp = deffectiveProductService.findOne(deffectiveProductDTO.id);
        if (dp.isPresent()) {
            DeffectiveProduct deffectiveProduct = dp.get();
            if (SecurityUtils.getCurrentUserLogin().isPresent()) {
                if (deffectiveProduct.getUser().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()) || SecurityUtils.getCurrentUserLogin().get().equalsIgnoreCase("admin")) {
                    deffectiveProduct.getFileImages().forEach(fileImageService::removeFile);
                    deffectiveProduct.setFileImages(null);
                    deffectiveProduct.setFileImages(deffectiveProductDTO.fileImageDTOS.stream().map(fileImageDTO -> FileImageDTO.toFileImage(fileImageDTO, getDir())).collect(Collectors.toSet()));
                    deffectiveProduct.setSerialNumber(deffectiveProductDTO.serialNumber);
                    deffectiveProduct.setDescription(deffectiveProductDTO.description);
                    return DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProductService.save(deffectiveProduct), getDir(), false);
                }
            }
        }
        return null;
    }

    @Transactional
    public void remove(Long id) {
        try {
            deffectiveProductService.findOne(id).ifPresent(dp -> {
                dp.getFileImages().forEach(fileImageService::removeFile);
                deffectiveProductService.delete(id);
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
