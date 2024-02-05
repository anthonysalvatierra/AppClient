package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productDao;

    @Override
    public Product findById(Long id) {
        return this.productDao.findById(id).orElse(null);
    }
}
