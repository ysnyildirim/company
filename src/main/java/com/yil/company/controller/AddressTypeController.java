package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.dto.AddressTypeDto;
import com.yil.company.dto.CreateAddressTypeDto;
import com.yil.company.model.AddressType;
import com.yil.company.service.AddressTypeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "v1/address-types")
public class AddressTypeController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final AddressTypeService addressTypeService;

    @Autowired
    public AddressTypeController(AddressTypeService addressTypeService) {
        this.addressTypeService = addressTypeService;
    }

    @GetMapping
    public ResponseEntity<List<AddressTypeDto>> findAll() {
        try {
            List<AddressType> data = addressTypeService.findAllByDeletedTimeIsNull();
            List<AddressTypeDto> dtoData = new ArrayList<>();
            data.forEach(f -> {
                dtoData.add(AddressTypeService.toDto(f));
            });
            return ResponseEntity.ok(dtoData);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressTypeDto> findById(@PathVariable Long id) {
        try {
            AddressType addressType = addressTypeService.findById(id);
            AddressTypeDto dto = AddressTypeService.toDto(addressType);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {

            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedAddressId,
                                 @Valid @RequestBody CreateAddressTypeDto dto) {
        try {
            AddressType addressType = new AddressType();
            addressType.setName(dto.getName());
            addressType.setCreatedUserId(authenticatedAddressId);
            addressType.setCreatedTime(new Date());
            addressType = addressTypeService.save(addressType);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {

            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedAddressId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateAddressTypeDto dto) {
        try {
            AddressType addressType;
            try {
                addressType = addressTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            addressType.setName(dto.getName());
            addressType = addressTypeService.save(addressType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedAddressId,
                                         @PathVariable Long id) {
        try {
            AddressType addressType;
            try {
                addressType = addressTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            addressType.setDeletedUserId(authenticatedAddressId);
            addressType.setDeletedTime(new Date());
            addressTypeService.save(addressType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

}
