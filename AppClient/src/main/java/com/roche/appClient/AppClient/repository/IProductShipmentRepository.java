package com.roche.appClient.AppClient.repository;

import com.roche.appClient.AppClient.entities.ProductShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface IProductShipmentRepository extends JpaRepository<ProductShipment, Long> {

    @Transactional
    void deleteById(Long id);

    List<ProductShipment> findAllByProductIdAndShipmentId(Long idProduct, Long idShipment);

    List<ProductShipment> findAllByShipmentId(Long idShipment);

}
