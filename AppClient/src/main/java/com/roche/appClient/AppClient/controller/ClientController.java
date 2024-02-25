package com.roche.appClient.AppClient.controller;

import com.roche.appClient.AppClient.entities.Client;
import com.roche.appClient.AppClient.entities.Membership;
import com.roche.appClient.AppClient.service.Iservice.IClientService;
import com.roche.appClient.AppClient.service.Iservice.IMembershipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.roche.appClient.AppClient.constant.Constant.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/client")
public class ClientController {

    static final Logger log = LogManager.getLogger(ClientController.class);

    @Autowired
    private IClientService clientService;

    @Autowired
    private IMembershipService membershipService;


    @GetMapping("/findAll")
    @ResponseBody
    public ResponseEntity<?> findAllClient(){

        Map<String, Object> response = new HashMap<>();
        List<Client> clients;

        try{
            clients = this.clientService.findAll();
        }catch(Exception e){
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            response.put(MESSAGE.getMessage(), MESSAGE_ERROR.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(clients.isEmpty()){
            response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
            log.info(MESSAGE_EMPTY_ELEMENTS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(CLIENTS.getMessage(), clients);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping("/findById/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable String id){

        Client client;
        Map<String, Object> response = new HashMap<>();

        try{

            client = this.clientService.findById(Long.parseLong(id));

        }catch(Exception e){
            response.put(MESSAGE.getMessage(), MESSAGE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(client == null){
            response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(CLIENT.getMessage(), client);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody Client client){

        Client clientSaved;
        Map<String, Object> response = new HashMap<>();

        try{

            clientSaved = this.clientService.save(client);

        }catch(Exception e){
            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(CLIENT.getMessage(), clientSaved);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Client clientDeleted;

        try{

            clientDeleted = this.clientService.findById(Long.parseLong(id));
            this.clientService.deleteById(Long.parseLong(id));

        }catch(Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(CLIENT_DELETED.getMessage(), clientDeleted);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> edit(@RequestParam String id, @RequestBody Client client){

        Map<String, Object> response = new HashMap<>();
        Client clientFound;
        Client clientUpdated = null;

        try{
            clientFound = this.clientService.findById(Long.parseLong(id));

            if(clientFound != null){

                clientFound.setName(client.getName());
                clientFound.setLastname(client.getLastname());
                clientFound.setEmail(client.getEmail());
                clientFound.setDni(client.getDni());
                clientFound.setMembership(client.getMembership());
                clientFound.setLastDelivery(client.getLastDelivery());

                clientUpdated = this.clientService.save(clientFound);

            }

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(clientUpdated == null){

            response.put(MESSAGE.getMessage(), MESSAGE_ERROR.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(CLIENT.getMessage(), clientUpdated);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping("/renewMembership")
    @ResponseBody
    public ResponseEntity<?> renewMembership(@RequestParam String idClient,
                                             @RequestParam String idMembership){

        Map<String, Object> response = new HashMap<>();
        Membership membership = null;
        Client client = null;

        try{

            membership = this.membershipService.findById(Long.parseLong(idMembership));
            client = this.clientService.findById(Long.parseLong(idClient));

            if(membership != null && client != null){
                Calendar calendar =  Calendar.getInstance();
                calendar.setTime(new Date());

                client.setMembership(membership);
                calendar.add(Calendar.HOUR, membership.getDuration());

                client.setNextRenewal(calendar.getTime());
                this.clientService.save(client);

            }else {
                response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
                log.info(MESSAGE_ERROR.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(CLIENT.getMessage(), client);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
