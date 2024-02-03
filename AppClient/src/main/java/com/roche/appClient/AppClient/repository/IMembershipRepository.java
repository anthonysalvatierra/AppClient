package com.roche.appClient.AppClient.repository;

import com.roche.appClient.AppClient.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IMembershipRepository extends JpaRepository<Membership, Long> {

    @Override
    Optional<Membership> findById(Long aLong);

    @Transactional
    void deleteById(Long id);
}
