package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Shipment;

import java.util.List;

public interface IShipmentService {

    List<Shipment> findAll();

    Shipment findById(Long id);

    Shipment save(Shipment shipment);

    void deleteById(Long id);

}
