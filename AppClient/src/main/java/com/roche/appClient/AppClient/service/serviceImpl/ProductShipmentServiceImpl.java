package com.roche.appClient.AppClient.service.serviceImpl;

import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.entities.ProductShipment;
import com.roche.appClient.AppClient.entities.Shipment;
import com.roche.appClient.AppClient.repository.IProductShipmentRepository;
import com.roche.appClient.AppClient.service.Iservice.IProductShipmentService;
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
    public List<ProductShipment> findAllByProductIdAndShipmentId(Product product, Shipment shipment) {
        return this.em.createQuery("SELECT ps FROM ProductShipment ps WHERE ps.product.id = :idProduct AND ps.shipment.id = :idShipment")
                .setParameter("idProduct", product.getId())
                .setParameter("idShipment", shipment.getId())
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        this.productShipmentDao.deleteById(id);
    }
}
