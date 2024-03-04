package com.roche.appClient.AppClient.controller;

import com.roche.appClient.AppClient.entities.Membership;
import static com.roche.appClient.AppClient.constant.Constant.*;
import com.roche.appClient.AppClient.service.Iservice.IMembershipService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/membership")
public class MembershipController {

    static Logger log = LogManager.getLogger(MembershipController.class);

    @Autowired
    private IMembershipService membershipService;

    @ResponseBody
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){

        Map<String, Object> response = new HashMap<>();
        List<Membership> memberships;

        try{

            memberships = this.membershipService.findAll();

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(memberships.isEmpty()){
            response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
            log.info(MESSAGE_EMPTY_ELEMENTS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(MEMBERSHIPS.getMessage(), memberships);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Membership membership;

        try{

            membership = this.membershipService.findById(Long.parseLong(id));

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(membership == null){
            response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
            log.info(MESSAGE_ERROR.getMessage() + " " + MESSAGE_SECCESS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(MEMBERSHIP.getMessage(), membership);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Membership membership){

        Map<String, Object> response = new HashMap<>();
        Membership membershipSaved;

        try{

            membershipSaved = this.membershipService.save(membership);

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if(membershipSaved == null){
            response.put(MESSAGE.getMessage(), MESSAGE_EMPTY_ELEMENTS.getMessage());
            log.info(MESSAGE_ERROR.getMessage() + " " + MESSAGE_SECCESS.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(MEMBERSHIP.getMessage(), membershipSaved);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable String id, @RequestBody Membership membership){

        Map<String, Object> response = new HashMap<>();
        Membership membershipFound;
        Membership membershipUpdated;

        try{

            membershipFound = this.membershipService.findById(Long.parseLong(id));

            if(membershipFound != null){
                membershipFound.setName(membership.getName());
                membershipFound.setDuration(membership.getDuration());
                membershipFound.setKey(membership.getKey());
                membershipFound.setPriority(membership.getPriority());

                membershipUpdated = this.membershipService.save(membershipFound);
            }else{
                response.put(MESSAGE.getMessage(), ELEMENT_DOES_NOT_EXIST.getMessage());
                log.info(MESSAGE_ERROR.getMessage() + " " + MESSAGE_EMPTY_ELEMENTS.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(MEMBERSHIP.getMessage(), membershipUpdated);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){

        Map<String, Object> response = new HashMap<>();
        Membership membershipFound;

        try{
            membershipFound = this.membershipService.findById(Long.parseLong(id));
            this.membershipService.deleteById(Long.parseLong(id));

        }catch (Exception e){

            response.put(MESSAGE.getMessage(), SENTENCE_ERROR.getMessage());
            log.debug(SENTENCE_ERROR.getMessage() + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put(MESSAGE.getMessage(), MESSAGE_SECCESS.getMessage());
        response.put(MEMBERSHIP_DELETED.getMessage(), membershipFound);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }



}
