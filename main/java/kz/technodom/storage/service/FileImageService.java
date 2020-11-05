package kz.technodom.storage.service;

import kz.technodom.storage.config.ApplicationProperties;
import kz.technodom.storage.domain.FileImage;
import kz.technodom.storage.repository.FileImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FileImage}.
 */
@Service
@Transactional
public class FileImageService {

    private final Logger log = LoggerFactory.getLogger(FileImageService.class);

    private final FileImageRepository fileImageRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    public FileImageService(FileImageRepository fileImageRepository) {
        this.fileImageRepository = fileImageRepository;
    }

    private String getDir() {
        return applicationProperties.getImagesdir().getFilepath();
    }

    /**
     * Save a fileImage.
     *
     * @param fileImage the entity to save.
     * @return the persisted entity.
     */
    public FileImage save(FileImage fileImage) {
        log.debug("Request to save FileImage : {}", fileImage);
        return fileImageRepository.save(fileImage);
    }

    /**
     * Get all the fileImages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List <FileImage> findAll() {
        log.debug("Request to get all FileImages");
        return fileImageRepository.findAll();
    }


    /**
     * Get one fileImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional <FileImage> findOne(Long id) {
        log.debug("Request to get FileImage : {}", id);
        return fileImageRepository.findById(id);
    }

    /**
     * Delete the fileImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FileImage : {}", id);
        fileImageRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void removeOldImages() {
        List <FileImage> filesToDelete = findAll().stream().filter(fileImage -> fileImage.getExpiryDate().compareTo(LocalDate.now()) < 0).collect(Collectors.toList());
        filesToDelete.forEach(this::removeFile);
        log.info("[CRONJOB] Removing old images. Removed {} images", filesToDelete.size());
    }

    @Transactional
    public void removeFile(FileImage fileImage) {
        try {
            delete(fileImage.getId());
            Files.deleteIfExists(Paths.get(getDir() + "/" + fileImage.getUuid() + "." + fileImage.getMimeType()));
            Files.deleteIfExists(Paths.get(getDir() + "/thumbnails/thumbnail." + fileImage.getUuid() + "." + fileImage.getMimeType()));
        } catch (IOException e) {
            log.error("RemoveFile error ", e.getMessage());
        }
    }

}
