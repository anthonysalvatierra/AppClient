package com.roche.appClient.AppClient.service.serviceImpl;

import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.repository.IProductRepository;
import com.roche.appClient.AppClient.service.Iservice.IProductService;
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
