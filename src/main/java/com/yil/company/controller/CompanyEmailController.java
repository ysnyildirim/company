package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.base.PageDto;
import com.yil.company.dto.CompanyEmailDto;
import com.yil.company.dto.CreateCompanyEmailDto;
import com.yil.company.model.CompanyEmail;
import com.yil.company.service.CompanyEmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "v1/companys/{companyId}/emails")
public class CompanyEmailController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyEmailService companyEmailService;

    @Autowired
    public CompanyEmailController(CompanyEmailService companyEmailService) {
        this.companyEmailService = companyEmailService;
    }

    @GetMapping
    public ResponseEntity<PageDto<CompanyEmailDto>> findAll(
            @PathVariable Long companyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<CompanyEmail> companyPage = companyEmailService.findAllByCompanyIdAndDeletedTimeIsNull(pageable, companyId);
            PageDto<CompanyEmailDto> pageDto = PageDto.toDto(companyPage, CompanyEmailService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyEmailDto> findById(
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            CompanyEmail company;
            try {
                company = companyEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!company.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            CompanyEmailDto dto = CompanyEmailService.toDto(company);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyEmailId,
                                 @PathVariable Long companyId,
                                 @Valid @RequestBody CreateCompanyEmailDto dto) {
        try {
            CompanyEmail entity = new CompanyEmail();
            entity.setCompanyId(companyId);
            entity.setAddress(dto.getAddress());
            entity.setEmailTypeId(dto.getEmailTypeId());
            entity.setCreatedUserId(authenticatedCompanyEmailId);
            entity.setCreatedTime(new Date());
            entity = companyEmailService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyEmailId,
                                  @PathVariable Long companyId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateCompanyEmailDto dto) {
        try {
            CompanyEmail entity = null;
            try {
                entity = companyEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            entity.setEmailTypeId(dto.getEmailTypeId());
            entity.setAddress(dto.getAddress());
            entity = companyEmailService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyEmailId,
                                         @PathVariable Long companyId,
                                         @PathVariable Long id) {
        try {
            CompanyEmail entity;
            try {
                entity = companyEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedCompanyEmailId);
            entity.setDeletedTime(new Date());
            entity = companyEmailService.save(entity);
            return ResponseEntity.ok("Company email deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
