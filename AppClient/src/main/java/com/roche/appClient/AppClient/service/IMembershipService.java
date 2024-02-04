package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Membership;

public interface IMembershipService {

    Membership findById(Long id);

}
