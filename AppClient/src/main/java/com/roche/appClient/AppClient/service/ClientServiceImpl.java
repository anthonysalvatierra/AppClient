package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Client;
import com.roche.appClient.AppClient.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
