package com.roche.appClient.AppClient.service;

import com.roche.appClient.AppClient.entities.Product;

public interface IProductService {

    Product findById(Long id);

}
