package com.roche.appClient.AppClient.controller;

import com.roche.appClient.AppClient.entities.Client;
import com.roche.appClient.AppClient.entities.ProductShipment;
import com.roche.appClient.AppClient.service.IProductShipmentService;
import com.roche.appClient.AppClient.utils.UtilService;
import com.roche.appClient.AppClient.entities.Shipment;
import com.roche.appClient.AppClient.service.IClientService;
import com.roche.appClient.AppClient.service.IProductService;
import com.roche.appClient.AppClient.service.IShipmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.roche.appClient.AppClient.constant.Constant.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/shipment")
public class ShipmentController {

    static Logger log = LogManager.getLogger(ShipmentController.class);

    @Autowired
    private IShipmentService shipmentService;

    @Autowired
    private IClientService clientDao;

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductShipmentService productShipmentService;

    @ResponseBody
    @RequestMapping("/findAll")
    public ResponseEntity<?> findAll(){

        Map<String, Object> response = new HashMap<>();
        List<Shipment> shipments;

        try{

            shipments = this.shipmentService.findAll();

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(shipments == null){
            response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
            log.info(MESSAGE_EMPTY_ELEMENTS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(SHIPMENTS.getMessage(), shipments);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Shipment shipment;

        try{

            shipment = this.shipmentService.findById(Long.parseLong(id));

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(shipment == null){
            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            log.info(ELEMENT_DOES_NOT_EXIST.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(SHIPMENT.getMessage(), shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/save")
    public ResponseEntity<?> save(@RequestParam String id,
                                  @RequestBody Object idsProdutsObject){

        Map<String, Object> response = new HashMap<>();
        Client client;
        List<Integer> idsProducts;
        Shipment shipment;
        UtilService util = new UtilService();

        try{

            client = this.clientDao.findById(Long.parseLong(id));
            idsProducts = (List<Integer>) idsProdutsObject;

            if(client != null && idsProducts != null){

                shipment = util.verify(client, idsProducts, "");

            }else{
                response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
                log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(shipment == null){
            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS);
        response.put(SHIPMENT.getMessage(), shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Shipment shipment;
        List<ProductShipment> productShipments;

        try{

            shipment = this.shipmentService.findById(Long.parseLong(id));
            productShipments = this.productShipmentService.findAllByShipmentId(shipment);

            for(ProductShipment productShipment: productShipments){

                this.productShipmentService.deleteById(productShipment.getId());

            }

            this.shipmentService.deleteById(shipment.getId());

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestParam String idClient,
                                  @RequestBody Object idsProdutsObject, @PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Client client;
        List<Integer> idsProducts;
        Shipment shipment;
        UtilService util = new UtilService();

        try{

            client = this.clientDao.findById(Long.parseLong(idClient));
            idsProducts = (List<Integer>) idsProdutsObject;

            if(client != null && idsProducts != null){

                shipment = util.verify(client, idsProducts, id);

            }else{
                response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
                log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(shipment == null){
            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS);
        response.put(SHIPMENT.getMessage(), shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
