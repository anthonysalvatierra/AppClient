package com.roche.appClient.AppClient.service.Iservice;

import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.entities.ProductShipment;
import com.roche.appClient.AppClient.entities.Shipment;

import java.util.List;

public interface IProductShipmentService {

    ProductShipment save(ProductShipment productShipment);

    List<ProductShipment> findAllByShipmentId(Shipment shipment);

    List<ProductShipment> findAllByProductIdAndShipmentId(Product product, Shipment shipment);

    void deleteById(Long id);

}
