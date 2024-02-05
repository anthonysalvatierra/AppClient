package com.roche.appClient.AppClient.service.Iservice;

import com.roche.appClient.AppClient.entities.Client;

import java.util.List;

public interface IClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void deleteById(Long id);

}
