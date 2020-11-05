package kz.technodom.storage.repository;

import kz.technodom.storage.domain.FileImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileImageRepository extends JpaRepository<FileImage, Long> {

}
