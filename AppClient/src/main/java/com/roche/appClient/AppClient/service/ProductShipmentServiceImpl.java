package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.ProductShipment;
import com.roche.appClient.AppClient.entities.Shipment;
import com.roche.appClient.AppClient.repository.IProductShipmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductShipmentServiceImpl implements IProductShipmentService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IProductShipmentRepository productShipmentDao;

    @Override
    public ProductShipment save(ProductShipment productShipment) {
        return this.productShipmentDao.save(productShipment);
    }

    @Override
    public List<ProductShipment> findAllByShipmentId(Shipment shipment) {
        return this.em.createQuery("SELECT ps FROM ProductShipment ps WHERE ps.shipment.id = :id")
                .setParameter("id", shipment.getId())
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        this.productShipmentDao.deleteById(id);
    }
}
