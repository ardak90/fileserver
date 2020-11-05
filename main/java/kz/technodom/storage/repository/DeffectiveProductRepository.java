package kz.technodom.storage.repository;

import kz.technodom.storage.domain.DeffectiveProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DeffectiveProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeffectiveProductRepository extends JpaRepository <DeffectiveProduct, Long>, JpaSpecificationExecutor <DeffectiveProduct> {

    @Query("select deffectiveProduct from DeffectiveProduct deffectiveProduct where deffectiveProduct.user = ?#{principal.username}")
    List <DeffectiveProduct> findByUserIsCurrentUser();

    Page <DeffectiveProduct> findBySerialNumberContainsAndUser(String serialNumber, String user, Pageable pageable);

    Page <DeffectiveProduct> findBySerialNumberContains(String serialNumber, Pageable pageable);

    Page <DeffectiveProduct> findAllByUser(String user, Pageable pageable);

    Optional <DeffectiveProduct> getFirstBySerialNumber(String serialNumber);


}
