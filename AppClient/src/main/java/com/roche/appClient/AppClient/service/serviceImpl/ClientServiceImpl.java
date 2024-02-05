package com.roche.appClient.AppClient.service.serviceImpl;

import com.roche.appClient.AppClient.entities.Client;
import com.roche.appClient.AppClient.repository.IClientRepository;
import com.roche.appClient.AppClient.service.Iservice.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository clientDao;

    @Override
    public List<Client> findAll() {
        return this.clientDao.findAll();
    }

    @Override
    public Client findById(Long id) {
        return this.clientDao.findById(id).orElse(null);
    }

    @Override
    public Client save(Client client) {
        return this.clientDao.save(client);
    }

    @Override
    public void deleteById(Long id) {
        this.clientDao.deleteById(id);
    }

}
