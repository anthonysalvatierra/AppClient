package com.roche.appClient.AppClient.repository;

import com.roche.appClient.AppClient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);

    @Transactional
    void deleteById(Long id);

}
