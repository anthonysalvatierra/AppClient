package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Membership;
import com.roche.appClient.AppClient.repository.IMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MembershipServiceImpl implements IMembershipService {

    @Autowired
    private IMembershipRepository membershipDao;

    @Override
    public Membership findById(Long id) {
        return this.membershipDao.findById(id).orElse(null);
    }

}
