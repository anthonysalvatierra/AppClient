package com.roche.appClient.AppClient.service.Iservice;

import com.roche.appClient.AppClient.entities.Product;

public interface IProductService {

    Product findById(Long id);

}
