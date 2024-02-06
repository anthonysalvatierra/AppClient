package com.roche.appClient.AppClient.service.Iservice;

import com.roche.appClient.AppClient.entities.Product;

import java.util.List;

public interface IProductService {

    Product findById(Long id);

    List<Product> findAll();

    Product save(Product product);

    void deleteById(Long id);

}
