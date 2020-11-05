package kz.technodom.storage.web.rest;

import io.github.jhipster.web.util.PaginationUtil;
import kz.technodom.storage.domain.DeffectiveProduct;
import kz.technodom.storage.security.AuthoritiesConstants;
import kz.technodom.storage.security.SecurityUtils;
import kz.technodom.storage.service.DeffectiveProductService;
import kz.technodom.storage.service.dto.DeffectiveProductDTO;
import kz.technodom.storage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

/**
 * REST controller for managing {@link kz.technodom.storage.domain.DeffectiveProduct}.
 */
@RestController
@RequestMapping("/api")
public class DeffectiveProductResource {

    private final Logger log = LoggerFactory.getLogger(DeffectiveProductResource.class);

    private static final String ENTITY_NAME = "deffectiveProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeffectiveProductService deffectiveProductService;

    public DeffectiveProductResource(DeffectiveProductService deffectiveProductService) {
        this.deffectiveProductService = deffectiveProductService;
    }

    @PostMapping("/deffective-products")
    public ResponseEntity<DeffectiveProduct> createDeffectiveProduct(@RequestBody DeffectiveProduct deffectiveProduct) throws URISyntaxException {
        log.debug("REST request to save DeffectiveProduct : {}", deffectiveProduct);
        if (deffectiveProduct.getId() != null) {
            throw new BadRequestAlertException("A new deffectiveProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeffectiveProduct result = deffectiveProductService.save(deffectiveProduct);
        return ResponseEntity.created(new URI("/api/deffective-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/deffective-products")
    public ResponseEntity<DeffectiveProduct> updateDeffectiveProduct(@RequestBody DeffectiveProduct deffectiveProduct) throws URISyntaxException {
        log.debug("REST request to update DeffectiveProduct : {}", deffectiveProduct);
        if (deffectiveProduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeffectiveProduct result = deffectiveProductService.save(deffectiveProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deffectiveProduct.getId().toString()))
            .body(result);
    }

    @GetMapping("/deffective-products/all")
    public List<DeffectiveProductDTO> getAllDeffectiveProducts() {
        log.debug("REST request to get all DeffectiveProducts");
        return deffectiveProductService.findAll();
    }

    @GetMapping("/deffective-products/")
    public ResponseEntity<List<DeffectiveProductDTO>> getAllProducts(@RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, String show, Pageable pageable) {
        log.debug("REST request to get all DeffectiveProducts");
        Page<DeffectiveProductDTO> page;
        if(show.equalsIgnoreCase("all")) {
            page = deffectiveProductService.findAllPageable(pageable);
        } else {
            page = deffectiveProductService.findAllByUserPageable(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/deffective-products/find")
    public ResponseEntity<List<DeffectiveProductDTO>> findProducts(@RequestParam MultiValueMap<String, String> queryParams, String serialNumber, UriComponentsBuilder uriBuilder, Pageable pageable, String show) {
        log.debug("REST request to find DeffectiveProducts");
        final Page<DeffectiveProductDTO> page = deffectiveProductService.findAllBySerialNumber(pageable, serialNumber, show);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/deffective-products/{id}")
    public ResponseEntity<DeffectiveProductDTO> getDeffectiveProduct(@PathVariable Long id) {
        log.debug("REST request to get DeffectiveProduct : {}", id);
        DeffectiveProductDTO deffectiveProduct = deffectiveProductService.findOneDTO(id);
        if(deffectiveProduct!=null){
            return ResponseEntity.status(HttpStatus.OK).body(deffectiveProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/deffective-products/serial/{serialNumber}")
    public ResponseEntity<DeffectiveProductDTO> getDeffectiveProductBySerial(@PathVariable String serialNumber) {
        log.debug("REST request to get DeffectiveProduct by serialNumber: {}", serialNumber);
        DeffectiveProductDTO deffectiveProduct = deffectiveProductService.findOneBySerialNumber(serialNumber);
        if(deffectiveProduct!=null){
            return ResponseEntity.status(HttpStatus.OK).body(deffectiveProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/deffective-product/remove/{id}")
    public ResponseEntity<String> deleteDeffectiveProduct(@PathVariable Long id){
        log.debug("REST to remove defective product");
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
           return ResponseEntity.status(HttpStatus.OK).body(deffectiveProductService.delete(id));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(deffectiveProductService.deleteByUser(id));
        }
    }

}
