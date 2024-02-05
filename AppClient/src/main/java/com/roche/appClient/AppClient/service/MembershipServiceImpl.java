package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Membership;
import com.roche.appClient.AppClient.repository.IMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MembershipServiceImpl implements IMembershipService {

    @Autowired
    private IMembershipRepository membershipDao;

    @Override
    public Membership findById(Long id) {
        return this.membershipDao.findById(id).orElse(null);
    }

    @Override
    public List<Membership> findAll() {
        return this.membershipDao.findAll();
    }

    @Override
    public Membership save(Membership membership) {
        return this.membershipDao.save(membership);
    }

    @Override
    public void deleteById(Long id) {
        this.membershipDao.deleteById(id);
    }

}
