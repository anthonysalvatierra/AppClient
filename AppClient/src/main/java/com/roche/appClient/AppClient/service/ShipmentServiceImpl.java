package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Shipment;
import com.roche.appClient.AppClient.repository.IShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements IShipmentService {

    @Autowired
    private IShipmentRepository shipmentDao;

    @Override
    public List<Shipment> findAll() {
        return this.shipmentDao.findAll();
    }

    @Override
    public Shipment findById(Long id) {
        return this.shipmentDao.findById(id).orElse(null);
    }

    @Override
    public Shipment save(Shipment shipment) {
        return this.shipmentDao.save(shipment);
    }

    @Override
    public void deleteById(Long id) {
        this.shipmentDao.deleteById(id);
    }
}
