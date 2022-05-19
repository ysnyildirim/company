package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.base.PageDto;
import com.yil.company.dto.CompanyDto;
import com.yil.company.dto.CreateCompanyDto;
import com.yil.company.model.Company;
import com.yil.company.service.CompanyService;
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
@RequestMapping(value = "v1/companys")
public class CompanyController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<PageDto<CompanyDto>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<Company> companyPage = companyService.findAllByDeletedTimeIsNull(pageable);
            PageDto<CompanyDto> pageDto = PageDto.toDto(companyPage, CompanyService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable Long id) {
        try {
            Company company;
            try {
                company = companyService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            CompanyDto dto = CompanyService.toDto(company);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyId,
                                 @Valid @RequestBody CreateCompanyDto dto) {
        try {
            Company company = new Company();
            company.setTitle(dto.getTitle());
            company.setFoundationDate(dto.getFoundationDate());
            company.setCreatedUserId(authenticatedCompanyId);
            company.setCreatedTime(new Date());
            company = companyService.save(company);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateCompanyDto dto) {
        try {
            Company company = new Company();
            company.setTitle(dto.getTitle());
            company.setFoundationDate(dto.getFoundationDate());
            company = companyService.save(company);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyId,
                                         @PathVariable Long id) {
        try {
            Company company;
            try {
                company = companyService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            company.setDeletedUserId(authenticatedCompanyId);
            company.setDeletedTime(new Date());
            companyService.save(company);
            return ResponseEntity.ok("Company deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
