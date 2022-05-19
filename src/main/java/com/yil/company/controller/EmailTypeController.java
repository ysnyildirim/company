package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.dto.CreateEmailTypeDto;
import com.yil.company.dto.EmailTypeDto;
import com.yil.company.model.EmailType;
import com.yil.company.service.EmailTypeService;
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
@RequestMapping(value = "v1/email-types")
public class EmailTypeController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final EmailTypeService emailTypeService;

    @Autowired
    public EmailTypeController(EmailTypeService emailTypeService) {
        this.emailTypeService = emailTypeService;
    }

    @GetMapping
    public ResponseEntity<List<EmailTypeDto>> findAll() {
        try {
            List<EmailType> data = emailTypeService.findAllByDeletedTimeIsNull();
            List<EmailTypeDto> dtoData = new ArrayList<>();
            data.forEach(f -> {
                dtoData.add(EmailTypeService.toDto(f));
            });
            return ResponseEntity.ok(dtoData);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<EmailTypeDto> findById(@PathVariable Long id) {
        try {
            EmailType emailType = emailTypeService.findById(id);
            EmailTypeDto dto = EmailTypeService.toDto(emailType);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {

            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedEmailId,
                                 @Valid @RequestBody CreateEmailTypeDto dto) {
        try {
            EmailType emailType = new EmailType();
            emailType.setName(dto.getName());
            emailType.setCreatedUserId(authenticatedEmailId);
            emailType.setCreatedTime(new Date());
            emailType = emailTypeService.save(emailType);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedEmailId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateEmailTypeDto dto) {
        try {
            EmailType emailType;
            try {
                emailType = emailTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            emailType.setName(dto.getName());
            emailType = emailTypeService.save(emailType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedEmailId,
                                         @PathVariable Long id) {
        try {
            EmailType emailType;
            try {
                emailType = emailTypeService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            emailType.setDeletedUserId(authenticatedEmailId);
            emailType.setDeletedTime(new Date());
            emailTypeService.save(emailType);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

}
