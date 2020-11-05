package kz.technodom.storage.web.rest;

import kz.technodom.storage.domain.FileImage;
import kz.technodom.storage.service.FileImageService;
import kz.technodom.storage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kz.technodom.storage.domain.FileImage}.
 */
@RestController
@RequestMapping("/api")
public class FileImageResource {

    private final Logger log = LoggerFactory.getLogger(FileImageResource.class);

    private static final String ENTITY_NAME = "fileImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileImageService fileImageService;

    public FileImageResource(FileImageService fileImageService) {
        this.fileImageService = fileImageService;
    }

    /**
     * {@code POST  /file-images} : Create a new fileImage.
     *
     * @param fileImage the fileImage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileImage, or with status {@code 400 (Bad Request)} if the fileImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-images")
    public ResponseEntity<FileImage> createFileImage(@RequestBody FileImage fileImage) throws URISyntaxException {
        log.debug("REST request to save FileImage : {}", fileImage);
        if (fileImage.getId() != null) {
            throw new BadRequestAlertException("A new fileImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileImage result = fileImageService.save(fileImage);
        return ResponseEntity.created(new URI("/api/file-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-images} : Updates an existing fileImage.
     *
     * @param fileImage the fileImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileImage,
     * or with status {@code 400 (Bad Request)} if the fileImage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-images")
    public ResponseEntity<FileImage> updateFileImage(@RequestBody FileImage fileImage) throws URISyntaxException {
        log.debug("REST request to update FileImage : {}", fileImage);
        if (fileImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileImage result = fileImageService.save(fileImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileImage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-images} : get all the fileImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileImages in body.
     */
    @GetMapping("/file-images")
    public List<FileImage> getAllFileImages() {
        log.debug("REST request to get all FileImages");
        return fileImageService.findAll();
    }

    /**
     * {@code GET  /file-images/:id} : get the "id" fileImage.
     *
     * @param id the id of the fileImage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileImage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-images/{id}")
    public ResponseEntity<FileImage> getFileImage(@PathVariable Long id) {
        log.debug("REST request to get FileImage : {}", id);
        Optional<FileImage> fileImage = fileImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileImage);
    }

    /**
     * {@code DELETE  /file-images/:id} : delete the "id" fileImage.
     *
     * @param id the id of the fileImage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-images/{id}")
    public ResponseEntity<Void> deleteFileImage(@PathVariable Long id) {
        log.debug("REST request to delete FileImage : {}", id);
        fileImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
