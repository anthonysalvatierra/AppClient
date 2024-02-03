package com.roche.appClient.AppClient.repository;

import com.roche.appClient.AppClient.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IShipmentRepository extends JpaRepository<Shipment, Long> {

    @Override
    Optional<Shipment> findById(Long aLong);

    @Transactional
    void deleteById(Long id);
}
