package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.dto.CreatePhoneTypeDto;
import com.yil.company.dto.PhoneTypeDto;
import com.yil.company.model.PhoneType;
import com.yil.company.service.PhoneTypeService;
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
@RequestMapping(value = "v1/phone-types")
public class PhoneTypeController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final PhoneTypeService phoneTypeService;

    @Autowired
    public PhoneTypeController(PhoneTypeService phoneTypeService) {
        this.phoneTypeService = phoneTypeService;
    }

    @GetMapping
    public ResponseEntity<List<PhoneTypeDto>> findAll() {
        try {
            List<PhoneType> data = phoneTypeService.findAllByDeletedTimeIsNull();
            List<PhoneTypeDto> dtoData = new ArrayList<>();
            data.forEach(f -> {
                dtoData.add(PhoneTypeService.toDto(f));
            });
            return ResponseEntity.ok(dtoData);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PhoneTypeDto> findById(@PathVariable Long id) {
        try {
            PhoneType phoneType = phoneTypeService.findById(id);
            PhoneTypeDto dto = PhoneTypeService.toDto(phoneType);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {

            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPhoneId,
                                 @Valid @RequestBody CreatePhoneTypeDto dto) {
        try {
            PhoneType phoneType = new PhoneType();
            phoneType.setName(dto.getName());
            phoneType.setCreatedUserId(authenticatedPhoneId);
            phoneType.setCreatedTime(new Date());
            phoneType = phoneTypeService.save(phoneType);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPhoneId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePhoneTypeDto dto) {
        try {
            PhoneType phoneType;
            try {
                phoneType = phoneTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            phoneType.setName(dto.getName());
            phoneType = phoneTypeService.save(phoneType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPhoneId,
                                         @PathVariable Long id) {
        try {
            PhoneType phoneType;
            try {
                phoneType = phoneTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            phoneType.setDeletedUserId(authenticatedPhoneId);
            phoneType.setDeletedTime(new Date());
            phoneTypeService.save(phoneType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

}
