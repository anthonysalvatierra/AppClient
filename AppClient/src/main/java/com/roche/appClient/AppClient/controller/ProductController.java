package com.roche.appClient.AppClient.controller;

import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.service.Iservice.IProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.roche.appClient.AppClient.constant.Constant.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

    static Logger log = LogManager.getLogger(Product.class);

    @Autowired
    private IProductService productService;

    @ResponseBody
    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll(){

        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        try{

            products = this.productService.findAll();

        }catch(Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(products == null){

            response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
            log.info(MESSAGE_EMPTY_ELEMENTS.getMessage() + " " + ELEMENT_DOES_NOT_EXIST.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(PRODUCTS.getMessage(), products);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Product product;

        try{

            product = this.productService.findById(Long.parseLong(id));

        }catch(Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(product == null){

            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            log.info(MESSAGE_EMPTY_ELEMENTS.getMessage() + " " + ELEMENT_DOES_NOT_EXIST.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(PRODUCT.getMessage(), product);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/save")
    public ResponseEntity<?> save(@RequestBody Product product){

        Map<String, Object> response = new HashMap<>();
        Product productSaved;

        try{

            productSaved = this.productService.save(product);

        }catch(Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(PRODUCT.getMessage(), productSaved);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Product productDeleted;

        try{

            productDeleted = this.productService.findById(Long.parseLong(id));
            this.productService.deleteById(Long.parseLong(id));


        }catch(Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(PRODUCT_DELETED.getMessage(), productDeleted);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable String id, @RequestBody Product product){

        Map<String, Object> response = new HashMap<>();
        Product productFound;
        Product productUpdated;

        try{

            productFound = this.productService.findById(Long.parseLong(id));

            if(productFound == null){
                response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
                log.info(MESSAGE_ERROR.getMessage() + " " + ELEMENT_DOES_NOT_EXIST.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            productFound.setName(product.getName());
            productFound.setCost(product.getCost());
            productFound.setMinPriority(product.getMinPriority());

            productUpdated = this.productService.save(productFound);

        }catch(Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(PRODUCTS.getMessage(), productUpdated);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
