package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.base.PageDto;
import com.yil.company.dto.CompanyPhoneDto;
import com.yil.company.dto.CreateCompanyPhoneDto;
import com.yil.company.model.CompanyPhone;
import com.yil.company.service.CompanyPhoneService;
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
@RequestMapping(value = "v1/companys/{companyId}/phones")
public class CompanyPhoneController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyPhoneService companyPhoneService;

    @Autowired
    public CompanyPhoneController(CompanyPhoneService companyPhoneService) {
        this.companyPhoneService = companyPhoneService;
    }

    @GetMapping
    public ResponseEntity<PageDto<CompanyPhoneDto>> findAll(
            @PathVariable Long companyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<CompanyPhone> companyPage = companyPhoneService.findAllByAndCompanyIdAndDeletedTimeIsNull(pageable, companyId);
            PageDto<CompanyPhoneDto> pageDto = PageDto.toDto(companyPage, CompanyPhoneService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyPhoneDto> findById(
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            CompanyPhone company;
            try {
                company = companyPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!company.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            CompanyPhoneDto dto = CompanyPhoneService.toDto(company);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyPhoneId,
                                 @PathVariable Long companyId,
                                 @Valid @RequestBody CreateCompanyPhoneDto dto) {
        try {
            CompanyPhone entity = new CompanyPhone();
            entity.setCompanyId(companyId);
            entity.setNumber(dto.getNumber());
            entity.setPhoneTypeId(dto.getPhoneTypeId());
            entity.setCreatedUserId(authenticatedCompanyPhoneId);
            entity.setCreatedTime(new Date());
            entity = companyPhoneService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyPhoneId,
                                  @PathVariable Long companyId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateCompanyPhoneDto dto) {
        try {
            CompanyPhone entity = null;
            try {
                entity = companyPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            entity.setPhoneTypeId(dto.getPhoneTypeId());
            entity.setNumber(dto.getNumber());
            entity = companyPhoneService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyPhoneId,
                                         @PathVariable Long companyId,
                                         @PathVariable Long id) {
        try {
            CompanyPhone entity;
            try {
                entity = companyPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedCompanyPhoneId);
            entity.setDeletedTime(new Date());
            entity = companyPhoneService.save(entity);
            return ResponseEntity.ok("Company phone deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
