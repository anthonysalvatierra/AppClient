package com.roche.appClient.AppClient.controller;

import com.roche.appClient.AppClient.entities.Client;
import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.entities.ProductShipment;
import com.roche.appClient.AppClient.service.Iservice.IProductShipmentService;
import com.roche.appClient.AppClient.entities.Shipment;
import com.roche.appClient.AppClient.service.Iservice.IClientService;
import com.roche.appClient.AppClient.service.Iservice.IProductService;
import com.roche.appClient.AppClient.service.Iservice.IShipmentService;
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
    @GetMapping("/findAll")
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
    @GetMapping("/findById/{id}")
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
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam String id,
                                  @RequestBody List<Long> idsProdutsObject){

        Map<String, Object> response = new HashMap<>();
        Client client;
        List<Long> idsProducts;
        Shipment shipment;

        try{

            client = this.clientDao.findById(Long.parseLong(id));
            idsProducts = idsProdutsObject;

            log.info("Id Product: " + idsProducts + " Client: " + client);

            if(client != null && idsProducts != null){

                shipment = verify(client, idsProducts, "");

            }else{
                response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
                log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(shipment == null){
            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(SHIPMENT.getMessage(), shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
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
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestParam String idClient,
                                  @RequestBody List<Long> idsProdutsObject, @PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Client client;
        List<Long> idsProducts;
        Shipment shipment;

        try{

            client = this.clientDao.findById(Long.parseLong(idClient));
            idsProducts = idsProdutsObject;

            if(client != null && idsProducts != null){

                shipment = verify(client, idsProducts, id);

            }else{
                response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
                log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(shipment == null){
            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            log.info(ELEMENT_DOES_NOT_EXIST.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(SHIPMENT.getMessage(), shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping("/deleteProductShipment")
    public ResponseEntity<?> deleteProductShipment(@RequestParam String idPruduct,
                                                   @RequestParam String idShipment){

        Product product;
        Shipment shipment;
        Map<String, Object> response = new HashMap<>();

        try{

            product = this.productService.findById(Long.parseLong(idPruduct));
            shipment = this.shipmentService.findById(Long.parseLong(idShipment));

            List<ProductShipment> productShipments = this.productShipmentService.findAllByProductIdAndShipmentId(product, shipment);

            if(productShipments == null){
                response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
                log.info(MESSAGE_EMPTY_ELEMENTS.getMessage() + " " + ELEMENT_DOES_NOT_EXIST.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            for(ProductShipment productShipment: productShipments){

                this.productShipmentService.deleteById(productShipment.getId());

            }

        }catch (Exception e){
            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage() + " " + MESSAGE_ERROR.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private Shipment verify(Client client, List<Long> idsProducts, String id){

        Shipment shipment;
        Shipment shipmentSaved = null;

        if(id.equals("")){
            shipment = new Shipment();
        }else{
            shipment = this.shipmentService.findById(Long.parseLong(id));
        }

        Date date = new Date();
        if(client.getNextRenewal().compareTo(date) > 0){

            Double acum = 0.0;
            List<Product> productList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            shipment.setClient(client);

            acum = validatePriory(idsProducts, client, acum, productList);

            Integer priory = client.getMembership().getPriority();
            validationPriory(shipment, calendar, priory);
            shipment.setTotalCost(acum);
            shipment.setProducts(productList);

            shipmentSaved = this.shipmentService.save(shipment);

            if(shipmentSaved == null){
                log.debug("Error executing sentences save in shipment");
            }

            saveProductShipmentRelation(productList, shipment);

        }

        if(shipmentSaved == null){
            log.debug("Problem has occurred during th execution of client.getNextRenewal");
        }

        return shipmentSaved;
    }

    private Double validatePriory(List<Long> idsProducts, Client client, Double acum, List<Product> products){

        for (Long idProduct : idsProducts) {
            Product product = this.productService.findById(idProduct);
            products.add(product);
            if (product != null) {
                if (product.getMinPriority() <= client.getMembership().getPriority()) {
                    acum += product.getCost();
                }else{
                    log.debug("You do not have priority enough");
                }
            }
        }
        return acum;
    }

    private void validationPriory(Shipment shipment, Calendar calendar, Integer priory){

        if (priory > 0 && priory <= 15) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            shipment.setDeliveryDate(calendar.getTime());
        } else if (priory > 15 && priory <= 18) {
            calendar.add(Calendar.HOUR, 12);
            shipment.setDeliveryDate(calendar.getTime());
        } else if (priory > 18) {
            calendar.add(Calendar.HOUR, 1);
            shipment.setDeliveryDate(calendar.getTime());
        }
    }

    private void  saveProductShipmentRelation(List<Product> productList, Shipment shipment) {

        log.info("Product List: " + productList + " Shipment: " + shipment);
        ProductShipment productShipmentSaved;

        for (Product product : productList) {
            ProductShipment productShipment = new ProductShipment();
            productShipment.setProduct(product);
            productShipment.setShipment(shipment);
            productShipmentSaved = this.productShipmentService.save(productShipment);

            log.info("Product Shipment: " + productShipmentSaved);
        }
    }
}
