package kz.technodom.storage.service;


import kz.technodom.storage.config.ApplicationProperties;
import kz.technodom.storage.domain.DeffectiveProduct;
import kz.technodom.storage.repository.DeffectiveProductRepository;
import kz.technodom.storage.security.SecurityUtils;
import kz.technodom.storage.service.dto.DeffectiveProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DeffectiveProduct}.
 */
@Service
@Transactional
public class DeffectiveProductService {

    private final Logger log = LoggerFactory.getLogger(DeffectiveProductService.class);

    private final DeffectiveProductRepository deffectiveProductRepository;

    private final FileImageService fileImageService;

    @Autowired
    ApplicationProperties applicationProperties;

    private String getDir() {
        return applicationProperties.getImagesdir().getFilepath();
    }

    public DeffectiveProductService(DeffectiveProductRepository deffectiveProductRepository, FileImageService fileImageService) {
        this.deffectiveProductRepository = deffectiveProductRepository;
        this.fileImageService = fileImageService;
    }

    @Transactional
    public DeffectiveProduct save(DeffectiveProduct deffectiveProduct) {
        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            SecurityUtils.getCurrentUserLogin().ifPresent(deffectiveProduct::setUser);
            log.debug("Request to save DeffectiveProduct : {}", deffectiveProduct);
            deffectiveProduct.getFileImages().forEach(fileImage -> fileImage.setDeffectiveProduct(deffectiveProduct));
            return deffectiveProductRepository.save(deffectiveProduct);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List <DeffectiveProductDTO> findAll() {
        log.debug("Request to get all DeffectiveProducts");
        return DeffectiveProductDTO.fromDeffectiveProducts(deffectiveProductRepository.findAll(), getDir(), false);
    }


    @Transactional(readOnly = true)
    public Page <DeffectiveProductDTO> findAllPageable(Pageable pageable) {
        log.debug("Request to get all DeffectiveProducts");
        Page <DeffectiveProduct> deffectiveProducts = deffectiveProductRepository.findAll(pageable);
        return deffectiveProducts.map(deffectiveProduct -> DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProduct, getDir(), false));
    }

    @Transactional(readOnly = true)
    public Page <DeffectiveProductDTO> findAllByUserPageable(Pageable pageable) {
        log.debug("Request to get all DeffectiveProducts");
        Page <DeffectiveProduct> deffectiveProducts = deffectiveProductRepository.findAllByUser(SecurityUtils.getCurrentUserLogin().orElse(null), pageable);
        return deffectiveProducts.map(deffectiveProduct -> DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProduct, getDir(), false));
    }

    @Transactional(readOnly = true)
    public Page <DeffectiveProductDTO> findAllBySerialNumber(Pageable pageable, String serialNumber, String show) {
        log.debug("Request to find Defective Products by serial number");
        Page <DeffectiveProduct> deffectiveProducts = show.equalsIgnoreCase("all") ? deffectiveProductRepository.findBySerialNumberContains(serialNumber, pageable) : deffectiveProductRepository.findBySerialNumberContainsAndUser(serialNumber, SecurityUtils.getCurrentUserLogin().orElse(null), pageable);

        return deffectiveProducts.map(deffectiveProduct -> DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProduct, getDir(), false));
    }

    @Transactional(readOnly = true)
    public DeffectiveProductDTO findOneDTO(Long id) {
        log.debug("Request to get DeffectiveProduct : {}", id);
        Optional <DeffectiveProduct> deffectiveProduct = deffectiveProductRepository.findById(id);
        return deffectiveProduct.map(deffectiveProduct1 -> DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProduct1, getDir(), true)).orElse(null);
    }

    @Transactional(readOnly = true)
    public DeffectiveProductDTO findOneBySerialNumber(String serialNumber) {
        Optional <DeffectiveProduct> deffectiveProduct = deffectiveProductRepository.getFirstBySerialNumber(serialNumber);
        return deffectiveProduct.map(deffectiveProduct1 -> DeffectiveProductDTO.fromDeffectiveProduct(deffectiveProduct1, getDir(), true)).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional <DeffectiveProduct> findOne(Long id) {
        log.debug("Request to get DeffectiveProduct : {}", id);
        return deffectiveProductRepository.findById(id);
    }

    @Transactional
    public String delete(Long id) {
        log.debug("Request to delete DeffectiveProduct : {}", id);
        try {
            deffectiveProductRepository.findById(id).ifPresent(deffectiveProduct1 -> deffectiveProduct1.getFileImages().forEach(fileImageService::removeFile));
            deffectiveProductRepository.deleteById(id);
            return "Deleted Successfuly";
        } catch (Exception e) {
            log.debug("[DELETE ACTION ERROR]: {}", e.getMessage());
            return "Something went wrong";
        }
    }

    @Transactional
    public String deleteByUser(Long id) {
        log.debug("Request to delete DeffectiveProduct : {}", id);
        try {
            Optional <DeffectiveProduct> deffectiveProduct = deffectiveProductRepository.findById(id);
            if (deffectiveProduct.isPresent()) {
                if (SecurityUtils.getCurrentUserLogin().isPresent() && SecurityUtils.getCurrentUserLogin().get().equalsIgnoreCase(deffectiveProduct.get().getUser())) {
                    deffectiveProduct.get().getFileImages().forEach(fileImageService::removeFile);
                    deffectiveProductRepository.deleteById(id);
                    return "Deleted Successfuly";
                } else {
                    return "You cant delete someones defective products";
                }
            } else {
                return "Defective Product not found";
            }
        } catch (Exception e) {
            log.debug("[DELETE ACTION ERROR]: {}", e.getMessage());
            return "Something went wrong";
        }
    }
}
