package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Membership;

import java.util.List;

public interface IMembershipService {

    Membership findById(Long id);

    List<Membership> findAll();

    Membership save(Membership membership);

    void deleteById(Long id);

}
