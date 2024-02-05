package com.roche.appClient.AppClient.service.serviceImpl;

import com.roche.appClient.AppClient.entities.Membership;
import com.roche.appClient.AppClient.repository.IMembershipRepository;
import com.roche.appClient.AppClient.service.Iservice.IMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
