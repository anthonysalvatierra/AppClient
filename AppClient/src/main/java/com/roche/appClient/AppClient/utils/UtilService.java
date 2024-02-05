package com.roche.appClient.AppClient.utils;

import com.roche.appClient.AppClient.entities.Client;
import com.roche.appClient.AppClient.entities.Product;
import com.roche.appClient.AppClient.entities.ProductShipment;
import com.roche.appClient.AppClient.entities.Shipment;
import com.roche.appClient.AppClient.service.IProductService;
import com.roche.appClient.AppClient.service.IProductShipmentService;
import com.roche.appClient.AppClient.service.IShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class UtilService {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductShipmentService productShipmentService;

    @Autowired
    private IShipmentService shipmentService;

    public Shipment verify(Client client, List<Integer> idsProducts, String id){

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
            validationPriory(shipment, client, calendar, priory);
            shipment.setTotalCost(acum);

            shipmentSaved = this.shipmentService.save(shipment);
            saveProductShipmentRelation(productList, shipment);

        }

        return shipmentSaved;
    }

    public Double validatePriory(List<Integer> idsProducts, Client client, Double acum, List<Product> products){

        for (Integer idProduct : idsProducts) {
            Product product = this.productService.findById(Long.parseLong(String.valueOf(idProduct)));
            products.add(product);
            if (product != null) {
                if (product.getMinPriority() <= client.getMembership().getPriority()) {
                    acum += product.getCost();
                }
            }
        }
        return acum;
    }

    public void validationPriory(Shipment shipment, Client client, Calendar calendar, Integer priory){

        if (priory > 0 && priory <= 15) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            shipment.setDeliveryDate(calendar.getTime());
        } else {
            if (priory > 15 && priory <= 18) {
                calendar.add(Calendar.HOUR, 12);
                shipment.setDeliveryDate(calendar.getTime());
            } else {
                if (client.getMembership().getPriority() > 18) {
                    calendar.add(Calendar.HOUR, 1);
                    shipment.setDeliveryDate(calendar.getTime());
                }
            }
        }

    }

    private void saveProductShipmentRelation(List<Product> productList, Shipment shipment) {
        for (Product product : productList) {
            ProductShipment productoShipment = new ProductShipment();
            productoShipment.setProduct(product);
            productoShipment.setShipment(shipment);
            this.productShipmentService.save(productoShipment);
        }
    }

}
